package plant.planner.plantplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ShutdownController {
    @Autowired
    private ConfigurableApplicationContext context;

    @GetMapping("/shutdown")
    public void shutdownApplication() {

        // Initiating graceful shutdown
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Logger.info("shutdown initialized");
                context.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.error(e);
            }
        }).start();

    }
}
