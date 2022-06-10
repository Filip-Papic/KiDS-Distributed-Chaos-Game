package cli.command;

import app.AppConfig;
import app.Job.*;
import servent.message.CreateJobMessage;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Job job = AppConfig.jobNamesMap.get(jobName);

                Map<Integer, List<Point>> jobsForServentsByTheirID = JobSplit.split(job);

                StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                                                             AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                                                             AppConfig.myServentInfo.getListenerPort(), jobName, jobsForServentsByTheirID);
                //startMessage.setFractalCoords(jobsForServentsByTheirID);
                MessageUtil.sendMessage(startMessage);
            }
        }
    }
}
