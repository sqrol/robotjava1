package frc.robot.subsystems;

import java.util.Set;

import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.Drivefor2Motors;
import frc.robot.subsystems.Cases.End;
import frc.robot.subsystems.Cases.OMS;
import frc.robot.subsystems.Cases.Odometry;
import frc.robot.subsystems.Cases.Reset;
import frc.robot.subsystems.Cases.Sensors;
import frc.robot.subsystems.Cases.SetGlidePosition;
import frc.robot.subsystems.Cases.SetGrabPos;
import frc.robot.subsystems.Cases.SetGripRotatePos;
import frc.robot.subsystems.Cases.SetLiftPosition;
import frc.robot.subsystems.Cases.SetMainRotatePos;
import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.StartPos;
import frc.robot.subsystems.Cases.Transition;

public class States {
    public static IState[][] mainStates = new IState[][] {
                                           // 0 
        {
            new StartPos(),

            new Transition(24),

            new End()
        },
        
            // MOV_IN_START_TO_CH1 // 1
        {
            new Align("sharp", 15, 0, 0),
            
        },
            // MOV_IN_CH1_TO_THIRD_RZ // 2
        {
            new Drivefor2Motors(0, -91),   
            new Drivefor2Motors(260, 0),
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
        }, 
            // MOV_IN_CH1_TO_CON2 // 5
        {
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(620, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),   
        }, 
            // MOV_IN_CON2_TO_CH1 // 6
        {
            new Drivefor2Motors(-1000, 0),
            new Align("sonic", 30, 0, 0),    
            new Drivefor2Motors(0, -90),  
            new Align("sharp", 8, 0, 0),     
            new Drivefor2Motors(0, 180),  
        },
            // MOVE_IN_CH1_TO_FINISH // 7
        {
            new Drivefor2Motors(1700, 0),
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(600, 0), 
            new End()
        },
            // MOV_IN_CH1_TO_THIRD_LOZ // 8
        {
            new Drivefor2Motors(0, 180),
            new Drivefor2Motors(400, 0),
            new Drivefor2Motors(0, 90), 
            new Drivefor2Motors(740, 0),
            new Drivefor2Motors(0, 90), 
            new Drivefor2Motors(80, 0), 
        },
            // MOV_IN_THIRD_LOZ_TO_CH2 // 9
        {
            new Drivefor2Motors(-100, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),    
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0), 
        },
            // MOV_IN_CH2_TO_CON4 // 10
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(2400, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 10, 0, 0), 
            new Transition(11)  
        },
            // MOV_IN_CON4_TO_CH2 // 11
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),    
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0), 
            new Transition(12) 
        },
            // MOVE_IN_CH2_TO_FINISH // 12
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1700, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(180, 0), 
            new Transition(24)
        },
            // MOV_IN_CH1_TO_THIRD_LZ // 13
        {
            new Drivefor2Motors(0, 180), 
            new Drivefor2Motors(400, 0), 
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1320, 0),
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0),    
        },
            // MOV_IN_CH2_TO_CON4 // 14
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(1990, 0),
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),    
        },
            // MOV_IN_CON4_TO_CH2 // 15
        {
            new Drivefor2Motors(-400, 0),
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(2090, 0),
            new Drivefor2Motors(0, 90),  
            new Align("sharp", 8, 0, 0),    
        },
            // MOV_IN_THIRD_LZ_TO_CH2 // 16
        {
            new Align("sharp", 8, 0, 0), 
        },
            // MOV_IN_START_TO_CH2 // 17
        {
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(1080, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(300, 0), 
            new Drivefor2Motors(0, -90), 
            new Align("sharp", 8, 0, 0),
            new Transition(22)   
        },
            // MOV_IN_CH2_TO_SECOND_LOZ // 18
        {
            new Drivefor2Motors(-220, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(-800, 0),
        },
            // MOV_IN_SECOND_LOZ_TO_CH2 // 19
        {
            new Drivefor2Motors(800, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
        },
            // MOV_IN_CH2_TO_SECOND_RZ // 20
        {
            new Drivefor2Motors(0, -90),
            
        },
            // MOV_IN_SECOND_RZ_TO_CH2 // 21
        {
            new Drivefor2Motors(0, -180),
            new Align("sharp", 8, 0, 0),
        },
            // MOV_IN_CH2_TO_SECOND_LZ // 22
        {
            new Drivefor2Motors(-220, 0),
            new Drivefor2Motors(0, 90),  
            new Drivefor2Motors(-940, 0), // поменять на соник
            new Transition(23)
        },
            // MOV_IN_SECOND_LZ_TO_CH2 // 23
        {
            new Drivefor2Motors(670, 0), // поменять на соник
            new Drivefor2Motors(0, -90),
            new Align("sharp", 8, 0, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
        },
            // MOV_IN_START_TO_CH3  // 24
        {
            new Drivefor2Motors(0, 90),
            new Drivefor2Motors(850, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(25)
        },
            // MOV_IN_CH3_TO_FRIST_LZ // 25
        {
            new Align("sharp", 8, 0, 0),
            new Transition(26)
        },   
            // MOV_IN_FRIST_LZ_TO_CH3 // 26
        {
            new Align("sharp", 8, 0, 0),
            new Transition(33)
        },
            // MOV_IN_CH3_TO_CON4 // 27
        {
            new Drivefor2Motors(-1000, 0),
            new Drivefor2Motors(0, 180),
            new Align("sharp", 8, 0, 0),
            new Transition(28)
        },
            // MOV_IN_CON4_TO_CH3 // 28
        {
            new Drivefor2Motors(-1000, 0),
            new Drivefor2Motors(0, 180),
            new Align("sharp", 8, 0, 0),
            new Transition(29)
        },
            // MOVE_IN_CH3_TO_FINISH // 29
        {
            new Drivefor2Motors(-700, 0),
            new Drivefor2Motors(0, 90),
            new Drivefor2Motors(700, 0),
            new Transition(33)
        },
            // MOV_IN_CH3_TO_FRIST_LOZ // 30
        {
            
            new Drivefor2Motors(600, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(27)
        },
            // MOV_IN_CH3_TO_FRIST_RZ // 31
        {
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(-1000, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(32)
        },
            // MOV_IN_FRIST_RZ_TO_CH3 // 32
        {
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(1050, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(27)
        },
            // MOV_IN_CH3_TO_CON3 // 33
        {
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(-1830, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(34)
        },
            // MOV_IN_CON3_TO_CH3 //34
        {
            
            new Drivefor2Motors(-300, 0),
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(1850, 0),
            new Drivefor2Motors(0, 90),
            new Align("sharp", 8, 0, 0),
            new Transition(35)   
        },

        {
            new End()
        }
    };
}