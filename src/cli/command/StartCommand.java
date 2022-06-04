package cli.command;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobResult;
import app.Job.NewJobCreator;

public class StartCommand implements CLICommand {

    public StartCommand() {
    }

    @Override
    public String commandName() {
        return "start";
    }

    @Override
    public void execute(String args) {//RADI KAD DODAM U MultiServentStarter
        if (args == null) {//unesi novi posao sam
            NewJobCreator newJobCreator = new NewJobCreator();
            newJobCreator.createJob();
        } else {
            if (!AppConfig.jobNames.contains(args)) {
                System.out.println(AppConfig.jobNames);
                System.out.println("Job with that name does not exist " + args);
            } else {
                Job job = null;
                for (Job jb : AppConfig.jobList) {
                    if (jb.getJobName().equals(args)) {
                        job = jb;
                    }
                }

            }
        }
    }
}
