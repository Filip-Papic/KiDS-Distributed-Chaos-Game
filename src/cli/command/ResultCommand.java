package cli.command;

import app.AppConfig;
import app.Job.JobResult;
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
            String jobName = args; //NEMA POENTE SLATI PORUKE ZA RESULT SAMO IZVUCI IZ MAPE

            JobResult.getResult(jobName);
            /*
            ResultMessage resultMessage = new ResultMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                    AppConfig.myServentInfo.getListenerPort(), jobName);
            MessageUtil.sendMessage(resultMessage);
            */
        }
    }
}
