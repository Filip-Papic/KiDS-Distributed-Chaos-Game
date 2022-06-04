package app.Job;

import app.AppConfig;
import app.Job.Job;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class NewJobCreator {

    public Job createJob() {
        Scanner scanner = new Scanner(System.in);
        //AppConfig.newJobFlag = true;
        Job job = null;
        try {
            System.out.println("Enter job name:");
            String jobName = scanner.nextLine();
            if (AppConfig.jobNames != null) {
                while (AppConfig.jobNames.contains(jobName)) {
                    System.out.println("Job name already exists. Enter new one: ");
                    jobName = scanner.nextLine();
                }
            }
            System.out.println("Enter number of fractal points: ");
            int n = scanner.nextInt();
            while (n < 3 || n > 10) {
                System.out.println("Must be between 3 and 10. Enter new one: ");
                n = scanner.nextInt();
            }
            System.out.println("Enter job distance: ");
            double p = scanner.nextDouble();
            while (p < 0 || p > 1) {
                System.out.println("Must be between 0 and 1. Enter new one: ");
                p = scanner.nextDouble();
            }
            System.out.println("Enter width (integer): ");
            int w = scanner.nextInt();
            System.out.println("Enter height (integer): ");
            int h = scanner.nextInt();
            System.out.println("Enter point coordinates. Use comma after each: ");
            scanner.nextLine();//izbacuje null bez ovoga
            String a = scanner.nextLine();
            String[] aSplit = a.split(",");
            while (!(aSplit.length % 2 == 0)) {
                System.out.println("Coordinates must be in pairs (x,y). Try again: ");
                a = scanner.nextLine();
                aSplit = a.split(",");
            }
            List<Point> coords = new ArrayList<>();
            for (int i = 0; i < aSplit.length; i += 2) {
                try {
                    coords.add(new Point(Integer.parseInt(aSplit[i]), Integer.parseInt(aSplit[i + 1])));
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect format");
                }
            }

            job = new Job(jobName, n, p, w, h, coords);
            AppConfig.jobList.add(job);
            AppConfig.jobNames.add(jobName);
            System.out.println(job);
            System.out.println("Current available jobs: " + AppConfig.jobNames);
            //AppConfig.newJobFlag = false;
        } catch (InputMismatchException e) {
            System.out.println("Wrong type of input");
        }

        return job;
    }
}

