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
        //int[] x = new int[n];
        //int[] y = new int[n];
        //int[] newXY = new int[n^2];

        int numberOfFractals = 3; //za sad
        int freeNodes = AppConfig.SERVENT_COUNT; //ovde treba neka logika za vise poslova u isto vreme

        System.out.println("SERVENT COUNT: " + freeNodes);

        if(freeNodes == 0) {
            System.out.println("No free nodes!");
            return null;
        }else if(freeNodes == 1) {
            System.out.println("Not distributed, one node free!");
            //jobsForServentsByTheirID.put(freeNodeID, job); //izvuci ID od nekog slobodnog cvora
            //return jobsForServentsByTheirID;
        }

        if(freeNodes > n) {
            jobsForServentsByTheirID = splitAgain(job, job.getJobCoordinates(), freeNodes);

            freeNodes -= n;
            int k = 0;
            if(freeNodes % (n-1) == 0) {
                List<Point> newPoints;
                Map<Integer, List<Point>> map;
                System.out.println("ima jos free nodova: " + freeNodes);

                while(freeNodes != 0) {

                    newPoints = jobsForServentsByTheirID.get(k);
                    jobsForServentsByTheirID.remove(k);

                    System.out.println("NEW MAP SIZE: " + jobsForServentsByTheirID.keySet());

                    map = splitAgain(job, newPoints, freeNodes);
                    jobsForServentsByTheirID.put(k, map.get(0));
                    map.remove(0);

                    for(int i = 1; i <= map.size(); i++){
                        jobsForServentsByTheirID.put(jobsForServentsByTheirID.size(), map.get(i));
                    }

                    k++;
                    freeNodes -= n-1;
                }
            } else {
                System.out.println("ima free nodova ali nisu potrebni pa su idle: " + freeNodes);
            }

        } else {
            int rest = n - freeNodes;
            int k = 0;
            if(rest % (n-1) == 0) {
                List<Point> newPoints;
                Map<Integer, List<Point>> map;
                System.out.println("ima jos free nodova: " + freeNodes);
                while (freeNodes != 0) {
                    newPoints = jobsForServentsByTheirID.get(k);
                    jobsForServentsByTheirID.remove(k);

                    System.out.println("NEW MAP SIZE: " + jobsForServentsByTheirID.keySet());

                    map = splitAgain(job, newPoints, freeNodes);
                    jobsForServentsByTheirID.put(k, map.get(0));//AppConfig.chordState.getValue(k)

                    map.remove(0);

                    for (int i = 1; i <= map.size(); i++) {
                        jobsForServentsByTheirID.put(jobsForServentsByTheirID.size(), map.get(i));//AppConfig.chordState.getValue(jobsForServentsByTheirID.size())
                    }

                    freeNodes -= n - 1;
                }
            } else {
                jobsForServentsByTheirID.put(0, job.getJobCoordinates());
                System.out.println(jobsForServentsByTheirID);
                //jobsForServentsByTheirID.put(freeNodeID, job); //izvuci ID od nekog slobodnog cvora
                return jobsForServentsByTheirID;
            }
        }

        System.out.println(jobsForServentsByTheirID.keySet());

        return jobsForServentsByTheirID;
    }

    private static Map<Integer, List<Point>> splitAgain(Job job, List<Point> startingCords, int freeNodes) {
        Map<Integer, List<Point>> map = new HashMap<>();
        int n = job.getJobPointNumber();
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();

        for(Point point : startingCords){
            x.add(point.x);
            y.add(point.y);
        }

        for (int i = 0; i < x.size(); i++) { //0_0
            List<Point> newPoints = new ArrayList<>();
            int l = 1;
            int g = 1;

            if (i > 0) {
                Collections.swap(x, 0, i);
                Collections.swap(y, 0, i);
            }

            newPoints.add(startingCords.get(i));

            while (g < x.size()) {

                Point point = new Point();

                point.x = (int) (x.get(0) + job.getJobDistance() * (x.get(l) - x.get(0)));
                point.y = (int) (y.get(0) + job.getJobDistance() * (y.get(l) - y.get(0)));

                newPoints.add(point);
                l++;
                g++;
            }

            map.put(i, newPoints);//ID cvora i njegove kordinate za posao
            freeNodes--;
        }
        return map;
    }
}
