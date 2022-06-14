package cli.command;

import app.AppConfig;
import servent.message.StatusMessage;
import servent.message.util.MessageUtil;

import java.util.HashMap;

public class StatusCommand implements CLICommand {

    @Override
    public String commandName() {
        return "status";
    }

    @Override
    public void execute(String args) {
        if(args == null){
            StatusMessage statusMessage = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),  AppConfig.myServentInfo.getListenerPort(),
                    null, -1, new HashMap<>());
            MessageUtil.sendMessage(statusMessage);
        }else if (args.contains(" ")) {
            String[] jobFractal = args.split(" ");
            String jobName = jobFractal[0];
            int fractalID = Integer.parseInt(jobFractal[1]);

            StatusMessage statusMessage = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),  AppConfig.myServentInfo.getListenerPort(),
                    jobName, fractalID, new HashMap<>());
            MessageUtil.sendMessage(statusMessage);
        } else {
            String jobName = args;

            StatusMessage statusMessage = new StatusMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),  AppConfig.myServentInfo.getListenerPort(),
                    jobName, -1, new HashMap<>());
            MessageUtil.sendMessage(statusMessage);
        }
    }
}
