package br.com.gokustore.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.com"})
@ComponentScan(basePackages = {"br.com"})
@EnableJpaRepositories("br.com")
public class GokuUserApp {
	public static void main( String[] args ){
    	SpringApplication.run(GokuUserApp.class, args);
    }
}
