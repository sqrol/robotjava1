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

            // Указывем в след. формате "Название массива" и его индекс
            put("MOV_IN_START_TO_CH2", 1);
            put("MOV_IN_CH1_TO_THIRD_RZ", 2);
            
            put("MOV_IN_THIRD_RZ_TO_CH1", 4);
            put("MOV_IN_CH1_TO_CON2", 5);
            put("MOV_IN_CON2_TO_CH1", 6);
            put("MOVE_IN_CH1_TO_FINISH", 7);
            put("MOV_IN_CH1_TO_THIRD_LOZ", 8);
            put("MOV_IN_THIRD_LOZ_TO_CH1", 9);
            put("MOV_IN_CH2_TO_CON4", 10);
            put("MOV_IN_CON4_TO_CH2", 11);
            put("MOVE_IN_CH2_TO_FINISH", 12);
            

            // put("AUTO_GRAB_UPPER", 16);
            // put("AUTO_GRAB_FRONT_LOWER_BRANCH", 17);
            // put("AUTO_GRAB_FRONT_MIDDLE_BRANCH", 18);
            // put("AUTO_GRAB_FRONT_TOP_BRANCH", 19);


            put("MOV_IN_START_TO_CH3", 24);
            put("MOV_IN_CH3_TO_FRIST_LZ", 25);
            put("MOV_IN_FRIST_LZ_TO_CH3", 26);
            put("MOV_IN_CH3_TO_CON4", 27);
            put("MOV_IN_CON4_TO_CH3", 28);
            put("MOVE_IN_CH3_TO_FINISH", 29);
            put("MOV_IN_CH3_TO_FRIST_LOZ", 30);
            put("MOV_IN_CH3_TO_FRIST_RZ", 31);
            put("MOV_IN_FRIST_RZ_TO_CH3", 32);
            put("MOV_IN_CH3_TO_CON3", 33);
            put("MOV_IN_CON3_TO_CH3", 34);
            put("MOV_IN_CH3_TO_CON2", 35);
            put("MOV_IN_CON2_TO_CH3", 36);
            put("MOV_IN_FRIST_LOZ_TO_CH3", 37);
            put("MOV_IN_CH3_TO_CON1", 38);
            put("MOV_IN_CH3_TO_CON1", 40);
            put("MOV_IN_CON1_TO_CH3", 41);
            put("MOV_IN_CH2_TO_CON1", 42);
            put("MOV_IN_FRIST_LOZ_TO_CH3", 37);
            put("MOV_IN_CH3_TO_CON1", 38);
            put("MOV_IN_CON3_TO_CH3", 34);
            put("MOV_IN_CH3_TO_CON2", 35);
            put("MOV_IN_CON2_TO_CH3", 36);
            put("MOV_IN_FRIST_LOZ_TO_CH3", 37);
            put("MOV_IN_CH3_TO_CON1", 38);
            put("MOV_IN_CH3_TO_FRIST_TZ", 39);
            put("MOV_IN_FRIST_TZ_TO_CH3", 69);
            put("MOV_IN_CH3_TO_SECOND_LOZ", 83);
            put("MOV_IN_CH3_TO_SECOND_TZ", 84);
            put("MOV_IN_CH3_TO_SECOND_RZ", 85);
            put("MOV_IN_CH3_TO_SECOND_LZ", 95);
            put("MOV_IN_CH3_TO_THIRD_LZ", 96);
            put("MOV_IN_CH3_TO_THIRD_LOZ", 100);
            put("MOV_IN_CH3_TO_THIRD_TZ", 101);
            put("MOV_IN_CH3_TO_THIRD_RZ", 102);
            put("AUTO_GRAB_UPPER", 214);
        }
    };
}