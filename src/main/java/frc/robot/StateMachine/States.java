package frc.robot.StateMachine;

import java.util.Set;

import frc.robot.StateMachine.Cases.Align;
import frc.robot.StateMachine.Cases.DriveXAxis;
import frc.robot.StateMachine.Cases.Drivefor2Motors;
import frc.robot.StateMachine.Cases.End;
import frc.robot.StateMachine.Cases.InitLogicase;
import frc.robot.StateMachine.Cases.OMS;
import frc.robot.StateMachine.Cases.Reset;
import frc.robot.StateMachine.Cases.Sensors;
import frc.robot.StateMachine.Cases.SetGlidePosition;
import frc.robot.StateMachine.Cases.SetGrabPos;
import frc.robot.StateMachine.Cases.SetGripRotatePos;
import frc.robot.StateMachine.Cases.SetLiftPosition;
import frc.robot.StateMachine.Cases.SetMainRotatePos;
import frc.robot.StateMachine.Cases.Align;
import frc.robot.StateMachine.Cases.StartPos;
import frc.robot.StateMachine.Cases.Transition;

public class States {
    public static IState[][] mainStates = new IState[][] {
                                           // 0 
        {
            new StartPos(), 
            
           
            new Align("sonic", 89, 0, 0),
            // new SetGlidePosition(21),
            // new SetGlidePosition(10),
            // new SetGlidePosition(15),
            // new SetGlidePosition(0),
            // new SetGlidePosition(15),
            // new SetGlidePosition(0),
            new End(),
            // new InitLogicase(),
            new Transition(),
        },
        
            // MOV_IN_START_TO_CH1 // 1
        {
            new Drivefor2Motors(0, -90),
            new Align("sharp", 15, 0, 0),
            new Transition()
            
        },
            // MOV_IN_CH1_TO_THIRD_RZ // 2
        {
            new Drivefor2Motors(0, -91),   
            new Drivefor2Motors(260, 0),
            new Transition()
        },
            // GRAB_POS_17 // 3
        {
            
        }, 
            // MOV_IN_THIRD_RZ_TO_CH1 // 4
        {
            new Drivefor2Motors(-1300, 0),
            new Align("sonic", 20, 0, 0),    
            new Drivefor2Motors(0, 93),   
            new Align("sharp", 8, 0, 0),  
            new Transition()   
        }, 
            // MOV_IN_CH1_TO_CON2 // 5
        {
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(620, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0), 
            new Transition()  
        }, 
            // MOV_IN_CON2_TO_CH1 // 6
        {
            new Drivefor2Motors(-1000, 0),
            new Align("sonic", 30, 0, 0),    
            new Drivefor2Motors(0, -90),  
            new Align("sharp", 8, 0, 0),     
            new Drivefor2Motors(0, 180),  
            new Transition()
        },
            // MOVE_IN_CH1_TO_FINISH // 7
        {
            new Drivefor2Motors(1700, 0),
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(600, 0), 
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_LOZ // 8
        {
            new Drivefor2Motors(0, 180),
            new Drivefor2Motors(400, 0),
            new Drivefor2Motors(0, 90), 
            new Drivefor2Motors(740, 0),
            new Drivefor2Motors(0, 90), 
            new Drivefor2Motors(80, 0), 
            new Transition()
        },
            // MOV_IN_THIRD_LOZ_TO_CH2 // 9
        {
            new Drivefor2Motors(-100, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),    
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0), 
            new Transition()
        },
            // MOV_IN_CH2_TO_CON4 // 10
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(2400, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 10, 0, 0), 
            new Transition()  
        },
            // MOV_IN_CON4_TO_CH2 // 11
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),    
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0), 
            new Transition() 
        },
            // MOVE_IN_CH2_TO_FINISH // 12
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1700, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(180, 0), 
            new Transition()
        },
            // MOV_IN_CH1_TO_THIRD_LZ // 13
        {
            new Drivefor2Motors(0, 180), 
            new Drivefor2Motors(400, 0), 
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1320, 0),
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0),    
            new Transition()
        },
            // MOV_IN_CH2_TO_CON4 // 14
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1990, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),  
            new Transition()  
        },
            // MOV_IN_CON4_TO_CH2 // 15
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(2090, 0),
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0),  
            new Transition()  
        },
            // MOV_IN_THIRD_LZ_TO_CH2 // 16
        {
            new Align("sharp", 8, 0, 0), 
            new Transition()
        },
            // MOV_IN_START_TO_CH2 // 17
        {
            new Drivefor2Motors(0, -180), 
            new Drivefor2Motors(1080, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(300, 0), 
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),
            new Transition()   
        },
            // MOV_IN_CH2_TO_SECOND_LOZ // 18
        {
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(-800, 0),
            new Transition()
        },
            // MOV_IN_SECOND_LOZ_TO_CH2 // 19
        {
            new Drivefor2Motors(800, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_RZ // 20
        {
            new Drivefor2Motors(0, -90),
            new Transition()
            
        },
            // MOV_IN_SECOND_RZ_TO_CH2 // 21
        {
            new Drivefor2Motors(0, -180),
            new Align("sharp", 8, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_SECOND_LZ // 22
        {
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(-940, 0), // поменять на соник
            new Transition()
        },
            // MOV_IN_SECOND_LZ_TO_CH2 // 23
        {
            new Drivefor2Motors(670, 0), // поменять на соник
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
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
            new DriveXAxis(27, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(32, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(5, 0),
            new Transition()
        },   
            // MOV_IN_FRIST_LZ_TO_CH3 // 26
        {
            new DriveXAxis(-5, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(35, 0),
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
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 25, 0, 0),
            new Align("sonic", 70, 0, 0),
            new DriveXAxis(0, 90),
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
            new Align("sonic", 35, 0, 0),
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
            new Align("sonic", 35, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(90, 0),
            new DriveXAxis(0, -90),
            new Transition()
        },
            // MOV_IN_FRIST_RZ_TO_CH3 // 32
        {
            new DriveXAxis(-5, 0),
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
            new Align("sharp", 14, 0, 0),
            new Transition()
        },
            // MOV_IN_CON3_TO_CH3 // 34
        {
            new Align("sonic", 60, 0, 0),
            new DriveXAxis(0, -90),
            new DriveXAxis(215, 0),
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
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, 90),
            new Drivefor2Motors(500, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(20, 0),
            new Transition()
        },
            // MOV_IN_FRIST_UZ_TO_CH3 // 39
        {
            new Drivefor2Motors(-60, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
            new Transition()
        },
            // MOV_IN_CH3_TO_CON1 // 40
        {   
            new DriveXAxis(0, 90),
            new Align("sonic", 85, 0, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(72, 0),
            new DriveXAxis(0, 90),
            new DriveXAxis(90, 0), 
            new Align("sharp", 11, 0, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH3 // 41
        {
            new DriveXAxis(-80, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 14, 0, 0),
            new DriveXAxis(0, -90),
            new Align("sharp", 18, 0, 0),
            new DriveXAxis(0, 90),
            new Align("sharp", 15, 0, 0),
            new Transition()
        },
            // MOV_IN_CH2_TO_CON1 // 42
        {
            new Drivefor2Motors(-600, 0),
            new Drivefor2Motors(0, 90),
            new Drivefor2Motors(300, 0),
            new Drivefor2Motors(0, 90),
            new Drivefor2Motors(30, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(2900, 0),
            new Transition()
        },
            // MOV_IN_CON1_TO_CH2 // 43
        {
            new Drivefor2Motors(-2500, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(50, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
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

        {
            new End(),
            new Transition()
        }
    };
}