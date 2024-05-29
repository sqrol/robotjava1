package frc.robot.StateMachine;

import frc.robot.StateMachine.Cases.*;

public class States {
    public static IState[][] mainStates = new IState[][] {
                                           // 0 
        {
            new StartPos(), 
            
            new RotateAligToFruit(true),
            new GlideMovToFruit(),
            new SetLiftPosition(98),
            new SetGrabPos("SMALL APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetRotatePos(1),

            new RotateAligToFruit(true),
            new GlideMovToFruit(),
            new SetLiftPosition(98),
            new SetGrabPos("SMALL APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetRotatePos(1),

            new RotateAligToFruit(true),
            new GlideMovToFruit(),
            new SetLiftPosition(98),
            new SetGrabPos("SMALL APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetRotatePos(1),
 
            new End(),
            
            // new testForNewCam(),
            // для маленького яблока new SetLiftPosition(98),
            // для большого яблока new SetLiftPosition(85),
            // с нижней ветки для маленького яблока new SetLiftPosition(41)
            // с нижней ветки для большого яблока new SetLiftPosition(30)
            // со средней ветки для маленького яблока new SetLiftPosition(65)
            // со средней ветки для большого яблока new SetLiftPosition(60)
            // с верхней ветки для маленького яблока new SetLiftPosition(5)
            // с верхней ветки для большого яблока new SetLiftPosition(2)
            // new StartPos(),
            // new DriveXAxis(104, 0), // метр вперед
            // new StartPos(),
            // new SetRotatePos(-48),
            // new SetGrabPos("OPEN", false),
            // new DriveXAxis(-4, 0),
            // new SetGlidePosition(11),
            // new SetLiftPosition(85),
            // new SetGrabPos("BIG APPLE", false),
            // new SetLiftPosition(-1),
            // new SetGlidePosition(1),
            // new SetRotatePos(0),
            // new DriveXAxis(4, 0),
            
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(-35, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),

            new Align("sonic", 45, 0, 0),
            // new End(),
            // new SetRotatePos(-61),
            // new SetGrabPos("OPEN", false),
            // new DriveXAxis(-4, 0),
            // new SetGlidePosition(11),
            // new SetLiftPosition(85),
            // new SetGrabPos("BIG APPLE", false),
            // new SetLiftPosition(-1),
            // new SetGlidePosition(1),
            // new SetRotatePos(0),
            // new DriveXAxis(4, 0),
            
            // new SetRotatePos(-90),
            // new StartPos(),
            // new End(),
            // new ObjectFinder(),
            // new RottenDetection(),
            // new End(),
            // new StartPos(),
            // new SonicCheck(),

            new End(),
            // // new StartPos(),
            
            // // new SharpCheck(),
            // // new StartPos(),
            // // new ObjectFinder(),
            // new InitLogic(),
            
            new Transition(),
        },
        
            // MOV_IN_START_TO_CH1 // 1
        {
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-140, 0),
            new Align("sonic", 70, 0, 0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_RZ // 2
        {
            new DriveXAxis(141, 0),
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
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 85, 0, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()  
        }, 
            // MOV_IN_CON2_TO_CH1 // 6
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 7, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(0, -90),
            new DriveXAxis(-140, 0),
            new DriveXAxis(0, 180),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new Transition()
        },
            // MOVE_IN_CH1_TO_FINISH // 7
        {
            new DriveXAxis(107, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(73, 0),
            new End(),
        },
            // MOV_IN_CH1_TO_THIRD_LOZ // 8
        {
            new DriveXAxis(120, 90),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(62, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(8, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CH1 // 9
        {
            new DriveXAxis(-24, 0),
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
            new SetGripRotatePos("FOR DROP"),
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
            new DriveXAxis(-70, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(150, 0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(0, 90),
            new DriveXAxis(20, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            
            new Transition() 
        },
            // MOVE_IN_CH2_TO_FINISH // 12
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 55, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(175, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(25, 0),
            new End(),
        },
            // MOV_IN_CH1_TO_THIRD_LZ // 13
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0), 
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(96, 0),
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
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(-35, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Transition()   
        },
            // MOV_IN_CH2_TO_SECOND_LOZ // 18
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(76, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(12, 0),
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
            new Align("sonic", 6, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(13, 0),
            new Transition()
            
        },
            // MOV_IN_SECOND_RZ_TO_CH2 // 21
        {
            new DriveXAxis(-21, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(48, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_LZ // 22
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(96, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(18, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CH2 // 23
        {
            new DriveXAxis(-21, 0),
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
            new DriveXAxis(92, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_LZ // 25
        {
            new DriveXAxis(32, 0),
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
            new Align("sonic", 80, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            // new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, 180),
            new SetGripRotatePos("FOR DROP"),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CON4_TO_CH3 // 28
        {
            new DriveXAxis(-80, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(40, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOVE_IN_CH3_TO_FINISH // 29
        {
            new DriveXAxis(15, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(80, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(87, 0),
            new End()
        },
            // MOV_IN_CH3_TO_FRIST_LOZ // 30
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 44, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(75, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(9, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_RZ // 31
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 44, 0, 0),
            new DriveXAxis(0, -90), 
            new DriveXAxis(92, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(10, 0),
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
            new DriveXAxis(-60, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(229, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()   
        },
            // MOV_IN_CH3_TO_CON2 // 35
        {
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(14, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CON2_TO_CH3 // 36
        {
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CH3 // 37
        {
            new DriveXAxis(-12, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(64, 0),
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
            new DriveXAxis(0, -90),
            new DriveXAxis(-70, 0),
            new DriveXAxis(0, 180),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON1 // 42
        {
            new DriveXAxis(0, 180),
            new SetGripRotatePos("FOR DROP"),
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
            new DriveXAxis(-280, 0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(0, -90),
            new DriveXAxis(27, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            
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
            new DriveXAxis(30, 0), 
            new DriveXAxis(0, -90),
            new DriveXAxis(27, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_UZR // 45
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(-33, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(93, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(41, 0),
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
            new DriveXAxis(-35, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(49, 0), 
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
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
            new DriveXAxis(42, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(43, 0),
            new Transition()
        },
            // MOV_IN_THIRD_UZR_TO_CH1 // 49
        {
            new DriveXAxis(-55, 0),
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
            new DriveXAxis(-60, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(110, 0),
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
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 72, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH1 // 53
        {
            new DriveXAxis(0, 90),
            new DriveXAxis(-70, 0),
            new DriveXAxis(0, 180),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_CH1_TO_CON4 // 54
        {
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON4_TO_CH1 // 55
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_CH1_TO_CON3 // 56
        {
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
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
            new DriveXAxis(-60, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(210, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_UZR // 58
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(38, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(42, 0),
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
            new DriveXAxis(91, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(35, 0),
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
            new DriveXAxis(0, 90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(76, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(12, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON3 // 63
        {
            new DriveXAxis(0, 180),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 55, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()

        },
            // MOV_IN_CON3_TO_CH2 // 64
        {   
            new DriveXAxis(-86, 0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
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
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 17, 0, 0),
            new Transition()
        },
            // MOV_IN_CON2_TO_CH2 // 66
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(15, 0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(60 , 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CH2 // 67
        {
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(72, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_FRIST_TZ // 68
        {   
            new DriveXAxis(0, 90),
            new Align("sonic", 44, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(75, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(9, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CH3 // 69
        {   
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
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
            new DriveXAxis(120, 90),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(62, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(8, 0),
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
            new End(),
            new Transition(),
        },
            // RESET_FRUIT // 73
        {
            new SetGrabPos("OPEN", false),
            new Transition()
        },
            // GRAB_POS_1_0_LOWER // 74
        {
            new SetGlidePosition(11),
            new SetGrabPos("OPEN", true),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // GRAB_POS_2_0_MIDDLE // 75
        {
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("ANGLE"),
            new SetLiftPosition(50),
            new SetGlidePosition(14),
            new SetLiftPosition(95),
            new SetGrabPos("PEAR", false),
            new SetLiftPosition(50),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // GRAB_POS_24 // 76
        {
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(100),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FOR DROP"),
            new Transition(),
        },
            // GRAB_POS_0_-45_LOWER // 77
        {   
            new SetRotatePos(-45),
            new DriveXAxis(-4, 0),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(9),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // MOV_IN_THIRD_LZ_TO_CH1 // 78
        {
            new DriveXAxis(-20, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(103, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // GRAB_POS_DOWN // 79
        {   
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(13),
            new SetGripRotatePos("FLOOR"),
            new SetLiftPosition(30),            // для большого яблока 30
            new SetGrabPos("BIG APPLE", false), 
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // B1 // 80
        {
            new StartPos(),
            new DriveXAxis(102, 0), // метр вперед
            
            // змейка
            new StartPos(),
            new DriveXAxis(62, 0),  // прямо со старта
            new DriveXAxis(0, -90),

            new DriveXAxis(75, 0), // между зеленой и красной
            new DriveXAxis(0, 90),
            
            new DriveXAxis(54, 0), // от красной к зеленой
            new DriveXAxis(0, 90),

            new DriveXAxis(54, 0), // между красной и зеленой
            new DriveXAxis(0, -90),

            new DriveXAxis(53, 0), // от предпоследней к последней
            new DriveXAxis(0, -90),
            
            new DriveXAxis(71, 0), 
            new DriveXAxis(0, 90), // между предпоследней и последней
            new DriveXAxis(50, 0),
            
            new StartPos(),
            new SonicCheck(),
            new StartPos(),
            new SharpCheck(),
            new StartPos(),
            new ObjectFinder()
        },
            // B2 // 81
        {
            new StartPos(),
            new ObjectFinder(),
            new StartPos(),
            new RottenDetection(),

            // Управление элементом
            new StartPos(),
            new SetGripRotatePos("FLOOR"),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new End()
        },
            // GRAB_POS_MID // 82
        {
            new SetGrabPos("OPEN", false),
            new SetRotatePos(-35),
            new SetGripRotatePos("BRANCH"),
            new SetLiftPosition(68), // и большое и маленькое и грушу возьмет на 68
            new SetGlidePosition(4),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // MOV_IN_CH3_TO_SECOND_LOZ // 83
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(241, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_SECOND_TZ // 84
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(241, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_SECOND_RZ // 85
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(105, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(12, 0),
            new Transition()
        },
            // GRAB_POS_UP // 86
        {
            new SetRotatePos(34),
            new SetGripRotatePos("BRANCH"),
            new SetLiftPosition(2), // 5 для большого яблока
            new SetGlidePosition(4),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // GRAB_POS_1_-45_LOWER // 87
        {
            new SetRotatePos(-37),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(14),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(98),
            new SetGrabPos("SMALL APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetRotatePos(1),
            new Transition()
        },
            // GRAB_POS_2_-45_MIDDLE // 88
        {
            new SetRotatePos(-35),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(18),
            new SetLiftPosition(90),
            new SetGrabPos("PEAR", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // GRAB_POS_0_20_LOWER // 89
        {
            new SetRotatePos(4),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(16),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // GRAB_POS_0_-45_LOWER_UZ // 90
        {
            new End(),
            new Transition()
        },
            // GRAB_POS_1_-45_LOWER_14 // 91
        {
            new SetRotatePos(-37),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(13),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(0),
            new SetRotatePos(0),
            new Transition()
        },
            // GRAB_POS_0_45_LOWER // 92
        {
            new SetRotatePos(51),
            new DriveXAxis(-4, 0),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(7),
            new SetGrabPos("OPEN", false), 
            new SetLiftPosition(85),
            new SetGrabPos("PEAR", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // GRAB_POS_1_45_LOWER // 93
        {
            new End(),
            new Transition()
        },
            // GRAB_POS_2_45_MIDDLE // 94
        {
            new SetRotatePos(30),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("SMALL ANGLE"),
            new SetGlidePosition(12),
            new SetLiftPosition(95),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // MOV_IN_CH3_TO_SECOND_LZ // 95
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(230, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 29, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(10, 0),
            new Transition()
        },
            // GRAB_POS_2_45_LOWER // 96
        {

        },
            // MOV_IN_CH3_TO_SECOND_UZL // 97
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(220, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(54, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(22, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_SECOND_UZR // 98
        {   
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new DriveXAxis(47, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(256, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_LZ // 99
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(120, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(150, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 42, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_LOZ // 100
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(120, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(67, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(9, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_TZ // 101
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(120, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(67, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(9, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_RZ // 102
        {
            new DriveXAxis(10, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(-50, 0),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(138, 90),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_UZL // 103
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(190, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(53, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_THIRD_UZR // 104
        {
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(149, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(39, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_LZ // 105
        {
            new Align("sonic", 37, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_UZL // 106
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 76, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(27, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_LOZ // 107 
        {
            new Align("sonic", 25, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(78, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(12, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_TZ // 108 
        {
            new Align("sonic", 25, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(78, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(12, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_RZ // 109 
        {
            new Align("sonic", 25, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sonic", 45, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(140, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 15, 0, 0),
            new DriveXAxis(0, -90),
            new Transition()
        },
            // MOV_IN_CH2_TO_THIRD_UZR // 110 
        {
            new DriveXAxis(0, 180),
            new Align("sonic", 50, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(108, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(27, 0),
            new Transition()
        },
            // UNDER_TREE_15 // 111
        {
            new DriveXAxis(-15, 0),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(8),
            new SetGripRotatePos("SMALL ANGLE"),
            new SetLiftPosition(100),
            new SetGlidePosition(16),
            new SetGrabPos("SMALL APPLE", false),
            new SetLiftPosition(80),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetLiftPosition(-1),
            new Transition()
        },
            // UNDER_TREE_8 // 112
        {
            new DriveXAxis(-7, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(60, 0),
            new SetLiftPosition(40),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(12, 0),
            new SetGrabPos("OPEN", true),
            new SetGlidePosition(8),
            new SetGripRotatePos("SMALL ANGLE"),
            new SetLiftPosition(100),
            new SetRotatePos(26),
            new SetGlidePosition(18),
            new SetGrabPos("SMALL APPLE", false),
            new SetGlidePosition(8),
            new SetLiftPosition(40),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new SetGripRotatePos("FLOOR"),
            new DriveXAxis(-12, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(-33, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(60, 0),
            new DriveXAxis(0, 90),
            new Transition()
        },
            // GRAB_POS_0_-45_LOWER_3 // 113
        {
            new DriveXAxis(-3, 0),
            new SetRotatePos(-45),
            new SetGlidePosition(11),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("FLOOR"),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_UZL // 114
        {
            new DriveXAxis(0, -90),
            new DriveXAxis(170, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 20, 0, 0),
            new DriveXAxis(0, 180),
            new Align("sonic", 57, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(103, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new Transition()
        },
            // END // 115
        {
            new End()
        },
            // MOV_IN_FRIST_LZ_TO_CON4 // 116
        {
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(100, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(25, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LZ_TO_CON1 // 117
        {
            new DriveXAxis(-30, 0),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 7, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(80, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LZ_TO_CON2 // 118
        {       
            new DriveXAxis(-30, 0),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 25, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CON1 // 119
        {
            new DriveXAxis(-42, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(150, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CON1 // 120
        {
            new DriveXAxis(-42, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(150, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CON2 // 121
        {
            new DriveXAxis(-11, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(70, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(52, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CON2 // 122
        {
            new DriveXAxis(-11, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(70, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(52, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CON4 // 123
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },  
            // MOV_IN_FRIST_TZ_TO_CON4 // 124
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CON3 // 125
        {
            
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CON3 // 126
        {
            new DriveXAxis(-20, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(150, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON1 // 127
        {
            new DriveXAxis(-42, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(177, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON2 // 128
        {
            new DriveXAxis(-20, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(95, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(57, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON4 // 129
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(52, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON3 // 130
        {
            
            new Transition()
        },
            // MOV_IN_FRIST_LZ_TO_CON3 // 131
        {
            new DriveXAxis(-10, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(208, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_LOZ_TO_CON3 // 132
        {
            new DriveXAxis(-30, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(156, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CON3 // 133
        {
            new DriveXAxis(-30, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(156, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_TZ_TO_CON4 // 134
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON4 // 135
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CON3 // 136
        {
            new DriveXAxis(-15, 0),
            new DriveXAxis(0, 90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(137, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_RZ_TO_CON1 // 137
        {
            new DriveXAxis(-150, 0),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 7, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(70, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_RZ_TO_CON4 // 138
        {
            new DriveXAxis(-94, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_RZ_TO_CON3 // 139
        {
            new DriveXAxis(-30, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(132, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_RZ_TO_CON2 // 140
        {
            new DriveXAxis(-150, 0),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(35, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CON3 // 141
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new DriveXAxis(45, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_TZ_TO_CON3 // 142
        {
            new DriveXAxis(-100, 0),
            new Align("sonic", 20, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new DriveXAxis(45, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_TZ_TO_CON4 // 143
        {
            new DriveXAxis(-15, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(150, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CON4 // 144
        {
            new DriveXAxis(-15, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(150, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CON2 // 145
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(190, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(53, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_TZ_TO_CON2 // 146
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(190, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(53, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_TZ_TO_CON1 // 147
        {
            new DriveXAxis(-45, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(260, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CON1 // 148
        {
            new DriveXAxis(-45, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(260, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LZ_TO_CON3 // 149
        {
            new Align("sonic", 14, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LZ_TO_CON4 // 150
        {
            new Align("sonic", 14, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(238, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LZ_TO_CON2 // 151
        {   
            new Align("sonic", 14, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(282, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_THIRD_LZ_TO_CON1 // 152
        {
            new Align("sonic", 14, 0, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(100, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(31, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(250, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_RZ_TO_CON3 // 153
        {
            new DriveXAxis(-8, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_RZ_TO_CON2 // 154
        {
            new DriveXAxis(-8, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(281, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_RZ_TO_CON1 // 155
        {
            new DriveXAxis(-8, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(100, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(25, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(250, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_RZ_TO_CON4 // 156
        {
            new DriveXAxis(-8, 0),
            new DriveXAxis(0, -90),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(240, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_RZ_TO_CON3 // 157
        {
            new DriveXAxis(-12, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CON3 // 158
        {
            new DriveXAxis(-12, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CON3 // 159
        {
            new DriveXAxis(-12, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CON4 // 160
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(180, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CON4 // 161
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(180, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CON1 // 162
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(287, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CON1 // 163
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(287, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_TZ_TO_CON2 // 164
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(220, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CON2 // 165
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(36, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(220, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CON3 // 166
        {
            new DriveXAxis(-14, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CON1 // 167
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(290, 0),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CON2 // 168
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(219, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(55, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CON4 // 169
        {
            new DriveXAxis(-40, 0),
            new SetGripRotatePos("FOR DROP"),
            new DriveXAxis(0, 90),
            new DriveXAxis(50, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(180, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // -45_1ST_SIDE_LINE (граб пос 1 и еще какой-то должен быть) // 170
        {
            new SetRotatePos(-61),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // -25_1ST_SIDE_LINE (граб пос 5 и еще наверное чото подойдет) // 171
        {
            new SetRotatePos(-35),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new Transition()
        },
            // 25_1ST_SIDE_LINE (граб пос 12 и еще наверное чото подойдет) // 172
        {
            new SetRotatePos(25),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_1ST_SIDE_LINE (граб пос 19 и 25 и 21 для второго дерева LZ) // 173
        {
            new SetRotatePos(50),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(9),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // -45_2NS_SIDE_LINE (граб пос 2 и чото еще потом дополню) // 174
        {
            new SetRotatePos(-52),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(16),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // -25_2ND_SIDE_LINE (граб пос 6 и потмо еще чото) // 175
        {
            new SetRotatePos(-25),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // 25_2ND_SIDE_LINE (граб пос 13 и    еще чото) // 176
        {
            new SetRotatePos(10),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_2ND_SIDE_LINE (граб пос 20 ) // 177
        {
            new SetRotatePos(47),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(15),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // -45_3RD_SIDE_LINE (граб пос 7) // 178
        {
            new SetRotatePos(-30),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("SMALL ANGLE"),
            new SetLiftPosition(95),
            new SetGlidePosition(11),
            new SetGrabPos("BIG APPLE", false),
            new SetGlidePosition(1),
            new SetLiftPosition(60),
            new SetRotatePos(0),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // GRAB_POS_14 // 179
        {
            new SetRotatePos(10),
            new SetGrabPos("OPEN", false),
            new SetGripRotatePos("ANGLE"),
            new SetLiftPosition(90),
            new SetGlidePosition(11),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(60),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // 45_3RD_SIDE_LINE (граб пос 21) // 180
        {
            new SetRotatePos(24),
            new SetGrabPos("OPEN", true),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // 0_1ST_MAIN_LINE (граб пос 24, 22 и 20 для второго дерева LZ) // 181
        {
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new Transition()
        },
            // -45_1ST_MAIN_LINE (граб пос 23 и 19 для второго дерева LZ) // 182
        {
            new SetRotatePos(-45),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(6),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // 0_2ND_MAIN_LINE (граб пос 17 и 13 для второго дерева LZ) // 183
        {
            new SetLiftPosition(50),
            new SetGripRotatePos("FLOOR"),
            new SetGlidePosition(10),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(50),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // -45_2ND_MAIN_LINE (граб пос 16 и 12 для второго дерева LZ) // 184
        {
            new SetRotatePos(-30),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(60),
            new SetGlidePosition(12),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(60),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_2ND_MAIN_LINE (граб пос 18) // 185
        {
            new SetRotatePos(40),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(13),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // 0_3RD_MAIN_LINE (граб пос 10) // 186
        {
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(60),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(60),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_3RD_MAIN_LINE (граб пос 11) // 187
        {
            new DriveXAxis(5, 0),
            new SetRotatePos(33),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(60),
            new SetGlidePosition(19),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(60),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new DriveXAxis(-5, 0),
            new Transition()
        },
            // -45_3RD_MAIN_LINE (граб пос 10) // 188
        {
            new DriveXAxis(5, 0),
            new SetRotatePos(-30),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(70),
            new SetGlidePosition(18),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new DriveXAxis(-5, 0),
            new Transition()
        },
            // 0_4TH_MAIN_LINE (граб пос 3) // 189
        {
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(70),
            new DriveXAxis(12, 0),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new DriveXAxis(-12, 0),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // 45_4TH_MAIN_LINE (граб пос 4) // 190
        {
            new SetRotatePos(30),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(12, 0),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(60),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(-12, 0),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // 45_2ND_MAIN_LINE_SECOND_LZ (граб пос 14 для второго дерева LZ) // 191
        {
            new SetRotatePos(40),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(9),
            new SetLiftPosition(79),
            new SetGlidePosition(12),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // 0_3RD_MAIN_LINE_SECOND_LZ (граб пос 6 для второго дерева LZ) // 192
        {
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(79),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(79),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // -45_3RD_MAIN_SECOND_LZ (граб пос 5 для второго дерева LZ) // 193
        {
            new DriveXAxis(5, 0),
            new SetRotatePos(-30),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(60),
            new SetGlidePosition(17),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new DriveXAxis(-5, 0),
            new Transition()
        },
            // 0_4TH_MAIN_LINE_SECOND_LZ (граб пос 2 для второго дерева LZ) // 194
        {
            new SetGrabPos("OPEN", false),
            new DriveXAxis(9, 0),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new DriveXAxis(-9, 0),
            new SetGripRotatePos("FLOOR"),
            new Transition()
        },
            // -45_4TH_MAIN_LINE_SECOND_LZ (граб пос 1 для второго дерева LZ) // 195
        {
            new SetRotatePos(-30),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(10, 0),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new DriveXAxis(-10, 0),
            new SetGripRotatePos("FLOOR"),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_3RD_MAIN_LINE_SECOND_LZ (граб пос 7 для второго дерева LZ) // 196
        {
            new DriveXAxis(-5, 0),
            new SetGrabPos("OPEN", false),
            new SetRotatePos(22),
            new SetGripRotatePos("SMALL ANGLE"),
            new SetLiftPosition(90),
            new SetGlidePosition(16),
            new SetGrabPos("BIG APPLE", false),
            new SetGlidePosition(6),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetGripRotatePos("FLOOR"),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new DriveXAxis(5, 0),
            new Transition()
        },
            // -45_1ST_SIDE_LINE_RZ (граб пос 25) // 197
        {
            new SetRotatePos(-48),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // -20_1ST_SIDE_LINE_RZ (граб пос 18) // 198
        {
            new SetRotatePos(-20),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(6),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // 20_1ST_SIDE_LINE_RZ (граб пос 11) // 199
        {
            new SetRotatePos(26),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(6),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // 45_1ST_SIDE_LINE_RZ (граб пос 4) // 200
        {
            new SetRotatePos(57),
            new SetGrabPos("OPEN", false),
            new DriveXAxis(-4, 0),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new DriveXAxis(4, 0),
            new Transition()
        },
            // -45_2ND_SIDE_LINE_RZ (граб пос 24) // 201
        {
            new SetRotatePos(-45),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(15),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // -20_2ND_SIDE_LINE_RZ (граб пос 17) // 202
        {   
            new SetRotatePos(-17),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(50),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // 20_2ND_SIDE_LINE_RZ (граб пос 10) // 203
        {
            new SetRotatePos(19),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(50),
            new SetGlidePosition(11),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // 45_2ND_SIDE_LINE_RZ (граб пос 3) // 204
        {
            new SetRotatePos(48),
            new SetGrabPos("OPEN", false),
            new SetGlidePosition(15),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(-1),
            new SetGlidePosition(1),
            new SetRotatePos(0),
            new Transition()
        },
            // -45_3RD_SIDE_LINE_RZ (граб пос 23) // 205
        {   
            new DriveXAxis(4, 0),
            new SetRotatePos(-35),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(70),
            new SetGlidePosition(21),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new DriveXAxis(-4, 0),
            new Transition()
        },
            // -20_3RD_SIDE_LINE_RZ (граб пос 16) // 206
        {
            new DriveXAxis(4, 0),
            new SetRotatePos(-17),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(70),
            new SetGlidePosition(18),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new DriveXAxis(-4, 0),
            new Transition()
        },
            // 20_3RD_SIDE_LINE_RZ (граб пос 9) // 207
        {
            new DriveXAxis(4, 0),
            new SetRotatePos(17),
            new SetGrabPos("OPEN", false),
            new SetLiftPosition(73),
            new SetGlidePosition(18),
            new SetLiftPosition(85),
            new SetGrabPos("BIG APPLE", false),
            new SetLiftPosition(70),
            new SetGlidePosition(1),
            new SetLiftPosition(-1),
            new SetRotatePos(0),
            new DriveXAxis(-4, 0),
            new Transition()
        }
    };
}