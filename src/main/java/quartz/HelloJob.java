package quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : pleier
 * @date : 2018/4/3
 */
public class HelloJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            System.out.println("Hello World! - " + jobExecutionContext.getJobDetail().getKey());
            System.out.println("Scheduler----" + jobExecutionContext.getScheduler());
            if (null != jobExecutionContext.getNextFireTime()) {
                System.out.println("NextFireTime()---" + new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss").format(jobExecutionContext.getNextFireTime()));
            }

            System.out.println("Key-----" + jobExecutionContext.getJobDetail().getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
