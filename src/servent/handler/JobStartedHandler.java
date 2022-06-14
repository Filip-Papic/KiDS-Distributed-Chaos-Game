package servent.handler;

import app.AppConfig;
import servent.message.JobStartedMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.ResultMessage;
import servent.message.util.MessageUtil;

public class JobStartedHandler implements MessageHandler {

    private Message clientMessage;

    public JobStartedHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.JOB_STARTED) {
            JobStartedMessage jobStartedMessage = (JobStartedMessage) clientMessage;

            if(jobStartedMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {
                AppConfig.chordState.incrementStartedJobs();

                if(jobStartedMessage.getFlag() < AppConfig.flag){
                    System.out.println("Ja sam prvi");
                } else {
                    System.out.println("Ja sam drugi");
                }
                JobStartedMessage jsm = new JobStartedMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),  jobStartedMessage.getOriginalSenderPort(),
                        AppConfig.flag);
                MessageUtil.sendMessage(jsm);
            } else {

                AppConfig.chordState.setStartedJobsBool(true);
            }

        } else {
            AppConfig.timestampedErrorPrint("New job started handler got a message that is not JOB_STARTED");
        }
    }
}
