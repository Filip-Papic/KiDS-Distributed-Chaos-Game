package app.Job;

import app.AppConfig;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JobSpread {

    public static void spreadOnServents(String[] jobNames, int startedJobs, int serventCount){
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
        Collections.sort(serventsSpread);
        System.out.println("SPREAD SERVENT: " + serventsSpread);//3 4

        int l = 0;
        for (String jobName : jobNames) {

            if (!AppConfig.jobNames.contains(jobName)) {
                System.out.println(AppConfig.jobNames);
                System.out.println("Job with that name does not exist: " + jobName);
            } else {

                int j = serventsSpread.get(l);
                Map<Integer, List<Point>> jobsForServentsByTheirID = JobSplit.split(jobName, j, position);
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
        }
    }
}
