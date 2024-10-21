package com.priyajafari.multithreadproject.scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobTrigger {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    public void runJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addLong("uniqueness", System.currentTimeMillis()).toJobParameters());
            log.info("Job Execution Status: {}", jobExecution.getStatus());
        } catch (Exception e) {
            log.error("Job execution encountered an error: ", e);
        }
    }
}