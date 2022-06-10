package servent.message;

import app.Job.Job;

public class NewJobAddedMessage extends BasicMessage {

    private Job jobFractal;
    private int originalSenderPort;
    public NewJobAddedMessage(int senderPort, int receiverPort, String senderIP, String recieverIP,
                              int originalSenderPort, Job jobFractal) {
        super(MessageType.NEW_JOB_ADDED, senderPort, receiverPort, senderIP, recieverIP);

        this.jobFractal = jobFractal;
        this.originalSenderPort = originalSenderPort;
    }

    public Job getJobFractal() {
        return jobFractal;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }
}
