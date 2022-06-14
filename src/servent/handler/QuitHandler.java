package servent.handler;

import app.AppConfig;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.QuitMessage;
import servent.message.util.MessageUtil;

public class QuitHandler implements MessageHandler {

    private Message clientMessage;

    public QuitHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.QUIT) {
            QuitMessage quitMessage = (QuitMessage) clientMessage;

            if(quitMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                AppConfig.SERVENT_COUNT -= 1;
                AppConfig.chordState.getAllNodeInfo().remove(quitMessage.getServentInfo());

                QuitMessage quitMessage1 = new QuitMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                        quitMessage.getOriginalSenderPort(), quitMessage.getServentInfo());
                MessageUtil.sendMessage(quitMessage1);
            }
        } else {
            AppConfig.timestampedErrorPrint("Welcome handler got a message that is not RESULT");
        }
    }
}
