package com.sicredi.votesms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class VotesMsApplication {

	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		SpringApplication.run(VotesMsApplication.class, args);
	}



}
