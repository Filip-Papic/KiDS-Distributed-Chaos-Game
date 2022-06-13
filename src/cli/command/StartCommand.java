package cli.command;

import app.AppConfig;
import app.Job.*;
import cli.CLIParser;
import servent.message.CreateJobMessage;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

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
            NewJobCreator newJobCreator = new NewJobCreator();
            newJobCreator.createJob(CLIParser.sc);
            //CreateJobMessage createJobMessage = new CreateJobMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
            //        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP());
            //MessageUtil.sendMessage(createJobMessage);
        } else {
            if (!AppConfig.jobNames.contains(args)) {
                System.out.println(AppConfig.jobNames);
                System.out.println("Job with that name does not exist: " + args);
            } else {
                String jobName = args;

                Map<Integer, List<Point>> jobsForServentsByTheirID = JobSplit.split(jobName);

                StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                                                             AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                                                             AppConfig.myServentInfo.getListenerPort(), jobName, jobsForServentsByTheirID);
                //startMessage.setFractalCoords(jobsForServentsByTheirID);
                MessageUtil.sendMessage(startMessage);
            }
        }
    }
}
