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

public class JobChaos implements Runnable {

    //private final int ITERATIONS = 10000;
    private List<Point> resultPoints = new CopyOnWriteArrayList<>();

    @Override
    public void run() {

    }

    public void startJob(Job job){ //static
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

        currentPoint.x = (int) ((startPoint.x + firstTargetPoint.x) * p);
        currentPoint.y = (int) ((startPoint.y + firstTargetPoint.y) * p);
        System.out.println("Start: " + startPoint);
        System.out.println("First: " + firstTargetPoint);
        System.out.println("Current: " + currentPoint);

        resultPoints.add(new Point(currentPoint.x, currentPoint.y));

        //others but with current point as target
        int ITERATIONS = 10000;
        for(int k = 0; k < ITERATIONS; k++){
            startPoint = points.get(random.nextInt(points.size()));
            currentPoint.x = (int) ((startPoint.x + currentPoint.x) * p);
            currentPoint.y = (int) ((startPoint.y + currentPoint.y) * p);
            resultPoints.add(new Point(currentPoint.x, currentPoint.y));
        }

        job.setJobResults(resultPoints);
        //AppConfig.jobResultPoints = resultPoints;
        //chaosResult(job);
    }

    public void chaosResult(Job job) {
        List<Point> resultPoints = job.getJobResults();
        int w = job.getJobWidth();
        int h = job.getJobHeight();
        int n = job.getJobPointNumber();
        List<Point> points = job.getJobCoordinates();
        int[] x = new int[n];
        int[] y = new int[n];

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(Color.black);
        g2d.fillRect(0, 0, w, h);

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            x[i] = Double.valueOf(point.getX()).intValue();
            y[i] = Double.valueOf(point.getY()).intValue();
        }

        //starting coordinates
        g2d.setPaint(Color.red);
        for(int xy = 0; xy < n; xy++){
            g2d.fillOval(x[xy], y[xy], 10, 10);
        }

        //all other
        g2d.setPaint(Color.yellow);
        for(Point pnt : resultPoints){
            g2d.fillOval(pnt.x, pnt.y, 2, 2);
        }

        try {
            ImageIO.write(img, "png", new File("images\\" + job.getJobName() + ".png"));
            System.out.println("Created image: " + job.getJobName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
