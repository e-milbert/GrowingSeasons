package plant.planner.plantplanner.helpers;


import java.util.UUID;

public class BusinessKeyGen {

    public static String generateBusinessKey() {

        return UUID.randomUUID().toString();
    }
}
