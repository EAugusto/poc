package com.eaugusto.poc.programmaticscheduling;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@SpringBootApplication
public class PocApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(2);
		scheduler.initialize();
		scheduler.schedule(() -> { 
			try {Thread.sleep(10000l);} catch (InterruptedException e) {throw new RuntimeException(e);} 
			System.out.println("The clock A says: " + LocalDateTime.now());
		}, new CronTrigger("0/5 * * * * ?"));
		scheduler.schedule(() -> { 
			throw new RuntimeException("EXCEPTION THROWN");
		}, new CronTrigger("0/5 * * * * ?"));
		scheduler.setErrorHandler(t -> System.out.println(t.getMessage()));
	}
}