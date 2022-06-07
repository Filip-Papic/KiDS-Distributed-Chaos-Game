package servent.message;

public class ResultFractalMessage extends BasicMessage {

    private String jobName;
    private int fractalID;
    private int originalSenderPort;

    public ResultFractalMessage(int senderPort, int receiverPort, String senderIP, String recieverIP,
                                int originalSenderPort, String jobName, int fractalID) {
        super(MessageType.FRACTAL_RESULT, senderPort, receiverPort, senderIP, recieverIP);
        this.originalSenderPort = originalSenderPort;
        this.jobName = jobName;
        this.fractalID = fractalID;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }

    public String getJobName() {
        return jobName;
    }

    public int getFractalID() {
        return fractalID;
    }
}
