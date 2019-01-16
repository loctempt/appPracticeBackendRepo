package apii.application_practice_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ApplicationPractice2Application {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationPractice2Application.class, args);
    }

}

