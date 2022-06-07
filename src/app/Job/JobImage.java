package app.Job;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JobImage {

    //public void imageResult(Map<Job, List<Point>> map) {
    public void imageResult(Job job) {
        try {
            //Job job = map.keySet().toArray()[0];
            //List<Point> resultPoints = map.get(job);
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
            for (int xy = 0; xy < n; xy++) {
                g2d.fillOval(x[xy], y[xy], 10, 10);
            }

            //all other
            g2d.setPaint(Color.yellow);
            for (Point pnt : resultPoints) {
                g2d.fillOval(pnt.x, pnt.y, 2, 2);
            }

            try {
                ImageIO.write(img, "png", new File("images\\" + job.getJobName() + ".png"));
                System.out.println("Created image: " + job.getJobName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public void imageResult(Map<Job, List<Point>> map) {
    public void imageFractalIDResult(Job job, int fractalID) { //nesto nzm
        try {
            //Job job = map.keySet().toArray()[0];
            //List<Point> resultPoints = map.get(job);
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
            for (int xy = 0; xy < n; xy++) {
                g2d.fillOval(x[xy], y[xy], 10, 10);
            }

            //all other
            g2d.setPaint(Color.yellow);
            for (Point pnt : resultPoints) {
                g2d.fillOval(pnt.x, pnt.y, 2, 2);
            }

            try {
                ImageIO.write(img, "png", new File("images\\" + job.getJobName() + ".png"));
                System.out.println("Created image: " + job.getJobName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
