package com.pponcet.adventofcode;


import com.pponcet.adventofcode.day10.Star10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventofcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventofcodeApplication.class, args);
		Star10.execute();
	}

}
