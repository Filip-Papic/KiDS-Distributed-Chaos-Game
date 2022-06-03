package cli.command;

import app.AppConfig;
import app.Job;
import cli.CLIParser;
import servent.SimpleServentListener;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StartCommand implements CLICommand {

    public StartCommand() {
    }

    @Override
    public String commandName() {
        return "start";
    }

    @Override
    public void execute(String args) {//RADI KAD DODAM U MultiServentStarter
        if(args == null){//unesi novi posao sam
            Scanner scanner = new Scanner(System.in);
            //AppConfig.newJobFlag = true;
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
                while(!(aSplit.length % 2 == 0)){
                    System.out.println("Coordinates must be in pairs (x,y). Try again: ");
                    a = scanner.nextLine();
                    aSplit = a.split(",");
                }
                List<Point> coords = new ArrayList<>();
                for (int i = 0; i < aSplit.length; i += 2) {
                    try {
                        coords.add(new Point(Integer.parseInt(aSplit[i]), Integer.parseInt(aSplit[i + 1])));
                    } catch (NumberFormatException e){}
                }

                Job job = new Job(jobName, n, p, w, h, coords);
                AppConfig.jobList.add(job);
                System.out.println(job);
                //AppConfig.newJobFlag = false;
            } catch (InputMismatchException e){}
        } else {
            if(!AppConfig.jobNames.contains(args)){
                System.out.println(AppConfig.jobList);
                System.out.println(AppConfig.jobNames);
                System.out.println("Job with that name does not exist " + args);
            } else {
                System.out.println("ima ga bolan");
            }
        }
    }
}
