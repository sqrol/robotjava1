package frc.robot.subsystems;

import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.End;
import frc.robot.subsystems.Cases.Odometry;
import frc.robot.subsystems.Cases.Reset;
import frc.robot.subsystems.Cases.Sensors;
import frc.robot.subsystems.Cases.Align;
import frc.robot.subsystems.Cases.StartPos;

public class States {
    public static IState[][] mainStates = new IState[][] {{
        new StartPos(),
        // new Odometry("absolute", 1000, 0, 90),
        // new Reset("/", 0, 0, 0),
        // new Reset("/", 0, 0, 0),
        // new Reset("/", 0, 0, 0),
        new Odometry("absolute", 500, 0, 90),
        // new Align("sonic", 0, 22, 0),
        // new Align("sharp", 15, 0, 0),
        // new Align("sonic", 0, -7, 0),
        // new Align("rotate", 0, 0, 90),
        // new Align("sonic", 0, -13, 0),
        // new Reset("/", 0 , 0 , 0),
        // new Align("rotate", 0, 0, -90),
        // new Reset("/", 0, 0, 0),
        // new Align("sonic", 0, -13, 0),
        // new Reset("/", 0 , 0 , 0),
        // new Align("rotate", 0, 0, 90),
        // new Reset("/", 0 , 0 , 0),
        // new Align("sonic", 0, 20, 0),

        

        // new Odometry("absolute", 1000, 0, 0),
        // new Odometry("absolute", 600, -600, 0),
        // new Odometry("absolute", 0, -600, 0),
        // new Odometry("absolute", 0, 0, 0),


        // new Odometry("relative", 0, 0, 180),
        // new Reset("/", 0, 0, 0),
        // new Sensors("sonic", 0, -10, 0),
        // new Odometry("relative", 0, 300, 0),
        // new Odometry("relative", 0, 0, 180),



        // поле
        // new Odometry("relative", -400, 0, 0),
        // new Odometry("relative", -200, 500, 0),
        // new Odometry("relative", 0, 500, 0),
        // new Odometry("relative", 600, 0, 0),
        // new Odometry("relative", 0, 300, 0),
        // new Odometry("relative", 2700, 0, 0),
        // new Odometry("relative", 0, 0, 90),
        // new Odometry("relative", -900, 0, 0),
        // new Sensors("sharp", 17 , 0, 0),
        // new Odometry("relative", 0, 1200, 0),
        // new Reset("Gyro", 0, 0, 0),
        // new Odometry("relative", 0, 0, 180),
        // new Reset("Gyro", 0, 0, 0),
        // new Sensors("sharp", 16, 0, 0),
        // new Odometry("relative", 0, -700, 0),
        // --------------------------------------
        new End(),
    },{
        
    }};
}
