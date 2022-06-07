package servent.message;

public class ResultMessage extends BasicMessage {

    private String jobName;
    private int fractalID = -1;
    private int originalSenderPort;

    public ResultMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort, String messageText) {
        super(MessageType.RESULT, senderPort, receiverPort, senderIP, recieverIP);
        this.jobName = messageText;
        this.originalSenderPort = originalSenderPort;
    }

    public ResultMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort, String messageText,
                         int fractalID) {
        super(MessageType.RESULT, senderPort, receiverPort, senderIP, recieverIP);
        this.jobName = messageText;
        this.fractalID = fractalID;
        this.originalSenderPort = originalSenderPort;
    }

    public String getJobName() {
        return jobName;
    }

    public int getFractalID() {
        return fractalID;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }
}
