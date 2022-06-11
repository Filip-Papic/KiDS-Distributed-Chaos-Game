package app.Job;

import app.AppConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JobResult {

    public static void getResult(String args){
        if(args.contains(" ")) {
            System.out.println("RESULT : " + args);

            JobImage jobImage = new JobImage();
            List<Point> jobResults = new ArrayList<>();

            String[] nameFractal = args.split(" ");
            String jobName = nameFractal[0];
            int fractalID = Integer.parseInt(nameFractal[1]);

            String newJobName = jobName + "_" + fractalID;

            for(String jbName : AppConfig.jobNamesMap.keySet()){
                if(jbName.equals(newJobName)){
                    System.out.println(AppConfig.jobNamesMap.keySet());
                    jobResults.addAll(AppConfig.jobNamesMap.get(jbName).getJobResults());
                }
            }
            Job job = AppConfig.jobNamesMap.get(newJobName);
            Job jobNew = new Job(job.getJobName(), job.getJobPointNumber(), job.getJobDistance(), job.getJobWidth(),
                    job.getJobHeight(), job.getJobCoordinates());
            jobNew.setJobResults(jobResults);

            jobImage.imageResult(jobNew);
        } else {
            System.out.println("RESULT : " + args);

            JobImage jobImage = new JobImage();
            List<Point> jobResults = new ArrayList<>();
            String jobName = args;

            for(String jbName : AppConfig.jobNamesMap.keySet()){
                if(jbName.startsWith(jobName)){
                    jobResults.addAll(AppConfig.jobNamesMap.get(jbName).getJobResults());
                }
            }
            Job job = AppConfig.jobNamesMap.get(jobName);
            Job jobNew = new Job(job.getJobName(), job.getJobPointNumber(), job.getJobDistance(), job.getJobWidth(),
                    job.getJobHeight(), job.getJobCoordinates());
            jobNew.setJobResults(jobResults);

            jobImage.imageResult(jobNew);
        }
    }
}
