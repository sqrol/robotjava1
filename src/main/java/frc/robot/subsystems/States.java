package frc.robot.subsystems;

import java.util.Set;

import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.Drivefor2Motors;
import frc.robot.subsystems.Cases.End;
import frc.robot.subsystems.Cases.Logic;
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
        new SetMainRotatePos(30),
        new OMS("FLOOR BIG APPLE"),
        new End()
    },

    {
        new StartPos(),
        
        new SetMainRotatePos(0),
        new OMS("FLOOR BIG APPLE"),
        new End()
    }
};
}
