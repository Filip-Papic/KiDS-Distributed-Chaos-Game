package servent.handler;

import app.AppConfig;
import app.Job.JobChaos;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.StartMessage;
import servent.message.StatusMessage;
import servent.message.util.MessageUtil;

import java.util.HashMap;

public class StatusHandler implements MessageHandler {
    private Message clientMessage;

    public StatusHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.STATUS) {
            StatusMessage statusMessage = (StatusMessage) clientMessage;

            //if (clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()) {
            if (statusMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                if(statusMessage.getJobName() == null && statusMessage.getFractalID() == -1) {//status svega
                    statusMessage.addStatus(AppConfig.myServentInfo.getId(), JobChaos.statusPoints);

                    StatusMessage statusMessage1 = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), statusMessage.getOriginalSenderPort(),
                            null, -1, statusMessage.getStatus());
                    MessageUtil.sendMessage(statusMessage1);
                } else if(statusMessage.getJobName() != null && statusMessage.getFractalID() == -1){
                    //nesto drugo

                    StatusMessage statusMessage1 = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), statusMessage.getOriginalSenderPort(),
                            statusMessage.getJobName(), -1, statusMessage.getStatus());
                    MessageUtil.sendMessage(statusMessage1);
                } else {
                    //nesto trece

                    StatusMessage statusMessage1 = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), statusMessage.getOriginalSenderPort(),
                            statusMessage.getJobName(), statusMessage.getFractalID(), statusMessage.getStatus());
                    MessageUtil.sendMessage(statusMessage1);
                }
            } else {
                System.out.println("STATUS " + statusMessage.getStatus());
            }
        } else {
            AppConfig.timestampedErrorPrint("Status handler got a message that is not STATUS");
        }
    }
}
