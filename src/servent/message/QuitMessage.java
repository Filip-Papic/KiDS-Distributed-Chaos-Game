package servent.message;

import app.ServentInfo;

public class QuitMessage extends BasicMessage {

    private ServentInfo serventInfo;
    private int originalSenderPort;

    public QuitMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort ,ServentInfo serventInfo) {
        super(MessageType.QUIT, senderPort, receiverPort, senderIP, recieverIP);
        this.originalSenderPort = originalSenderPort;
        this.serventInfo = serventInfo;
    }

    public ServentInfo getServentInfo() {
        return serventInfo;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }
}
