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
        } else if (args.contains(",")){
            String[] jobNames = args.split(",");
            System.out.println("Job Names: " + Arrays.toString(jobNames));

            int startedJobs = jobNames.length;
            int serventCount = AppConfig.SERVENT_COUNT;
            int position = 0;
            List<Integer> serventsSpread = new ArrayList<>();

            if (serventCount < startedJobs) {
                System.out.println("not enough servents");
            } else if (serventCount % startedJobs == 0) {
                for (int i = 0; i < startedJobs; i++) {
                    serventsSpread.add(serventCount / startedJobs);
                }
            } else {
                int m = startedJobs - (serventCount % startedJobs);
                int n = serventCount / startedJobs;
                for (int i = 0; i < startedJobs; i++) {
                    if (i >= m) {
                        serventsSpread.add(n + 1);
                    } else {
                        serventsSpread.add(n);
                    }
                }
            }
            System.out.println("SPREAD SERVENT: " + serventsSpread);//3 4

            int l = 0;
            for (String jobName : jobNames) {

                if (!AppConfig.jobNames.contains(jobName)) {
                    System.out.println(AppConfig.jobNames);
                    System.out.println("Job with that name does not exist: " + jobName);
                } else {
                    AppConfig.chordState.addStartedJobs(jobName);

                    int j = serventsSpread.get(l);
                    Map<Integer, List<Point>>  jobsForServentsByTheirID = JobSplit.split(jobName, j, position);
                    position += j;
                    l++;

                    System.out.println("FINAL MAP: " + jobsForServentsByTheirID);

                    //if (jobsForServentsByTheirID != null) {
                    //    Map.Entry<Integer, List<Point>> entry = jobsForServentsByTheirID.entrySet().iterator().next();

                    //if(!jobsForServentsByTheirID.keySet().contains(AppConfig.chordState.getNextNodePort())){
                    StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(),
                            AppConfig.chordState.getNextNodePort(),//AppConfig.chordState.getNextNodeForKey(entry.getKey()).getId(),
                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                            AppConfig.myServentInfo.getListenerPort(), jobName, jobsForServentsByTheirID);
                    //startMessage.setFractalCoords(jobsForServentsByTheirID);
                    MessageUtil.sendMessage(startMessage);

                }
                //}
            }
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
