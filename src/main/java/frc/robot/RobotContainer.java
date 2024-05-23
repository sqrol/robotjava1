package frc.robot;

import frc.robot.subsystems.Training;
import frc.robot.Logic.LogicCore;
import frc.robot.StateMachine.Drive;
import frc.robot.MachineVision.JavaCam;;

public class RobotContainer {

  public static RobotContainer m_robotContainer;
  public static Training train;
  public static JavaCam javcam;
  public static int checkAppleResult;
  public static LogicCore logic; 

  private RobotContainer() {
    train = new Training();

    logic = new LogicCore();

    Runnable runnableJavaCam = new JavaCam();
    Thread threadJavaCam = new Thread(runnableJavaCam);
    threadJavaCam.setDaemon(true);
    threadJavaCam.start();

    train.setDefaultCommand(new Drive());
  }
  
  public static RobotContainer getRobotContainer() {
    if(m_robotContainer == null) {
      m_robotContainer = new RobotContainer();
    }
    return m_robotContainer;
  }
}
