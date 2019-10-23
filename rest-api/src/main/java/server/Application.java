package server;

import kotlin.Suppress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"controller","database","init","helper","config","tool"})
@EnableJpaRepositories("database")
@EntityScan("database")
@EnableCaching
@Suppress(names = "UNCHECKED_CAST")
@EnableScheduling
@EnableMongoRepositories("database")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
