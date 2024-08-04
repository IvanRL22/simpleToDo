package com.ivanrl.simpleToDo;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class SimpleToDoApplication {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsername;
	@Value("${spring.datasource.password}")
	private String dbPassword;

	public static void main(String[] args) {
		SpringApplication.run(SimpleToDoApplication.class, args);
	}

	@Bean
	DataSource dataSource() {
		var ds = new PGSimpleDataSource();
		ds.setURL(dbUrl);
		ds.setUser(dbUsername);
		ds.setPassword(dbPassword);
		return ds;
	}

}
