package plant.planner.plantplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class PlantplannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantplannerApplication.class, args);


    }

}
