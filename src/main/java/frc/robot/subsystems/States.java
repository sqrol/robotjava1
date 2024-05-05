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
        
            // MOV_IN_START_TO_CH1
        {
            new StartPos(),
            new Align("sharp", 15, 0),
            new Transition(1)
        },
            // MOV_IN_CH1_TO_THIRD_RZ
        {
            new Drivefor2Motors(0, -91),   
            new Drivefor2Motors(260, 0),
            new Transition(2)
        },
            // GRAB_POS_17
        {
            
        }, 
            // MOV_IN_THIRD_RZ_TO_CH1
        {
            new Drivefor2Motors(-1300, 0),
            new Align("sonic", 20, 0),    
            new Drivefor2Motors(0, 93),   
            new Align("sharp", 7, 0),     
        }, 
            // MOV_IN_CH1_TO_CON2
        {
            new Drivefor2Motors(0, -90),
            new Drivefor2Motors(620, 0),
            new Drivefor2Motors(0, -90),
            new Align("sharp", 7, 0),   
        }, 
            // MOV_IN_CON2_TO_CH1
        {
            new Drivefor2Motors(-1000, 0),
            new Align("sonic", 30, 0),    
            new Drivefor2Motors(0, -90),  
            new Align("sharp", 7, 0),     
            new Drivefor2Motors(0, 180),  
        },
            // MOVE_IN_CH1_TO_FINISH
        {
            new Drivefor2Motors(1700, 0),
            new Drivefor2Motors(0, -90), 
            new Drivefor2Motors(600, 0), 
            new End()
        }
    };
}
