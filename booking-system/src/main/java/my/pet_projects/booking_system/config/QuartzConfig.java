package my.pet_projects.booking_system.config;

import my.pet_projects.booking_system.scheduler.BookingCompletionJob;
import my.pet_projects.booking_system.scheduler.BookingNotificationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail notificationJobDetail() {
        return JobBuilder
                .newJob(BookingNotificationJob.class)
                .withIdentity("bookingNotificationJob")
                .storeDurably()
                .build();

    }

    @Bean
    public Trigger notificationTrigger() {
        SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .withIdentity("bookingNotificationTrigger")
                .withSchedule(schedule)
                .forJob(notificationJobDetail())
                .build();
    }

    @Bean
    public JobDetail completionBookingJobDetail() {
        return JobBuilder
                .newJob(BookingCompletionJob.class)
                .withIdentity("completionBookingJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger completionBookingTrigger() {
        SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .withSchedule(schedule)
                .withIdentity("completionBookingTrigger")
                .forJob(completionBookingJobDetail())
                .build();
    }
}
