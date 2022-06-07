package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import app.Job.JobResult;
import servent.message.*;
import servent.message.util.MessageUtil;

public class ResultHandler implements MessageHandler{

    private Message clientMessage;

    public ResultHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.RESULT) {
            ResultMessage resultMessage = (ResultMessage) clientMessage;
            if(clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()){// &&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                ResultMessage resultMessage1 = new ResultMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), resultMessage.getJobName());
                MessageUtil.sendMessage(resultMessage1);
            } else {
                JobChaos jobChaos = new JobChaos();
                for (Job jb : AppConfig.jobList) {
                    if (jb.getJobName().equals(resultMessage.getJobName())) {
                        jobChaos.chaosResult(jb);
                    }
                }
            }
        } else {
            AppConfig.timestampedErrorPrint("Welcome handler got a message that is not RESULT");
        }
    }
}
