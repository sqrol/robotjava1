package frc.robot.StateMachine;

import frc.robot.StateMachine.Cases.*;

public class States {
    public static IState[][] mainStates = new IState[][] {
                                           // 0 
        {
            new StartPos(), 
            // new InitLogicase(),
            new Align("sonic", 85, 0, 0),
            new Transition(),
        },
        
            // MOV_IN_START_TO_CH1 // 1
        {
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_RZ // 2
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new Transition()
        },
            // GRAB_POS_17 // 3
        {
            
        }, 
            // MOV_IN_THIRD_RZ_TO_CH1 // 4
        {

            new DriveXAxis(-20, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),

            new Transition()   
        }, 
            // MOV_IN_CH1_TO_CON2 // 5
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 85, 0, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()  
        }, 
            // MOV_IN_CON2_TO_CH1 // 6
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 85, 0, 0),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOVE_IN_CH1_TO_FINISH // 7
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(165, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 80, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_LOZ // 8
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(75, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(23, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CH1 // 9
        {
            new DriveXAxis(-23, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(80, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0), 
            new Transition()
        },
            // MOV_IN_CH2_TO_CON4 // 10
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(200, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new Transition()  
        },
            // MOV_IN_CON4_TO_CH2 // 11
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(190, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition() 
        },
            // MOVE_IN_CH2_TO_FINISH // 12
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(105, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_LZ // 13
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(93, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(21, 0),
   
            new Transition()
        },
            // MOV_IN_CH2_TO_CON4 // 14
        {
            // ненужный не удалять
            new Transition()  
        },
            // MOV_IN_CON4_TO_CH2 // 15
        {
            // тоже ненужный. не удалять
            new Transition()  
        },
            // MOV_IN_THIRD_LZ_TO_CH2 // 16
        {
            new DriveXAxis(-21, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(98, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_START_TO_CH2 // 17
        {
            new DriveXAxis(0, 180),
            new DriveXAxis(125, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(30, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()   
        },
            // MOV_IN_CH2_TO_SECOND_LOZ // 18
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(70, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(11, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CH2 // 19
        {
            new DriveXAxis(-16, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(76, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_RZ // 20
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(40, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(11, 0),
            new Transition()
            
        },
            // MOV_IN_SECOND_RZ_TO_CH2 // 21
        {
            new DriveXAxis(-15, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(45, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_LZ // 22
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(86, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(11, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CH2 // 23
        {
            new DriveXAxis(-16, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(93, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_START_TO_CH3  // 24
        {
            new DriveXAxis(93, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_LZ // 25
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new DriveXAxis(33, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(43, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(13, 0),
            new Transition()
        },   
            // MOV_IN_FRIST_LZ_TO_CH3 // 26
        {
            new DriveXAxis(-13, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(49, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_CON4 // 27
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            // new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CON4_TO_CH3 // 28
        {
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOVE_IN_CH3_TO_FINISH // 29
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(38, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 25, 0, 0),
            new DriveXAxis(0, 180),
            new DriveXAxis(80, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(98, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_LOZ // 30
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 40, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(60, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(7, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_RZ // 31
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 40, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(84, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(11, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CH3 // 32
        {
            new DriveXAxis(-15, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(94, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_CON3 // 33
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(215, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CON3_TO_CH3 // 34
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 80, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(225, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()   
        },
            // MOV_IN_CH3_TO_CON2 // 35
        {
            new DriveXAxis(0, 90),
            new DriveXAxis(14, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CON2_TO_CH3 // 36
        {
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CH3 // 37
        {
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(60, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_UZ // 38
        {
            
            new Transition()
        },
            // MOV_IN_FRIST_UZ_TO_CH3 // 39
        {
            
            new Transition()
        },
            // MOV_IN_CH3_TO_CON1 // 40
        {   
            new DriveXAxis(0, 180),
            // new Align("sonic", 89, 0, 0),
            new DriveXAxis(77, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH3 // 41
        {
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON1 // 42
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(287, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH2 // 43
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 80, 0, 0),
            new DriveXAxis(190, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_UZL // 44
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new DriveXAxis(30, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(25, 0), 
            new DriveXAxis(0, -90),
            new DriveXAxis(28, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_UZR // 45
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 35, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(90, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(25, 0),
            new Transition()
        },
            // MOV_IN_FRIST_UZR_TO_CH3 // 46
        {
            new DriveXAxis(-30, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(94, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_UZL_TO_CH3 // 47
        {
            new DriveXAxis(-32, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(32, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_UZR // 48
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(33, 0),
            new Transition()
        },
            // MOV_IN_THIRD_UZR_TO_CH1 // 49
        {
            new DriveXAxis(-35, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_ON_CH1_TO_THIRD_UZL // 50
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(93, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(33, 0),
            new Transition()
        },
            // MOV_IN_THIRD_UZL_TO_CH1 // 51
        {
            new DriveXAxis(-33, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(98, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_CON1 // 52
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 72, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH1 // 53
        {
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_CON4 // 54
        {
            new DriveXAxis(0, -90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON4_TO_CH1 // 55
        {
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_CON3 // 56
        {
            new DriveXAxis(0, -90),
            new Align("sonic", 90, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(222, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CON3_TO_CH1 // 57
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(205, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_UZR // 58
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(40, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(25, 0),
            new Transition()
        },
            // MOV_IN_SECOND_UZR_TO_CH2 // 59
        {
            new DriveXAxis(-30, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(45, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_UZL // 60
        {   
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(87, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(25, 0),
            new Transition()
        },
            // MOV_IN_SECOND_UZL_TO_CH2 // 61
        {
            new DriveXAxis(-30, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(94, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_TZ // 62
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(67, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(10, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON3 // 63
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 55, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()

        },
            // MOV_IN_CON3_TO_CH2 // 64
        {   
            new DriveXAxis(0, 180),
            new DriveXAxis(105, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON2 // 65
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(210, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CON2_TO_CH2 // 66
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 90, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(210, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CH2 // 67
        {
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(-72, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_TZ // 68
        {   
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(65, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(7, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CH3 // 69
        {   
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(75, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_TZ // 70
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),  // можно сделать потом чтоб стенку держал
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(73, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new Transition()
        },
            // MOV_IN_THIRD_TZ_TO_CH1 // 71
        {
            new DriveXAxis(-25, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(78, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // GRAB_POS_0_0_LOWER // 72
        {
            new SetGrabPos("OPEN"),
            new SetLiftPosition(90),
            new SetGrabPos("BIG APPLE"),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FOR DROP"),
            new Transition(),
        },
            // RESET_FRUIT // 73
        {
            new SetGrabPos("OPEN"),
            new Transition()
        },
            // GRAB_POS_1_0_LOWER // 74
        {
            new SetGrabPos("OPEN"),
            new SetGlidePosition(13),
            new SetLiftPosition(90),
            new SetGrabPos("BIG APPLE"),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetGripRotatePos("FOR DROP"),
            new Transition()
        },
            // GRAB_POS_2_0_MIDDLE // 75
        {
            new SetGrabPos("OPEN"),
            new SetGripRotatePos("ANGLE"),
            new SetGlidePosition(12),
            new SetLiftPosition(100),
            
            new SetGrabPos("SMALL APPLE"),
            new DriveXAxis(-30, 0),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetGripRotatePos("FOR DROP"),
            new Transition()
        },
            // GRAB_POS_24 // 76
        {
            new SetGrabPos("OPEN"),
            new SetLiftPosition(100),
            new SetGrabPos("BIG APPLE"),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FOR DROP"),
            new Transition(),
        },
            // GRAB_POS_0_-45_LOWER // 77
        {   

        }
    };
}