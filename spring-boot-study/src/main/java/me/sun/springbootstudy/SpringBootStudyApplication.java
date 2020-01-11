package me.sun.springbootstudy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootStudyApplication {

    private static final String PROPERTICES
            = "spring.config.location="
            + "classpath:/application.yml"
            + ",/app/config/springboot/tokenInformation.yml";


    public static void main(String[] args) {
//        SpringApplication.run(SpringBootStudyApplication.class, args);
        new SpringApplicationBuilder(SpringBootStudyApplication.class)
                .properties(PROPERTICES)
                .run(args);
    }

}
