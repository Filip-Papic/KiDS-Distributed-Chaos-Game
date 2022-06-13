package app;

import app.Job.Job;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains all the global application configuration stuff.
 * @author bmilojkovic
 *
 */
public class AppConfig {

	/**
	 * Convenience access for this servent's information
	 */
	public static ServentInfo myServentInfo;
	
	/**
	 * Print a message to stdout with a timestamp
	 * @param message message to print
	 */
	public static void timestampedStandardPrint(String message) {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		
		System.out.println(timeFormat.format(now) + " - " + message);
	}
	
	/**
	 * Print a message to stderr with a timestamp
	 * @param message message to print
	 */
	public static void timestampedErrorPrint(String message) {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		
		System.err.println(timeFormat.format(now) + " - " + message);
	}

	public static boolean INITIALIZED = false;
	public static int BOOTSTRAP_PORT;//short
	public static String BOOTSTRAP_IP;
	public static int WEAK_LIMIT;
	public static int STRONG_LIMIT;
	public static int SERVENT_COUNT;
	public static ArrayList<Job> jobList = new ArrayList<>();
	public static ArrayList<String> jobNames = new ArrayList<>();
	public static ChordState chordState;
	public static AtomicInteger ID = new AtomicInteger(-1);
	public static Boolean newJobFlag = false;
	public static Map<String, Job> jobNamesMap = new HashMap<>();
	public static Map<String, Map<Job, List<Point>>> jobNameResultsMap = new HashMap<>();
	
	/**
	 * Reads a config file. Should be called once at start of app.
	 * The config file should be of the following format:
	 * <br/>
	 * <code><br/>
	 * servent_count=3 			- number of servents in the system <br/>
	 * chord_size=64			- maximum value for Chord keys <br/>
	 * bs.port=2000				- bootstrap server listener port <br/>
	 * servent0.port=1100 		- listener ports for each servent <br/>
	 * servent1.port=1200 <br/>
	 * servent2.port=1300 <br/>
	 * 
	 * </code>
	 * <br/>
	 * So in this case, we would have three servents, listening on ports:
	 * 1100, 1200, and 1300. A bootstrap server listening on port 2000, and Chord system with
	 * max 64 keys and 64 nodes.<br/>
	 * 
	 * @param configName name of configuration file
	 * @param serventId id of the servent, as used in the configuration file
	 */
	public static void readConfig(String configName, int serventId){
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(configName)));
			
		} catch (IOException e) {
			timestampedErrorPrint("Couldn't open properties file. Exiting...");
			System.exit(0);
		}
		
		try {
			BOOTSTRAP_PORT = Integer.parseInt(properties.getProperty("bs.port"));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading bootstrap_port. Exiting...");
			System.exit(0);
		}

		try {
			SERVENT_COUNT = Integer.parseInt(properties.getProperty("servent_count"));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading servent_count. Exiting...");
			System.exit(0);
		}
		
		try {
			int chordSize = Integer.parseInt(properties.getProperty("chord_size"));
			
			ChordState.CHORD_SIZE = chordSize;
			chordState = new ChordState();
			
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading chord_size. Must be a number that is a power of 2. Exiting...");
			System.exit(0);
		}

		//BOOTSTRAP_IP = properties.getProperty("bs.ip_address");
		String checkIp = "";
		try {
			checkIp = String.valueOf(InetAddress.getLocalHost().getHostAddress());
			BOOTSTRAP_IP = String.valueOf(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if(checkIp == null){
			AppConfig.timestampedErrorPrint("Problem reading IP address from host machine");
			System.exit(0);
		}

		try {
			WEAK_LIMIT = Integer.parseInt(properties.getProperty("weak_limit"));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading weak_limit. Exiting...");
			System.exit(0);
		}

		try {
			STRONG_LIMIT = Integer.parseInt(properties.getProperty("strong_limit"));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading strong_limit. Exiting...");
			System.exit(0);
		}

		int jobs_count = 0;
		try {
			jobs_count = Integer.parseInt(properties.getProperty("jobs_count"));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading number of jobs. Exiting...");
			System.exit(0);
		}

		for(int i = 1; i <= jobs_count; i++){
			try {
				String name = properties.getProperty("name" + i);
				int n = Integer.parseInt(properties.getProperty("n" + i));
				if (n < 3 || n > 10) {
					timestampedErrorPrint("Number of points must be between 3 and 10. Exiting...");
					System.exit(0);
				}
				double p = Float.parseFloat(properties.getProperty("p" + i));
				if(p < 0 || p > 1){
					timestampedErrorPrint("Distance must be between 0 and 1. Exiting...");
					System.exit(0);
				}
				int w = Integer.parseInt(properties.getProperty("w" + i));
				int h = Integer.parseInt(properties.getProperty("h" + i));
				String[] a = properties.getProperty("a" + i).split(",");
				List<Point> aa = new ArrayList<>();
				for(int j = 0; j < a.length; j+=2){
					aa.add(new Point(Integer.parseInt(a[j]), Integer.parseInt(a[j+1])));
				}

				Job job = new Job(name, n, p, w, h, aa);
				jobNames.add(name);
				jobList.add(job);
				jobNamesMap.put(name, job);
			} catch (NumberFormatException e) {
				timestampedErrorPrint("Problems reading config for job" + i + ". Exiting...");
				System.exit(0);
			}
		}

		String portProperty = "servent"+serventId+".port";
		
		int serventPort = -1;
		
		try {
			serventPort = Integer.parseInt(properties.getProperty(portProperty));
		} catch (NumberFormatException e) {
			timestampedErrorPrint("Problem reading " + portProperty + ". Exiting...");
			System.exit(0);
		}
		
		myServentInfo = new ServentInfo(BOOTSTRAP_IP, serventPort);
	}

}
