package app.Job;

import app.AppConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobSplit {

    public static Map<Integer, List<Point>> split(Job job) {
        int serventCount = AppConfig.chordState.getAllNodeInfo().size();
        int pointsCount = job.getJobPointNumber();

        //job split
        Map<Integer, List<Point>> jobsForServentsByTheirID = new HashMap<>();
        List<Point> startingCoords = job.getJobCoordinates();

        int numberOfFractals = 3; //za sad

                /*300,100,100,213,400,213
                100,446,100,213,300,446
                500,446,400,213,300,446*/

        List<Point> fractalServent1 = new ArrayList<>();
        List<Point> fractalServent0 = new ArrayList<>();
        List<Point> fractalServent3 = new ArrayList<>();

        fractalServent1.add(new Point(300,100));
        fractalServent1.add(new Point(100,213));
        fractalServent1.add(new Point(400,213));

        fractalServent0.add(new Point(100,446));
        fractalServent0.add(new Point(100,213));
        fractalServent0.add(new Point(300,446));

        fractalServent3.add(new Point(500,446));
        fractalServent3.add(new Point(400,213));
        fractalServent3.add(new Point(300,446));

        jobsForServentsByTheirID.put(1, fractalServent1);
        jobsForServentsByTheirID.put(0, fractalServent0);
        jobsForServentsByTheirID.put(2, fractalServent0);
        jobsForServentsByTheirID.put(3, fractalServent3);

        return jobsForServentsByTheirID;
    }
}
