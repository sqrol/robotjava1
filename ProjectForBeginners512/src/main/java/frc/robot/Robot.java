package frc.robot;

import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.StateMachine.CoreEngine.StateMachine;
import frc.robot.StateMachine.CoreEngine.CommandAdapter;

public class Robot extends TimedRobot {

  private Command adapter = new CommandAdapter(); 
  private ReentrantLock mutex = new ReentrantLock();
  
  @Override
  public void robotInit() {
    
    StateMachine.states.clear();
    
    initMaps();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    sendSmartDashBoard();
  }
  
  @Override
  public void disabledInit() { 
    if (adapter != null) {
      adapter.cancel();
    }
  }

  @Override
  public void autonomousInit() {
    if (adapter != null) {
      adapter.schedule();
    }
  }

  @Override 
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (adapter != null) {
      adapter.schedule();
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  private void initMaps() {

    Main.switchMap.put("startButton", false);
    Main.switchMap.put("EMSButton", false);
    Main.switchMap.put("limitSwitch", false);

    Main.sensorsMap.put("indicationMode", 1.0);

    Main.sensorsMap.put("cobraVoltage", 0.0);

    Main.sensorsMap.put("sharpRight", 0.0);
    Main.sensorsMap.put("sharpLeft", 0.0);

    Main.sensorsMap.put("sonicRight", 0.0);
    Main.sensorsMap.put("sonicLeft", 0.0);

    Main.sensorsMap.put("updateTime", 0.0);
    Main.sensorsMap.put("updateTimeCamera", 0.0);

    Main.sensorsMap.put("objectFind", 0.0);
    Main.sensorsMap.put("camTask", 0.0);
    Main.sensorsMap.put("posZ", 0.0);
    Main.sensorsMap.put("resetGyro", 0.0);
    Main.sensorsMap.put("srcGyro", 0.0);
    

    Main.motorControllerMap.put("encRight", 0.0);
    Main.motorControllerMap.put("encLeft", 0.0);
    Main.motorControllerMap.put("encRotate", 0.0);
    Main.motorControllerMap.put("encLift", 0.0);
    Main.motorControllerMap.put("resetEncs", 0.0);
    
    Main.motorControllerMap.put("resetEncRight", 0.0);
    Main.motorControllerMap.put("resetEncLeft", 0.0);
    Main.motorControllerMap.put("resetEncRotate", 0.0);
    Main.motorControllerMap.put("resetEncLift", 0.0);

    Main.motorControllerMap.put("rpm0", 0.0);
    Main.motorControllerMap.put("rpm1", 0.0);
    Main.motorControllerMap.put("rpm2", 0.0);
    Main.motorControllerMap.put("rpm3", 0.0);

    Main.motorControllerMap.put("rightPID", 0.0);
    Main.motorControllerMap.put("leftPID", 0.0);
    Main.motorControllerMap.put("rotatePID", 0.0);
    Main.motorControllerMap.put("liftPID", 0.0);
    Main.motorControllerMap.put("resetPID", 0.0);

    Main.motorControllerMap.put("servoGrab", 40.0);
    Main.motorControllerMap.put("servoGripRotate", 60.0);
    Main.motorControllerMap.put("glideServoSpeed", 0.0);

    Main.motorControllerMap.put("speedX", 0.0);
    Main.motorControllerMap.put("speedZ", 0.0);
    Main.motorControllerMap.put("rotateSpeed", 0.0);
    Main.motorControllerMap.put("liftSpeed", 0.0);

    Main.motorControllerMap.put("posX", 0.0);
    Main.motorControllerMap.put("updateTime", 0.0);
  }

  private void sendSmartDashBoard() {

    SmartDashboard.putNumber("sharpRight", Main.sensorsMap.get("sharpRight"));
    SmartDashboard.putNumber("sharpLeft", Main.sensorsMap.get("sharpLeft"));

    SmartDashboard.putNumber("sonicLeft", Main.sensorsMap.get("sonicLeft"));
    SmartDashboard.putNumber("sonicRight", Main.sensorsMap.get("sonicRight"));

    SmartDashboard.putBoolean("isResetZ", Main.sensorsMap.get("resetGyro") == 1.0);
    SmartDashboard.putBoolean("isResetEncs", Main.motorControllerMap.get("resetEncs") == 1.0);

    SmartDashboard.putNumber("rpmRight", Main.motorControllerMap.get("rpmRight"));
    SmartDashboard.putNumber("rpmLeft", Main.motorControllerMap.get("rpmLeft"));
    SmartDashboard.putNumber("rpmRotate", Main.motorControllerMap.get("rpmRotate"));
    SmartDashboard.putNumber("rpmLift", Main.motorControllerMap.get("rpmLift"));

    SmartDashboard.putNumber("encRight", Main.motorControllerMap.get("encRight"));
    SmartDashboard.putNumber("encLeft", Main.motorControllerMap.get("encLeft"));
    SmartDashboard.putNumber("encRotate", Main.motorControllerMap.get("encRotate"));
    SmartDashboard.putNumber("encLift", Main.motorControllerMap.get("encLift"));

    SmartDashboard.putNumber("speedX", Main.motorControllerMap.get("speedX"));
    SmartDashboard.putNumber("speedZ", Main.motorControllerMap.get("speedZ"));

    SmartDashboard.putNumber("posX", Main.motorControllerMap.get("posX"));
    SmartDashboard.putNumber("posZ", Main.sensorsMap.get("posZ"));

    SmartDashboard.putNumber("camTask", Main.sensorsMap.get("camTask"));

    SmartDashboard.putNumber("index", StateMachine.index);
    SmartDashboard.putNumber("updateTimeMotors", Main.motorControllerMap.get("updateTime"));
    SmartDashboard.putNumber("updateTimeSensors", Main.sensorsMap.get("updateTime"));
    SmartDashboard.putNumber("updateTimeCamera", Main.sensorsMap.get("updateTimeCamera"));

    SmartDashboard.putBoolean("EMS", Main.switchMap.get("EMSButton"));
    SmartDashboard.putBoolean("startButton", Main.switchMap.get("startButton"));
    SmartDashboard.putBoolean("limitSwitch", Main.switchMap.get("limitSwitch"));
    
    SmartDashboard.putNumber("objectFind", Main.sensorsMap.get("objectFind"));

    if (StateMachine.states.size() > 0) {
      SmartDashboard.putString("currentState", StateMachine.currentState.getClass().getSimpleName());
    } else {
      SmartDashboard.putString("currentState", "null");
    }
  }
}