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
    public static IState[][] mainStates = new IState[][] {{
        new StartPos(),
        // от старта к дереву 1 справа и к сбросу 1 
        new Align("sharp", 7.5, 0),
        new Drivefor2Motors(0, -91),
        new Drivefor2Motors(260, 0),
        new Drivefor2Motors(-1300, 0),
        new Align("sonic", 20, 0),
        new Drivefor2Motors(0, 91),
        new Align("sharp", 7.5, 0),
        new End()
    },

    {
        new Align("sharp", 8, 0),
        
        
        
        new Drivefor2Motors(-1100, 0),
    },

    {
        
    },

    {
        // // от старта к дереву 1 спереди справа и к сбросу 1
        // new Drivefor2Motors(350, 0),
        // new Drivefor2Motors(0, -90),
        // new Drivefor2Motors(600, 0),
        // new Drivefor2Motors(-1350, 0),
        // new Drivefor2Motors(0, 90),
        // new Align("sharp", 8, 0),
    }
};
}
