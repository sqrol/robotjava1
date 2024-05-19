package frc.robot.MachineVision;

import org.opencv.core.Mat;

public class DefaultCameraCommand implements CameraCommand {

    private static CameraCommand intance;

    private DefaultCameraCommand(){}

    @Override
    public int execute(Mat image) {
        return 0;
    }

    public static CameraCommand getInstance() {
        if (intance == null){
            intance = new DefaultCameraCommand();
        }
        return intance;
    }

}