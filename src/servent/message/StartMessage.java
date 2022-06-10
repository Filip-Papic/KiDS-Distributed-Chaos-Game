package servent.message;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class StartMessage extends BasicMessage{
    private String jobName;
    private int originalSenderPort;
    private Map<Integer, List<Point>> idJobMap;

    public StartMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort,
                        String messageText, Map<Integer, List<Point>> idJobMap) {
        super(MessageType.START, senderPort, receiverPort, senderIP, recieverIP, messageText);

        this.originalSenderPort = originalSenderPort;
        this.jobName = messageText;
        this.idJobMap = idJobMap;
    }

    public String getJobName() {
        return jobName;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }

    public Map<Integer, List<Point>> getIdJobMap() {
        return idJobMap;
    }
}
