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
            put("MOV_IN_START_TO_CH1", 1);
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
            put("MOV_IN_CH3_TO_THIRD_LZ", 99);
            put("MOV_IN_CH3_TO_THIRD_LOZ", 100);
            put("MOV_IN_CH3_TO_THIRD_TZ", 101);
            put("MOV_IN_CH3_TO_THIRD_RZ", 102);
            put("MOV_IN_FRIST_LZ_TO_CON4", 116);
            put("AUTO_GRAB_UPPER", 214);
            put("MOV_IN_FRIST_RZ_TO_FRIST_TZ", 215);
            put("MOV_IN_FRIST_TZ_TO_FRIST_LZ", 216);
            put("MOV_IN_THIRD_TZ_TO_THIRD_LZ", 213);
            put("MOV_IN_THIRD_RZ_TO_THIRD_TZ", 212);
            put("MOV_IN_SECOND_TZ_TO_SECOND_LZ", 211);
            put("MOV_IN_SECOND_RZ_TO_SECOND_TZ", 210);
            put("MOV_IN_THIRD_TZ_TO_THIRD_LZ", 209);
            put("MOV_IN_THIRD_RZ_TO_THIRD_TZ", 208);
            put("MOV_IN_SECOND_LZ_TO_CON4", 169);
            put("MOV_IN_SECOND_LZ_TO_CON2", 168);
            put("MOV_IN_SECOND_LZ_TO_CON1", 167);
            put("MOV_IN_SECOND_LZ_TO_CON3", 166);
            put("MOV_IN_SECOND_LOZ_TO_CON2", 165);
            put("MOV_IN_SECOND_TZ_TO_CON2", 164);
            put("MOV_IN_SECOND_TZ_TO_CON1", 163);
            put("MOV_IN_SECOND_LOZ_TO_CON1", 162);
            put("MOV_IN_SECOND_LOZ_TO_CON4", 161);
            put("MOV_IN_SECOND_TZ_TO_CON4", 160);
            put("MOV_IN_SECOND_LOZ_TO_CON3", 159);
            put("MOV_IN_SECOND_TZ_TO_CON3", 158);
            put("MOV_IN_SECOND_RZ_TO_CON3", 157);
            put("MOV_IN_SECOND_RZ_TO_CON4", 156);
            put("MOV_IN_SECOND_RZ_TO_CON1", 155);
            put("MOV_IN_SECOND_RZ_TO_CON2", 154);
            put("MOV_IN_SECOND_RZ_TO_CON3", 153);
            put("MOV_IN_THIRD_LZ_TO_CON1", 152);
            put("MOV_IN_THIRD_LZ_TO_CON2", 151);
            put("MOV_IN_THIRD_LZ_TO_CON4", 150);
            put("MOV_IN_THIRD_LZ_TO_CON3", 149);
            put("MOV_IN_THIRD_LOZ_TO_CON1", 148);
            put("MOV_IN_THIRD_TZ_TO_CON1", 147);
            put("MOV_IN_THIRD_TZ_TO_CON2", 146);
            put("MOV_IN_THIRD_LOZ_TO_CON2", 145);
            put("MOV_IN_THIRD_LOZ_TO_CON4", 144);
            put("MOV_IN_THIRD_TZ_TO_CON4", 143);
            put("MOV_IN_THIRD_TZ_TO_CON3", 142);
            put("MOV_IN_THIRD_LOZ_TO_CON3", 141);
            put("MOV_IN_THIRD_RZ_TO_CON2", 140);
            put("MOV_IN_THIRD_RZ_TO_CON3", 139);
            put("MOV_IN_THIRD_RZ_TO_CON4", 138);
            put("MOV_IN_THIRD_RZ_TO_CON1", 137);
            put("MOV_IN_FRIST_RZ_TO_CON3", 136);
            put("MOV_IN_FRIST_RZ_TO_CON4", 135);
            put("MOV_IN_FRIST_TZ_TO_CON4", 134);
            put("MOV_IN_FRIST_TZ_TO_CON3", 133);
            put("MOV_IN_FRIST_LOZ_TO_CON3", 132);
            put("MOV_IN_FRIST_LZ_TO_CON3", 131);
            put("MOV_IN_FRIST_RZ_TO_CON4", 129);
            put("MOV_IN_FRIST_RZ_TO_CON2", 128);
            put("MOV_IN_FRIST_RZ_TO_CON1", 127);
            put("MOV_IN_FRIST_LOZ_TO_CON3", 126);
            put("MOV_IN_FRIST_TZ_TO_CON4", 124);
            put("MOV_IN_FRIST_LOZ_TO_CON4", 123);
            put("MOV_IN_FRIST_LOZ_TO_CON2", 122);
            put("MOV_IN_FRIST_TZ_TO_CON2", 121);
            put("MOV_IN_FRIST_TZ_TO_CON1", 120);
            put("MOV_IN_FRIST_LOZ_TO_CON1", 119);
            put("MOV_IN_FRIST_LZ_TO_CON2", 118);
            put("MOV_IN_FRIST_LZ_TO_CON1", 117);
            put("MOV_IN_FRIST_LZ_TO_CON4", 116);
            put("MOV_IN_CH2_TO_THIRD_RZ", 109);
            put("MOV_IN_CH2_TO_THIRD_TZ", 108);
            put("MOV_IN_CH2_TO_THIRD_LOZ", 107);
            put("MOV_IN_CH2_TO_THIRD_LZ", 105);
            put("RESET_FRUIT", 73);
        }
    };
}