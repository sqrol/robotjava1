package frc.robot.Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.Encoder.IndexingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.StateMachine.StateMachine;
import frc.robot.StateMachine.States;

public class LogicMain {
    private static LogicCore logic;
    public static ArrayList<Integer> indexList = new ArrayList<>();
    public static void main(String[] args) {
        
        logic = new LogicCore();
        logic.logicInit();

        for (String elem : logic.arrayWithLogic) {
            System.out.println(elem);
            SmartDashboard.putString("elem", elem); 
            switch(elem) {
                case "MOV_IN_START_TO_CH1":
                    indexList.add(1);
                    break;
                case "MOV_IN_CH1_TO_THIRD_RZ":
                    indexList.add(2);
                    break;
                case "MOV_IN_THIRD_RZ_TO_CH1":
                    indexList.add(4);
                    break;
                case "MOV_IN_CH1_TO_CON2":
                    indexList.add(5);
                    break;
                case "MOV_IN_CON2_TO_CH1":
                    indexList.add(6);
                    break;
                case "MOVE_IN_CH1_TO_FINISH":
                    indexList.add(7);
                    break;
                case "MOV_IN_CH1_TO_THIRD_LOZ":
                    indexList.add(8);
                    break;
                case "MOV_IN_THIRD_LOZ_TO_CH2":
                    indexList.add(9);
                    break;
                case "MOV_IN_CH2_TO_CON4":
                    indexList.add(10);
                    break;
                case "MOV_IN_CON4_TO_CH2":
                    indexList.add(11);
                    break;
                case "MOVE_IN_CH2_TO_FINISH":
                    indexList.add(12);
                    break;
                case "MOV_IN_CH1_TO_THIRD_LZ":
                    indexList.add(13);
                    break;
                case "MOV_IN_START_TO_CH3":
                    indexList.add(24);
                    break;
                case "MOV_IN_CH3_TO_FRIST_LZ":
                    indexList.add(25);
                    break;
                case "MOV_IN_FRIST_LZ_TO_CH3":
                    indexList.add(26);
                    break;
                case "MOV_IN_CH3_TO_CON4":
                    indexList.add(27);
                    break;
                case "MOV_IN_CON4_TO_CH3":
                    indexList.add(28);
                    break;
                case "MOVE_IN_CH3_TO_FINISH":
                    indexList.add(29);
                    break;
                case "MOV_IN_CH3_TO_FRIST_LOZ":
                    indexList.add(30);
                    break;
                case "MOV_IN_CH3_TO_FRIST_RZ":
                    indexList.add(31);
                    break;
                case "MOV_IN_FRIST_RZ_TO_CH3":
                    indexList.add(32);
                    break;
                case "MOV_IN_CH3_TO_CON3":
                    indexList.add(33);
                    break;
                case "MOV_IN_CON3_TO_CH3":
                    indexList.add(34);
                    break;
                case "MOV_IN_CH3_TO_CON2":
                    indexList.add(35);
                    break;
                case "MOV_IN_CON2_TO_CH3":
                    indexList.add(36);
                    break;
                case "MOV_IN_FRIST_LOZ_TO_CH3":
                    indexList.add(37);
                    break;
                case "MOV_IN_CH3_TO_FRIST_UZ":
                    indexList.add(38);
                    break;
                case "MOV_IN_FRIST_UZ_TO_CH3":
                    indexList.add(39);
                    break;
                case "MOV_IN_CH3_TO_CON1":
                    indexList.add(40);
                    break;
                case "MOV_IN_CON1_TO_CH3":
                    indexList.add(41);
                    break;
                case "MOV_IN_CH2_TO_CON1":
                    indexList.add(42);
                    break;
                case "MOV_IN_CON1_TO_CH2":
                    indexList.add(43);
                    break;
            }
        }

    }
}