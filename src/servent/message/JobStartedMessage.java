package servent.message;

import java.util.concurrent.atomic.AtomicBoolean;

public class JobStartedMessage extends BasicMessage {
    private int originalSenderPort;
    private boolean bool;
    private int flag;

    public JobStartedMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort, int flag) {
        super(MessageType.JOB_STARTED, senderPort, receiverPort, senderIP, recieverIP);
        this.originalSenderPort = originalSenderPort;
        this.flag = flag;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }

    public boolean getBool() {
        return bool;
    }

    public int getFlag() {
        return flag;
    }
}
