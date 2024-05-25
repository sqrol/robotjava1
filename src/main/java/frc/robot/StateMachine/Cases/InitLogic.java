package frc.robot.StateMachine.Cases;

import java.util.ArrayList;

import frc.robot.RobotContainer;
import frc.robot.Logic.LogicCore;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class InitLogic implements IState {

    private LogicCore logic; 
    private Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        
        logic = RobotContainer.logic;
        logic.logicInit();
        
        ArrayList<Integer> indexList = Training.indexList;

        for (String elem : logic.arrayWithLogic) {
            System.out.println(elem);
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
                case "MOV_IN_THIRD_LZ_TO_CH2":
                    indexList.add(16);
                    break;
                case "MOV_IN_START_TO_CH2":
                    indexList.add(17);
                    break;
                case "MOV_IN_CH2_TO_SECOND_LOZ":
                    indexList.add(18);
                    break;
                case "MOV_IN_SECOND_LOZ_TO_CH2":
                    indexList.add(19);
                    break;
                case "MOV_IN_CH2_TO_SECOND_RZ": 
                    indexList.add(20);
                    break;
                case "MOV_IN_SECOND_RZ_TO_CH2":
                    indexList.add(21);
                    break;
                case "MOV_IN_CH2_TO_SECOND_LZ":
                    indexList.add(22);
                    break;
                case "MOV_IN_SECOND_LZ_TO_CH2":
                    indexList.add(23);
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
                case "MOV_IN_CH3_TO_FRIST_UZL":
                    indexList.add(44);
                    break;
                case "MOV_IN_CH3_TO_FRIST_UZR":
                    indexList.add(45);
                    break;
                case "MOV_IN_FRIST_UZL_TO_CH3":
                    indexList.add(47);
                    break;
                case "MOV_IN_CH1_TO_THIRD_UZR":
                    indexList.add(48);
                    break;
                case "MOV_IN_THIRD_UZR_TO_CH1":
                    indexList.add(49);
                    break;
                case "MOV_ON_CH1_TO_THIRD_UZL":
                    indexList.add(50);
                    break;
                case "MOV_IN_THIRD_UZL_TO_CH1":
                    indexList.add(51);
                    break;
                case "MOV_IN_CH1_TO_CON1":
                    indexList.add(52);
                    break;
                case "MOV_IN_CON1_TO_CH1":
                    indexList.add(53);
                    break;
                case "MOV_IN_CH1_TO_CON4":
                    indexList.add(54);
                    break;
                case "MOV_IN_CON4_TO_CH1":
                    indexList.add(55);
                    break;
                case "MOV_IN_CH1_TO_CON3":
                    indexList.add(56);
                    break;
                case "MOV_IN_CON3_TO_CH1":
                    indexList.add(57);
                    break;
                case "MOV_IN_CH2_TO_SECOND_UZR":
                    indexList.add(58);
                    break;
                case "MOV_IN_SECOND_UZR_TO_CH2":
                    indexList.add(59);
                    break;
                case "MOV_IN_CH2_TO_SECOND_UZL":
                    indexList.add(60);
                    break;
                case "MOV_IN_SECOND_UZL_TO_CH2":
                    indexList.add(61);
                    break;
                case "MOV_IN_CH2_TO_SECOND_TZ":
                    indexList.add(62);
                    break;
                case "MOV_IN_CH2_TO_CON3":
                    indexList.add(63);
                    break;
                case "MOV_IN_CON3_TO_CH2":
                    indexList.add(64);
                    break;
                case "MOV_IN_CH2_TO_CON2":
                    indexList.add(65);
                    break;
                case "MOV_IN_CON2_TO_CH2":
                    indexList.add(66);
                    break;
                case "MOV_IN_SECOND_TZ_TO_CH2":
                    indexList.add(67);
                    break;
                case "MOV_IN_CH3_TO_FRIST_TZ":
                    indexList.add(68);
                    break;
                case "MOV_IN_FRIST_TZ_TO_CH3":
                    indexList.add(69);
                    break;
                case "MOV_IN_CH1_TO_THIRD_TZ":
                    indexList.add(70);
                    break;
                case "MOV_IN_THIRD_TZ_TO_CH1":
                    indexList.add(71);
                    break;
                case "GRAB_POS_0_0_LOWER":
                    indexList.add(72);
                    break;
                case "RESET_FRUIT":
                    indexList.add(73);
                    break;
                case "GRAB_POS_1_0_LOWER":
                    indexList.add(74);
                    break;
                case "GRAB_POS_2_0_MIDDLE":
                    indexList.add(75);
                    break;
                case "GRAB_POS_24":
                    indexList.add(76);
                    break;
                case "GRAB_POS_0_-45_LOWER":
                    indexList.add(77);
                    break;
                case "MOV_IN_THIRD_LZ_TO_CH1":
                    indexList.add(78);
                    break;
                case "GRAB_POS_DOWN":
                    indexList.add(79);
                    break;
                case "B1":
                    indexList.add(80);
                    break;
                case "B2":
                    indexList.add(81);
                    break;
                case "GRAB_POS_MID":
                    indexList.add(82);
                    break;
                case "MOV_IN_CH3_TO_SECOND_LOZ":
                    indexList.add(83);
                    break;
                case "MOV_IN_CH3_TO_SECOND_TZ":
                    indexList.add(84);
                    break;
                case "MOV_IN_CH3_TO_SECOND_RZ":
                    indexList.add(85);
                    break;
                case "GRAB_POS_UP":
                    indexList.add(86);
                    break;
                case "GRAB_POS_1_-45_LOWER":
                    indexList.add(87);
                    break;
                case "GRAB_POS_2_-45_MIDDLE":
                    indexList.add(88);
                    break;
                case "GRAB_POS_0_20_LOWER":
                    indexList.add(89);
                    break;
                case "GRAB_POS_0_-45_LOWER_UZ":
                    indexList.add(90);
                    break;
                case "GRAB_POS_1_-45_LOWER_14":
                    indexList.add(91);
                    break;
                case "GRAB_POS_0_45_LOWER":
                    indexList.add(92);
                    break;
                case "GRAB_POS_1_45_LOWER":
                    indexList.add(93);
                    break;
                case "GRAB_POS_2_45_MIDDLE":
                    indexList.add(94);
                    break;
                case "MOV_IN_CH3_TO_SECOND_LZ":
                    indexList.add(95);
                    break;
                case "GRAB_POS_2_45_LOWER":
                    indexList.add(96);
                    break;
                case "MOV_IN_CH3_TO_SECOND_UZL":
                    indexList.add(97);
                    break;
                case "MOV_IN_CH3_TO_SECOND_UZR":
                    indexList.add(98);
                    break;
                case "MOV_IN_CH3_TO_THIRD_LZ":
                    indexList.add(99);
                    break;
                case "MOV_IN_CH3_TO_THIRD_LOZ":
                    indexList.add(100);
                    break;
                case "MOV_IN_CH3_TO_THIRD_TZ":
                    indexList.add(101);
                    break;
                case "MOV_IN_CH3_TO_THIRD_RZ":
                    indexList.add(102);
                    break;
                case "MOV_IN_CH3_TO_THIRD_UZL":
                    indexList.add(103);
                    break;
                case "MOV_IN_CH3_TO_THIRD_UZR":
                    indexList.add(104);
                    break;
                case "MOV_IN_CH2_TO_THIRD_LZ":
                    indexList.add(105);
                    break;
                case "MOV_IN_CH2_TO_THIRD_UZL":
                    indexList.add(106);
                    break;
                case "MOV_IN_CH2_TO_THIRD_LOZ":
                    indexList.add(107);
                    break;
                case "MOV_IN_CH2_TO_THIRD_TZ":
                    indexList.add(108);
                    break;
                case "MOV_IN_CH2_TO_THIRD_RZ":
                    indexList.add(109);
                    break;
                case "MOV_IN_CH2_TO_THIRD_UZR":
                    indexList.add(110);
                    break;
                case "UNDER_TREE_15":
                    indexList.add(111);
                    break;
                case "UNDER_TREE_8":
                    indexList.add(112);
                    break; 
                case "GRAB_POS_0_-45_LOWER_3":
                    indexList.add(113);
                    break;
                case "MOV_IN_CH1_TO_THIRD_UZL":
                    indexList.add(114);
                    break;
                case "END":
                    indexList.add(115);
                    break;
            }
        }
        return true;
    }
    
}
