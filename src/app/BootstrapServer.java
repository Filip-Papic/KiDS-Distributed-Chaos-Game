package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BootstrapServer {

	private volatile boolean working = true;
	private List<String> activeServents;
	private String ipAddress;

	{
		try {
			ipAddress = String.valueOf(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private class CLIWorker implements Runnable {
		@Override
		public void run() {
			Scanner sc = new Scanner(System.in);
			
			String line;
			while(true) {
				line = sc.nextLine();
				
				if (line.equals("stop")) {
					working = false;
					break;
				}
			}
			
			sc.close();
		}
	}
	
	public BootstrapServer() {
		activeServents = new ArrayList<>();
	}
	
	public void doBootstrap(int bsPort) {
		Thread cliThread = new Thread(new CLIWorker());
		cliThread.start();
		
		ServerSocket listenerSocket = null;
		try {
			listenerSocket = new ServerSocket(bsPort);
			listenerSocket.setSoTimeout(1000);
		} catch (IOException e1) {
			AppConfig.timestampedErrorPrint("BOOTSTRAP: Problem while opening listener socket.");
			System.exit(0);
		}
		
		Random rand = new Random(System.currentTimeMillis());
		
		while (working) {
			try {
				Socket newServentSocket = listenerSocket.accept();
				
				 /* 
				 * Handling these messages is intentionally sequential, to avoid problems with
				 * concurrent initial starts.
				 * 
				 * In practice, we would have an always-active backbone of servents to avoid this problem.
				 */
				
				Scanner socketScanner = new Scanner(newServentSocket.getInputStream());
				String message = socketScanner.nextLine();
				
				/*
				 * New servent has hailed us. He is sending us his own listener port.
				 * He wants to get a listener port from a random active servent,
				 * or -1 if he is the first one.
				 */
				if (message.equals("Hail")) {
					String[] info = socketScanner.nextLine().split(":");
					String newServentIp = info[0];
					int newServentPort = Integer.parseInt(info[1]);
					String newServentInfo = newServentIp + ":" + newServentPort;

					System.out.println("BOOTSTRAP: got " + newServentIp + ":" + newServentPort);
					PrintWriter socketWriter = new PrintWriter(newServentSocket.getOutputStream());

					if (activeServents.size() == 0) {//bootstrap salje i ip adresu i port
						//socketWriter.write(String.valueOf(-1) + "\n");
						socketWriter.write("-1"+ ":" + String.valueOf(-1) + "\n");
						activeServents.add(newServentInfo); //first one doesn't need to confirm
					} else {
						String randServent = activeServents.get(rand.nextInt(activeServents.size()));
						socketWriter.write(randServent.split(":")[0] + ":" + randServent.split(":")[1] + "\n");
					}
					
					socketWriter.flush();
					newServentSocket.close();
				} else if (message.equals("New")) {
					/**
					 * When a servent is confirmed not to be a collider, we add him to the list.
					 */
					String[] info = socketScanner.nextLine().split(":");
					String newServentIp = info[0];
					int newServentPort = Integer.parseInt(info[1]);
					String newServentInfo = newServentIp + ":" + newServentPort;

					System.out.println("BOOTSTRAP: adding " + newServentInfo);

					activeServents.add(newServentInfo);
					newServentSocket.close();
				} else if (message.equals("Bye")) {
					/**
					 * Removing servents from the list
					 */
					String[] info = socketScanner.nextLine().split(":");
					String serventIp = info[0];
					int serventPort = Integer.parseInt(info[1]);
					String newServentInfo = serventIp + ":" + serventPort;

					System.out.println("BOOTSTRAP: removing " + serventIp + ":" + serventPort);
					PrintWriter socketWriter = new PrintWriter(newServentSocket.getOutputStream());

					if (activeServents.size() == 0) {
						System.out.println("BOOTSTRAP: no nodes to remove!!!");
					} else {
						activeServents.remove(newServentInfo);
					}

					socketWriter.flush();
					newServentSocket.close();
				}
			} catch (SocketTimeoutException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Expects one command line argument - the port to listen on.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			AppConfig.timestampedErrorPrint("BOOTSTRAP: Bootstrap started without port argument.");
		}
		
		int bsPort = 0;
		try {
			bsPort = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			AppConfig.timestampedErrorPrint("BOOTSTRAP: Bootstrap port not valid: " + args[0]);
			System.exit(0);
		}
		
		AppConfig.timestampedStandardPrint("BOOTSTRAP: Bootstrap server started on port: " + bsPort);
		
		BootstrapServer bs = new BootstrapServer();
		bs.doBootstrap(bsPort);
	}
}
