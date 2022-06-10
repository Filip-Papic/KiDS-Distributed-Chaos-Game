package cli.command;

import app.AppConfig;
import app.ChordState;
import cli.CLIParser;
import servent.SimpleServentListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class QuitCommand implements CLICommand {

    private CLIParser parser;
    private SimpleServentListener listener;

    public QuitCommand(CLIParser parser, SimpleServentListener listener) {
        this.parser = parser;
        this.listener = listener;
    }

    @Override
    public String commandName() {
        return "quit";
    }

    @Override
    public void execute(String args) {
        try {
            //Socket bsSocket = new Socket("localhost", bsPort);
            Socket bsSocket = new Socket(AppConfig.BOOTSTRAP_IP, AppConfig.BOOTSTRAP_PORT);

            PrintWriter bsWriter = new PrintWriter(bsSocket.getOutputStream());
            bsWriter.write("Bye\n" + AppConfig.myServentInfo.getIpAddress() + ":" + AppConfig.myServentInfo.getListenerPort() + "\n");
            //bsWriter.write("Hail\n" + AppConfig.BOOTSTRAP_IP + ":" + AppConfig.myServentInfo.getListenerPort() + "\n");
            bsWriter.flush();

            bsSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //QuitMessage quitMessage =  URADI AAAAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        AppConfig.timestampedStandardPrint("Bootstrap is removing this node...");
        parser.stop();
        listener.stop();
    }

}
