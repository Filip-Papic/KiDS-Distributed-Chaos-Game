package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.StartMessage;

public class StartHandler implements MessageHandler {

    private Message clientMessage;

    public StartHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.START) {
            if (clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()) {
                StartMessage startMessage = (StartMessage) clientMessage;

                JobChaos.startJob(AppConfig.jobNamesMap.get(startMessage.getJobName()));
            }
        } else {
            AppConfig.timestampedErrorPrint("Start handler got a message that is not Start");
        }
    }
}
