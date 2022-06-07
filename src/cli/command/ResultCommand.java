package cli.command;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import servent.message.Message;
import servent.message.ResultFractalMessage;
import servent.message.ResultMessage;
import servent.message.util.MessageUtil;

public class ResultCommand implements CLICommand{
    @Override
    public String commandName() {
        return "result";
    }

    @Override
    public void execute(String args) {
        if(args == null){
            System.out.println("Please enter job name and optional fractal ID");
        } else {
            String[] argsSplit = args.split(" ");
            if (argsSplit.length == 1) {
                String jobName = argsSplit[0];
                ResultMessage resultMessage = new ResultMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                        AppConfig.myServentInfo.getListenerPort(), jobName);
                MessageUtil.sendMessage(resultMessage);
            } else if (argsSplit.length == 2) {
                String jobName = argsSplit[0];
                int fractalID = Integer.parseInt(argsSplit[1]);
                ResultFractalMessage resultFractalMessage = new ResultFractalMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                        AppConfig.myServentInfo.getListenerPort(), jobName, fractalID);
                MessageUtil.sendMessage(resultFractalMessage);
            } else {
                System.out.println("Bad format for result command");
            }
        }
    }
}
