package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author : pleier
 * @date : 2018/4/3
 */
public class SimpleExample {

    public void run(String job1, String group, String tigger) throws Exception {

        System.out.println("------- 初始化 ----------------------");

        // 首先要实例化scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        System.out.println("------- 初始化完成 -----------");

        // 获取给定时间的下一个完整分钟的时间，例如给定时间 08:13:54 则会反回 08:14:00
        Date runTime = DateBuilder.evenMinuteDate(new Date());
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 5);
        System.out.println("------- Job安排 -------------------");

        // 获取job实例
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(job1, group).build();

        // 在下一轮分钟触发运行
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tigger, group).withSchedule(simpleScheduleBuilder).build();

        // 告诉quartz使用某个trigger执行某个job
        scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " 将会运行于: " + runTime);

        // 启动scheduler
        scheduler.start();

        System.out.println("------- 开始安排 -----------------");
/*
        System.out.println("------- 等待65秒 -------------");*/
        //Thread.sleep(65L * 1000L);

       /* // 关闭scheduler
        System.out.println("------- 关闭 ---------------------");
        scheduler.shutdown(true);
        System.out.println("------- 关闭完成 -----------------");*/
    }

    public static void main(String[] args) throws Exception {
        SimpleExample example = new SimpleExample();
        example.run("job1", "group1", "tigger1");
        SimpleExample example1 = new SimpleExample();
        example1.run("job2", "group2", "tigger2");
        SimpleExample example2 = new SimpleExample();
        example2.run("job3", "group3", "tigger3");
        SimpleExample example3 = new SimpleExample();
        example3.run("job4", "group4", "tigger4");
    }
}