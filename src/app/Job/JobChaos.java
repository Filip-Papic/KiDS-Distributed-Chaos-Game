package app.Job;

import app.AppConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class JobChaos {

    private final int ITERATIONS = 10000;

    public void startJob(Job job){
        Random random = new Random();
        int n = job.getJobPointNumber();
        double p = job.getJobDistance();
        int w = job.getJobWidth();
        int h = job.getJobHeight();
        List<Point> initialPoints = new ArrayList<>();
        List<Point> points = job.getJobCoordinates();
        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            x[i] = Double.valueOf(point.getX()).intValue();
            y[i] = Double.valueOf(point.getY()).intValue();
        }

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(Color.black);
        g2d.fillRect(0, 0, w, h);

        //starting coordinates
        g2d.setPaint(Color.red);
        for(int xy = 0; xy < n; xy++){
            g2d.fillOval(x[xy], y[xy], 10, 10);
        }

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

        System.out.println("Start: " + startPoint);
        System.out.println("First: " + firstTargetPoint);
        currentPoint.x = (int) ((startPoint.x + firstTargetPoint.x) * p);
        currentPoint.y = (int) ((startPoint.y + firstTargetPoint.y) * p);
        System.out.println("Current: " + currentPoint);

        g2d.setPaint(Color.blue);
        g2d.fillOval(currentPoint.x, currentPoint.y, 10, 10);
        g2d.setPaint(Color.yellow);

        //others but with current point as target
        for(int k = 0; k < ITERATIONS; k++){
            startPoint = points.get(random.nextInt(points.size()));
            currentPoint.x = (int) ((startPoint.x + currentPoint.x) * p);
            currentPoint.y = (int) ((startPoint.y + currentPoint.y) * p);
            g2d.fillOval(currentPoint.x, currentPoint.y, 2, 2);
        }

        try {
            ImageIO.write(img, "png", new File("images\\" + job.getJobName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chaosGame(){

    }
}
