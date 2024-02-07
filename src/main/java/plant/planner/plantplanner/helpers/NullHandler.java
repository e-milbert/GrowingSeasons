package plant.planner.plantplanner.helpers;

import java.util.ArrayList;
import java.util.List;

public class NullHandler {

    public static <T> List<T> possibleNullFiltering(List<T> data) {
        int nullIndex = data.indexOf(null);
        List<T> nullFreeList = new ArrayList<>();
        if (nullIndex != -1) {
            for (int i = 0; i < nullIndex; i++) {
                nullFreeList.add(data.get(i));
            }
            return nullFreeList;
        }
        return data;
    }

}
