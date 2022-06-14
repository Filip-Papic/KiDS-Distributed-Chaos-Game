package app.Job;

import app.AppConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobChaos implements Runnable {

    //private final int ITERATIONS = 10000;
    private AtomicBoolean sleep;
    public static List<Point> statusPoints = new ArrayList<>();

    @Override
    public void run() {
        while(true) {
            if (sleep.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startJob(Job job){ //static
        List<Point> resultPoints = new ArrayList<>();//CopyOnWriteArrayList<>();
        Random random = new Random();
        int n = job.getJobPointNumber();
        double p = job.getJobDistance();
        int w = job.getJobWidth();
        int h = job.getJobHeight();
        List<Point> points = job.getJobCoordinates();
        int[] x = new int[n];
        int[] y = new int[n];

        //chaos game
        //first new point
        Point currentPoint = new Point();
        Point startPoint = points.get(random.nextInt(points.size()));//jedna od svih
        List<Point> pointsTemp = new ArrayList<>();
        for (Point pnt: points){
            if(pnt != startPoint){
                pointsTemp.add(pnt);
            }
        }
        Point firstTargetPoint = pointsTemp.get(random.nextInt(pointsTemp.size()));//jedna od ostalih

        //currentPoint.x = (int) ((startPoint.x + firstTargetPoint.x) * p);
        //currentPoint.y = (int) ((startPoint.y + firstTargetPoint.y) * p);
        currentPoint.x = (int) ((firstTargetPoint.x - startPoint.x) * p + startPoint.x);
        currentPoint.y = (int) ((firstTargetPoint.y - startPoint.y) * p + startPoint.y);
        System.out.println("Start: " + startPoint);
        System.out.println("First: " + firstTargetPoint);
        System.out.println("Current: " + currentPoint);

        resultPoints.add(new Point(currentPoint.x, currentPoint.y));

        //others but with current point as target
        int ITERATIONS = 10000;
        for(int k = 0; k < ITERATIONS; k++){
            startPoint = points.get(random.nextInt(points.size()));
            //currentPoint.x = (int) ((startPoint.x + currentPoint.x) * p);//mod?
            //currentPoint.y = (int) ((startPoint.y + currentPoint.y) * p);
            currentPoint.x = (int) ((currentPoint.x - startPoint.x) * p + startPoint.x);
            currentPoint.y = (int) ((currentPoint.y - startPoint.y) * p + startPoint.y);
            resultPoints.add(new Point(currentPoint.x, currentPoint.y));
        }

        job.setJobResults(resultPoints);
        statusPoints = resultPoints;

        Map<Job, List<Point>> map = new HashMap<>();
        map.put(job, resultPoints);
        AppConfig.jobNameResultsMap.put(job.getJobName(), map);//mape: ime, job, result
        System.out.println("JOB " + job.getJobName() +  " DONE");
    }
}
