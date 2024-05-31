package frc.robot;

import frc.robot.subsystems.Training;
import frc.robot.Logic.LogicCore;
import frc.robot.Logic.TreeTraverse;
import frc.robot.StateMachine.StateMachine;
import frc.robot.MachineVision.JavaCam;;

public class RobotContainer {

  public static RobotContainer m_robotContainer;
  public static Training train;
  public static JavaCam javcam;
  public static int checkAppleResult;
  public static LogicCore logic; 
  public static TreeTraverse traverse;

  public RobotContainer() {
    train = new Training();
    logic = new LogicCore();
    traverse = new TreeTraverse();

    Runnable runnableJavaCam = new JavaCam();
    Thread threadJavaCam = new Thread(runnableJavaCam);
    threadJavaCam.setDaemon(true);
    threadJavaCam.start();

    train.setDefaultCommand(new StateMachine());
  }
}
