package frc.robot.MachineVision;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FruitCheck implements CameraCommand {
    private static CameraCommand intance;

    private FruitCheck(){}

    @Override
    public int execute(final Mat src) {

        final double red1 = SmartDashboard.getNumber("RED1", 0);
        final double red2 = SmartDashboard.getNumber("RED2", 0);

        final double green1 = SmartDashboard.getNumber("GREEN1", 0);
        final double green2 = SmartDashboard.getNumber("GREEN2", 0);

        final double blue1 = SmartDashboard.getNumber("BLUE1", 0);
        final double blue2 = SmartDashboard.getNumber("BLUE2", 0);

        final Point redPoint = new Point(red1, red2);
        final Point greenPoint = new Point(green1, green2);
        final Point bluePoint = new Point(blue1, blue2);

        final Mat HSVImage = Viscad2.ConvertBGR2HSV(src);
        final Mat BlurImage = Viscad2.Blur(HSVImage, 3);
        final Mat threshImage = Viscad2.Threshold(BlurImage, redPoint, greenPoint, bluePoint);

        final Mat erdilImage = Viscad2.Erode(threshImage, 3);
        final Mat dilateImage = Viscad2.Dilate(erdilImage, 3);

        final int colorArea = Viscad2.ImageTrueArea(dilateImage);

        if (colorArea > 5000) {
            return 1;
        } else {
            return 0;
        }

    }

    public static CameraCommand getInstance() {
        if (intance == null){
            intance = new FruitCheck();
        }
        return intance;
    }

}