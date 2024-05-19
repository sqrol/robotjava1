package frc.robot.MachineVision;

import org.opencv.core.Mat;

public interface CameraCommand {
    int execute (Mat image);
}