package plant.planner.plantplanner.helpers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CMDHelper implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", "http://localhost:8080"});
    }

}
