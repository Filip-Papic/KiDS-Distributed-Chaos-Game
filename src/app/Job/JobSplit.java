package app.Job;

import app.AppConfig;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JobSplit {

    public static Map<Integer, List<Point>> split(String jobName, Integer servents, int position) {
        Job job = AppConfig.jobNamesMap.get(jobName);
        int serventCount = AppConfig.chordState.getAllNodeInfo().size();//????
        int n = job.getJobPointNumber();
        Map<Integer, List<Point>> jobsForServentsByTheirID = new HashMap<>();
        int numberOfFractals = 3; //za sad
        int freeNodes = servents;

        System.out.println("STARTED: " + jobName);
        System.out.println("SERVENT COUNT: " + freeNodes);

        if(freeNodes == 0) {
            System.out.println("No free nodes!");
            return null;
        }else if(freeNodes == 1) {
            System.out.println("Not distributed, one node free!");
            int freeNodeID = -1;
            for(int i = 0; i < servents; i++) {
                if(AppConfig.chordState.getAllNodeInfo().get(i).isIdle()){
                    freeNodeID = AppConfig.chordState.getAllNodeInfo().get(i).getId();
                }
            }
            jobsForServentsByTheirID.put(freeNodeID, job.getJobCoordinates());
            return jobsForServentsByTheirID;
        }

        if(freeNodes == n) {
            jobsForServentsByTheirID = splitAgain(job, job.getJobCoordinates(), freeNodes, position);
        } else if(freeNodes > n) {
            jobsForServentsByTheirID = splitAgain(job, job.getJobCoordinates(), freeNodes, position);

            freeNodes -= n;
            int k = position;
            int h = position;
            if(freeNodes % (n-1) == 0) {
                List<Point> newPoints;
                Map<Integer, List<Point>> map;
                System.out.println("ima jos free nodova: " + freeNodes);

                while(freeNodes != 0) {

                    newPoints = jobsForServentsByTheirID.get(k);
                    jobsForServentsByTheirID.remove(k);

                    map = splitAgain(job, newPoints, freeNodes, 0);

                    jobsForServentsByTheirID.put(k, map.get(position));
                    map.remove(0);

                    for(int i = 1; i <= map.size(); i++){
                        jobsForServentsByTheirID.put(jobsForServentsByTheirID.size() + h, map.get(i));
                    }

                    k++;
                    freeNodes -= n-1;
                }
            } else {
                System.out.println("ima free nodova ali nisu potrebni pa su idle: " + freeNodes);
            }

        } else {
            int rest = n - freeNodes;
            int k = position;
            int h = position;
            if(rest % (n-1) == 0) {
                List<Point> newPoints;
                Map<Integer, List<Point>> map;
                System.out.println("ima jos free nodova: " + freeNodes);
                while (freeNodes != 0) {
                    newPoints = jobsForServentsByTheirID.get(k);
                    jobsForServentsByTheirID.remove(k);

                    map = splitAgain(job, newPoints, freeNodes, 0);
                    jobsForServentsByTheirID.put(k, map.get(position));//AppConfig.chordState.getValue(k)
                    map.remove(0);

                    for (int i = 1; i <= map.size(); i++) {
                        jobsForServentsByTheirID.put(jobsForServentsByTheirID.size() + h, map.get(i));//AppConfig.chordState.getValue(jobsForServentsByTheirID.size())
                    }

                    freeNodes -= n - 1;
                }
            } else {
                jobsForServentsByTheirID.put(0, job.getJobCoordinates());
                System.out.println(jobsForServentsByTheirID);
                int freeNodeID = -1;
                for(int i = 0; i < servents; i++) {
                    if(AppConfig.chordState.getAllNodeInfo().get(i).isIdle()){
                        freeNodeID = AppConfig.chordState.getAllNodeInfo().get(i).getId();
                    }
                }
                jobsForServentsByTheirID.put(freeNodeID, job.getJobCoordinates());
                return jobsForServentsByTheirID;
            }
        }

        System.out.println(jobsForServentsByTheirID.keySet());

        return jobsForServentsByTheirID;
    }

    private static Map<Integer, List<Point>> splitAgain(Job job, List<Point> startingCords, int freeNodes, int position) {
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

            int gg = i + position;
            map.put(gg, newPoints);//ID cvora i njegove kordinate za posao
            freeNodes--;
        }
        return map;
    }
}
