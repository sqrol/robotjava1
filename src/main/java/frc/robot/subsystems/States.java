package frc.robot.subsystems;

import java.util.Set;

import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.Drivefor2Motors;
import frc.robot.subsystems.Cases.End;
import frc.robot.subsystems.Cases.Logic;
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
        // new Align("sonic", 50, 0),
        // new SetGlidePosition(200),
        // new Drivefor2Motors(2000, 0),
        // new Drivefor2Motors(0, 180),
        // new SetLiftPosition(200),
        // new Drivefor2Motors(700, 0),
        // new Drivefor2Motors(0, 90),
        // new Drivefor2Motors(700, 0),
        // new Drivefor2Motors(0, 90),
        // new Drivefor2Motors(700, 0),
        // new Drivefor2Motors(0, 90),
        // new Drivefor2Motors(700, 0),
        // new Drivefor2Motors(0, 90),

        // new SetLiftPosition(100),
        // new Logic(),
        // new SetLiftPosition(0),
        // new SetLiftPosition(100),
        // new Transition(),

        // new SetGlidePos(150),
        // new SetGripRotatePos("FLOOR"),
        // new SetGrabPos("OPEN"),
        // new SetGrabPos("PEAR"),
        // new SetGripRotatePos("BRANCH"),

        // new 
        // new SetLiftPosition(200),
        // new SetGrabPos("PEAR"),
        // new Drivefor2Motors(100, 0),
        // new SetGrabPos("SMALL APPLE"),
        // new SetLiftPosition(480),
        // new SetGlidePos(290),
        // new Odometry("absolute", 0, 0, 0),
        // new SetGrabPos("SMALL APPLE"),
        // new SetGrabPos("OPEN"),
        // new SetGrabPos("PEAR"),
        // new SetLiftPosition(0),
        
        // new SetLiftPosition(100),
        // new SetGlidePos(200),
        // new SetMainRotatePos(200),
        
        // new SetGrabPos("OPEN"),
        // new SetGlidePos(100),
        // new SetGrabPos("PEAR"),
        // new SetLiftPosition(100),
        // new SetGlidePos(300),
        // new Odometry("relative", 0, 0, 180),
        // new Reset("/", 0, 0, 0),
        // new Sensors("sonic", 0, -10, 0),
        // new Odometry("relative", 0, 300, 0),
        // new Odometry("relative", 0, 0, 180),

        
        // поле
        // --------------------------------------
        new End(),
    },

    {
        
    }
};
}
