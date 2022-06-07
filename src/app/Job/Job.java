package app.Job;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class Job implements Serializable {

    private final String jobName;
    private final int jobPointNumber;
    private final double jobDistance;
    private final int jobWidth;
    private final int jobHeight;
    private final List<Point> jobCoordinates;
    private List<Point> jobResults; //mapa fractal id, result

    public Job(String name, int n, double p, int w, int h, List<Point> aa) {
        this.jobName = name;
        this.jobPointNumber = n;
        this.jobDistance = p;
        this.jobWidth = w;
        this.jobHeight = h;
        this.jobCoordinates = aa;
    }

    public List<Point> getJobResults() {
        return jobResults;
    }

    public void setJobResults(List<Point> jobResults) {
        this.jobResults = jobResults;
    }

    public String getJobName() {
        return jobName;
    }

    public int getJobPointNumber() {
        return jobPointNumber;
    }

    public double getJobDistance() {
        return jobDistance;
    }

    public int getJobWidth() {
        return jobWidth;
    }

    public int getJobHeight() {
        return jobHeight;
    }

    public List<Point> getJobCoordinates() {
        return jobCoordinates;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobName='" + jobName + '\'' +
                ", jobPointNumber=" + jobPointNumber +
                ", jobDistance=" + jobDistance +
                ", jobWidth=" + jobWidth +
                ", jobHeight=" + jobHeight +
                ", jobCoordinates=" + jobCoordinates +
                '}';
    }
}
