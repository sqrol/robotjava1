/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
      javcam = new JavaCam();

      Runnable camRunnable = new JavaCam();
      Thread camThread = new Thread(camRunnable);
      camThread.setDaemon(true);
      camThread.start();

      train.setDefaultCommand(new Drive());
  }
}
