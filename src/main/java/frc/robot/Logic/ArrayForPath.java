package frc.robot.Logic;

import java.util.HashMap;
import java.util.Map;

public class ArrayForPath { // Храним пути для автономки

    public static Integer getArrayForPath(String name) {
        return pathMap.getOrDefault(name, 100);
    }
    
    private static final Map<String, Integer> pathMap = new HashMap<>()
    {
        {
            put("Start", 0);

            // Указывем в след. формате "Название массива" и его индкес
            put("MOV_IN_START_TO_CH1", 1);
            put("MOV_IN_CH1_TO_THIRD_RZ", 2);
            put("MOV_IN_THIRD_RZ_TO_CH1", 3);
            put("MOV_IN_CH1_TO_CON2", 4);
            put("MOV_IN_CON2_TO_CH1", 5);
            put("MOVE_IN_CH1_TO_FINISH", 6);
            put("MOV_IN_CH1_TO_THIRD_LOZ", 7);
            put("MOV_IN_THIRD_LOZ_TO_CH2", 8);
            put("MOV_IN_CH2_TO_CON4", 9);
            put("MOV_IN_CON4_TO_CH2", 10);
            put("MOVE_IN_CH2_TO_FINISH", 11);
            put("MOV_IN_CH1_TO_THIRD_LZ", 12);

            put("AUTO_GRAB_UPPER", 16);
            put("AUTO_GRAB_FRONT_LOWER_BRANCH", 17);
            put("AUTO_GRAB_FRONT_MIDDLE_BRANCH", 18);
            put("AUTO_GRAB_FRONT_TOP_BRANCH", 19);
        }
    };
}