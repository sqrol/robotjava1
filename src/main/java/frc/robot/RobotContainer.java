package frc.robot;

import frc.robot.subsystems.Training;
import frc.robot.Logic.Change;
import frc.robot.Logic.LogicInit;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.JavaCam;

public class RobotContainer {

  public static RobotContainer m_robotContainer;

  public static Training train;
  public static JavaCam javcam;
  public static LogicInit logic;
  public static Change change;
  public static int checkAppleResult;

  private RobotContainer()
  {
      train = new Training();

      // Runnable runnableJavaCam = new JavaCam();
      // Thread threadJavaCam = new Thread(runnableJavaCam);
      // threadJavaCam.setDaemon(true);
      // threadJavaCam.start();

      logic = new LogicInit();
      change = new Change();

      train.setDefaultCommand(new Drive());
  }
  
  public static RobotContainer getRobotContainer() {
    if(m_robotContainer == null) {
      m_robotContainer = new RobotContainer();
    }
    return m_robotContainer;
  }

}
