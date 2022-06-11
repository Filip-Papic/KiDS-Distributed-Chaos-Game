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
        int k = 0;
        for(Point point : startingCoords){
            //x[k] = point.x;
            //y[k] = point.y;
            //k++;
            x.add(point.x);
            y.add(point.y);
        }

        List<Point> newPoints = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) { //0_0
            int l = 1;
            int g = 1;
            while (g < x.size()) {
                System.out.println("x: " + x + " y: " + y + " for " + i + " size " + x.size() + " l " + l);
                Point point = new Point();

                point.x = (int) ((x.get(i) + x.get(i + l)) * job.getJobDistance());
                point.y = (int) ((y.get(i) + y.get(i + l)) * job.getJobDistance());

                newPoints.add(point);
                l++;
                g++;
                System.out.println("ADDED " + point + " l = " + l);
            }
            x.remove(i);
            y.remove(i);
            i--;
        }
        System.out.println("NEW POINTS SPLIT: " + newPoints);


        if(freeNodes > job.getJobPointNumber()) {

        }

        /*300,100,200,273,400,273
        100,446,200,273,300,446
        500,446,400,273,300,446*/

        List<Point> fractalServent1 = new ArrayList<>();
        List<Point> fractalServent0 = new ArrayList<>();
        List<Point> fractalServent3 = new ArrayList<>();

        fractalServent1.add(new Point(300,100));
        fractalServent1.add(new Point(200,273));
        fractalServent1.add(new Point(400,273));

        fractalServent0.add(new Point(100,446));
        fractalServent0.add(new Point(200,273));
        fractalServent0.add(new Point(300,446));

        fractalServent3.add(new Point(500,446));
        fractalServent3.add(new Point(400,273));
        fractalServent3.add(new Point(300,446));

        jobsForServentsByTheirID.put(1, fractalServent1);
        jobsForServentsByTheirID.put(0, fractalServent0);
        jobsForServentsByTheirID.put(2, fractalServent0);
        jobsForServentsByTheirID.put(3, fractalServent3);

        return jobsForServentsByTheirID;
    }
}
