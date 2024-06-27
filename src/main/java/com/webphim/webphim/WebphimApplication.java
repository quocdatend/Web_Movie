package com.webphim.webphim;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WebphimApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebphimApplication.class, args);
	}
	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", "dap6ivvwp");
		config.put("api_key", "875469923979388");
		config.put("api_secret", "sT_lEC69UilqcB6NB6Fhn6kaZqU");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}
}
