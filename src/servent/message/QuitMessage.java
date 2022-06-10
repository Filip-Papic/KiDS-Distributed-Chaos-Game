package servent.message;

public class QuitMessage extends BasicMessage {
    public QuitMessage(MessageType type, int senderPort, int receiverPort, String senderIP, String recieverIP) {
        super(type, senderPort, receiverPort, senderIP, recieverIP);
    }

    public QuitMessage(MessageType type, int senderPort, int receiverPort, String senderIP, String recieverIP, String messageText) {
        super(type, senderPort, receiverPort, senderIP, recieverIP, messageText);
    }
}
