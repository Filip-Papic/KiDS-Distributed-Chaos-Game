package app.Job;

import app.AppConfig;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JobSplit {

    public static Map<Integer, List<Point>> split(String jobName) {
        Job job = AppConfig.jobNamesMap.get(jobName);
        int serventCount = AppConfig.chordState.getAllNodeInfo().size();//????
        int n = job.getJobPointNumber();
        Map<Integer, List<Point>> jobsForServentsByTheirID = new HashMap<>();
        List<Point> startingCoords = job.getJobCoordinates();
        //int[] x = new int[n];
        //int[] y = new int[n];
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        int[] newXY = new int[n^2];

        int numberOfFractals = 3; //za sad
        int freeNodes = AppConfig.SERVENT_COUNT; //ovde treba neka logika za vise fraktala u isto vreme

        if(freeNodes == 0) {
            System.out.println("No free nodes!");
        }else if(freeNodes == 1) {
            System.out.println("Not distributed, one node free!");
            //jobsForServentsByTheirID.put(freeNode, job); //tako nesto
            //return jobsForServentsByTheirID;
        }

        for(Point point : startingCoords){
            x.add(point.x);
            y.add(point.y);
        }

        for (int i = 0; i < x.size(); i++) { //0_0 veselo
            List<Point> newPoints = new ArrayList<>();
            int l = 1;
            int g = 1;

            if(i > 0) {
                Collections.swap(x, 0, i);
                Collections.swap(y, 0, i);
            }

            newPoints.add(startingCoords.get(i));

            while (g < x.size()) {

                Point point = new Point();

                point.x = (int) ((x.get(0) + x.get(l)) * job.getJobDistance());
                point.y = (int) ((y.get(0) + y.get(l)) * job.getJobDistance());

                newPoints.add(point);
                l++;
                g++;
            }

            jobsForServentsByTheirID.put(i, newPoints);//ID cvora i njegove kordinate za posao
        }

        System.out.println(jobsForServentsByTheirID.keySet());

        if(freeNodes > job.getJobPointNumber()) {

        }

        /*300,100,200,273,400,273
        100,446,200,273,300,446
        500,446,400,273,300,446*/
        return jobsForServentsByTheirID;
    }
}
