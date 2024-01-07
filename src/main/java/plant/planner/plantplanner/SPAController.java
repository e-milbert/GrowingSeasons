package plant.planner.plantplanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class SPAController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String direct() {
        return "forward:/";
    }


}
