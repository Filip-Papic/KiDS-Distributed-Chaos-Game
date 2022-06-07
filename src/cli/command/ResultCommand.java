package cli.command;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import servent.message.Message;
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
        } else { //URADI I ZA FRAKTAL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //String[] argsSplit = args.split(" "); //PORUKE!!!
            //String jobName = argsSplit[0];
            String jobName = args;
            //if (argsSplit.length == 1) {
            ResultMessage resultMessage = new ResultMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                                                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), jobName);
            MessageUtil.sendMessage(resultMessage);

            //} else if (argsSplit.length == 2){
            //    int fractalID = Integer.parseInt(argsSplit[1]);

            //} else {
            //    System.out.println("Too many arguments");
            //}
        }
    }
}
