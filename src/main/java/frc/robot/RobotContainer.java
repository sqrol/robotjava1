package frc.robot;

import frc.robot.subsystems.Training;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.EMSThread;
import frc.robot.subsystems.InitThread;
import frc.robot.subsystems.JavaCam;

public class RobotContainer {

  public static RobotContainer m_robotContainer;
  public static Training train;
  public static JavaCam javcam;
  public static int checkAppleResult;

  private RobotContainer()
  {
      train = new Training();

      // Runnable runnableJavaCam = new JavaCam();
      // Thread threadJavaCam = new Thread(runnableJavaCam);
      // threadJavaCam.setDaemon(true);
      // threadJavaCam.start();

      // Runnable initRunnable = new InitThread();
      // Thread initThread = new Thread(initRunnable);
      // initThread.setDaemon(true);
      // initThread.start();

      // Runnable EMSRunnable = new EMSThread();
      // Thread EMSThread = new Thread(EMSRunnable);
      // EMSThread.setDaemon(true);
      // EMSThread.start();

      // logic = new LogicInit();
      // change = new Change();

      train.setDefaultCommand(new Drive());
  }
  
  public static RobotContainer getRobotContainer() {
    if(m_robotContainer == null) {
      m_robotContainer = new RobotContainer();
    }
    return m_robotContainer;
  }

}
