package frc.robot;

import frc.robot.subsystems.Training;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.JavaCam;

public class RobotContainer {

  public static Training train;
  public static JavaCam javcam;

  public RobotContainer()
  {
      train = new Training();

      Runnable runnableJavaCam = new JavaCam();
      Thread threadJavaCam = new Thread(runnableJavaCam);
      threadJavaCam.setDaemon(true);
      threadJavaCam.start();
      
      train.setDefaultCommand(new Drive());
  }
}
