package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import servent.message.NewNodeMessage;
import servent.message.util.MessageUtil;

public class ServentInitializer implements Runnable {

	private String getSomeServentPort() {
		int bsPort = AppConfig.BOOTSTRAP_PORT;
		
		String retVal = "err";
		
		try {
			//Socket bsSocket = new Socket("localhost", bsPort);
			Socket bsSocket = new Socket(AppConfig.BOOTSTRAP_IP, bsPort);
			
			PrintWriter bsWriter = new PrintWriter(bsSocket.getOutputStream());
			bsWriter.write("Hail\n" + AppConfig.myServentInfo.getIpAddress() + ":" + AppConfig.myServentInfo.getListenerPort() + "\n");
			//bsWriter.write("Hail\n" + AppConfig.BOOTSTRAP_IP + ":" + AppConfig.myServentInfo.getListenerPort() + "\n");
			bsWriter.flush();
			
			Scanner bsScanner = new Scanner(bsSocket.getInputStream());
			retVal = bsScanner.nextLine();

			bsSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}
	
	@Override
	public void run() {
		String someServentPort = getSomeServentPort();
		int port = Integer.parseInt(someServentPort.split(":")[1]);
		String ip = someServentPort.split(":")[0];
		int id = Integer.parseInt(someServentPort.split(":")[2]);

		System.out.println("IP: " + ip);
		String[] ipSplit = ip.split(".");
		int [] arr = new int [ipSplit.length];
		int ID = 0;
		for(int i = 0; i < ipSplit.length; i++){
			arr[i] = Integer.parseInt(ipSplit[i]);
			ID += arr[i];
		}
		System.out.println(ID);
		ID += port;
		int hashID = ChordState.chordHash(ID);
		System.out.println("Hash ID: " + hashID + " old: " + ID);

		if (someServentPort.equals("err")) {
			AppConfig.timestampedErrorPrint("Error in contacting bootstrap. Exiting...");
			System.exit(0);
		}
		if (port == -1) { //bootstrap gave us -1 -> we are first
			//AppConfig.chordState.getAllNodeInfo().add(AppConfig.myServentInfo); //BRISI!!!!!!!!!!!!!!!
			AppConfig.myServentInfo.setId(id);
			AppConfig.timestampedStandardPrint("First node in Chord system.");
			System.out.println(someServentPort);
		} else { //bootstrap gave us something else - let that node tell our successor that we are here
			AppConfig.myServentInfo.setId(id);
			System.out.println(someServentPort);
			NewNodeMessage nnm = new NewNodeMessage(AppConfig.myServentInfo.getListenerPort(), port,
													AppConfig.myServentInfo.getIpAddress(), ip);
			MessageUtil.sendMessage(nnm);
		}
	}
}
