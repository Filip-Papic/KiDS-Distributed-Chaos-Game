package cli.command;

import app.AppConfig;
import app.ChordState;
import cli.CLIParser;
import servent.SimpleServentListener;
import servent.message.QuitMessage;
import servent.message.util.MessageUtil;

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

        AppConfig.timestampedStandardPrint("Bootstrap is removing this node...");
        AppConfig.SERVENT_COUNT -= 1;
        AppConfig.chordState.getAllNodeInfo().remove(AppConfig.myServentInfo);

        QuitMessage quitMessage = new QuitMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                AppConfig.myServentInfo.getListenerPort(), AppConfig.myServentInfo);
        MessageUtil.sendMessage(quitMessage);

        parser.stop();
        listener.stop();
    }

}
