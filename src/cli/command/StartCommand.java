package cli.command;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import app.Job.JobResult;
import app.Job.NewJobCreator;
import servent.message.CreateJobMessage;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

public class StartCommand implements CLICommand {

    public StartCommand() {
    }

    @Override
    public String commandName() {
        return "start";
    }

    @Override
    public void execute(String args) {//RADI KAD DODAM U MultiServentStarter
        if (args == null) {//unesi novi posao sam
            CreateJobMessage createJobMessage = new CreateJobMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP());
            MessageUtil.sendMessage(createJobMessage);
        } else {
            if (!AppConfig.jobNames.contains(args)) {
                System.out.println(AppConfig.jobNames);
                System.out.println("Job with that name does not exist: " + args);
            } else {
                String jobName = args;
                StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                                                             AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), jobName);
                MessageUtil.sendMessage(startMessage);
            }
        }
    }
}
