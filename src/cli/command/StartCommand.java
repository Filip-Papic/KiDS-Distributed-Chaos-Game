package cli.command;

import app.AppConfig;
import app.Job.*;
import cli.CLIParser;
import servent.message.CreateJobMessage;
import servent.message.JobStartedMessage;
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
        /*AppConfig.chordState.incrementStartedJobs();
        AppConfig.chordState.setStartedJobName(args);

        JobStartedMessage jsm = new JobStartedMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),  AppConfig.myServentInfo.getListenerPort(),
                AppConfig.flag);
        MessageUtil.sendMessage(jsm);

        System.out.println("Waiting...");
        while(!AppConfig.chordState.isStartedJobsBool()){
            try {
                System.out.println(AppConfig.chordState.isStartedJobsBool());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done waiting");

        if (AppConfig.chordState.getStartedJobs().get() > 1){
        */
        if (args == null) {//unesi novi posao sam
            NewJobCreator newJobCreator = new NewJobCreator();
            newJobCreator.createJob(CLIParser.sc);
            //CreateJobMessage createJobMessage = new CreateJobMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
            //        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP());
            //MessageUtil.sendMessage(createJobMessage);
        } else if (args.contains(",")){
            String[] jobNames = args.split(",");
            System.out.println("Job Names: " + Arrays.toString(jobNames));

            int startedJobs = jobNames.length;
            int serventCount = AppConfig.SERVENT_COUNT;

            JobSpread.spreadOnServents(jobNames, startedJobs, serventCount);

        } else {
            String jobName = args;

            Map<Integer, List<Point>> jobsForServentsByTheirID = JobSplit.split(jobName, AppConfig.SERVENT_COUNT, 0);

            StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                    AppConfig.myServentInfo.getListenerPort(), jobName, jobsForServentsByTheirID);
            //startMessage.setFractalCoords(jobsForServentsByTheirID);
            MessageUtil.sendMessage(startMessage);
        }
    }
}
