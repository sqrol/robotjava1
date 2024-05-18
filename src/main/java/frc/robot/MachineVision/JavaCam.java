package frc.robot.MachineVision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;


public class JavaCam implements Runnable
{
    private static UsbCamera camera;
    private CvSink cvSink;
    private static CvSource outStream, outStream2, outStream3;

    public int nowTask, nowResult = 0;

    public String colorCube, colorStand;
    public boolean alignCamera = false;

    @Override
    public void run() {

        SmartDashboard.putNumber("RED1", 0.0);
        SmartDashboard.putNumber("RED2", 0.0);
        SmartDashboard.putNumber("GREEN1", 0.0);
        SmartDashboard.putNumber("GREEN2", 0.0);
        SmartDashboard.putNumber("BLUE1", 0.0);
        SmartDashboard.putNumber("BLUE2", 0.0);
        
        camera = CameraServer.getInstance().startAutomaticCapture(); // Находим доступные камеры и подсасывам его
        camera.setResolution(640, 480); // Разрешение
        camera.setFPS(30); // Частота кадров
        cvSink = CameraServer.getInstance().getVideo(camera);
        settingCameraParameters(false);
        // camera.getProperty("name").setString("value");
        
        outStream = CameraServer.getInstance().putVideo("outStream", 640, 480);
        outStream2 = CameraServer.getInstance().putVideo("outBlur", 640, 480);
        outStream3 = CameraServer.getInstance().putVideo("outHSV", 640, 480);

        while (true) {
            try {
                final Mat source = new Mat();
                if (cvSink.grabFrame(source) == 0) {
                    continue;
                }
                RobotContainer.train.checkAppleResult = CheckApple(source);
                SmartDashboard.putNumber("checkAppleResult", RobotContainer.train.checkAppleResult);
                source.release();
            } catch (final Exception e) {
                DriverStation.reportError("An error occurred in JavaCam: " + e.getMessage(), false);
                e.printStackTrace();
            }
        }
    }

    public static int CheckApple(final Mat orig) // Распознавание яблока
    {
        // Нужно заменить массивом
        final double red1 = SmartDashboard.getNumber("RED1", 0);
        final double red2 = SmartDashboard.getNumber("RED2", 0);

        final double green1 = SmartDashboard.getNumber("GREEN1", 0);
        final double green2 = SmartDashboard.getNumber("GREEN2", 0);

        final double blue1 = SmartDashboard.getNumber("BLUE1", 0);
        final double blue2 = SmartDashboard.getNumber("BLUE2", 0);

        final Point redPoint = new Point(red1, red2);
        final Point greenPoint = new Point(green1, green2);
        final Point bluePoint = new Point(blue1, blue2);

        // final Point redPoint = new Point(0, 9);
        // final Point greenPoint = new Point(86, 255); // красное яблоко
        // final Point bluePoint = new Point(0, 255);

        // final Point redPoint = new Point(17, 245);
        // final Point greenPoint = new Point(200, 240); // желтая груша
        // final Point bluePoint = new Point(170, 250);

        // final Point redPoint = new Point(31, 190);
        // final Point greenPoint = new Point(90, 200); // зеленая груша
        // final Point bluePoint = new Point(70, 170);

        Mat hsvImage = new Mat();
        Mat blurMat = Viscad2.Blur(orig, 10);
        Imgproc.cvtColor(blurMat, hsvImage, Imgproc.COLOR_BGR2HSV);

        final Mat redApple = Viscad2.Threshold(orig, new Point(0, 9), new Point(86, 255), new Point(0, 255));
        final Mat greenPear = Viscad2.Threshold(orig, new Point(31, 190), new Point(90, 200), new Point(70, 170));
        final Mat yellowPear = Viscad2.Threshold(orig, new Point(17, 245), new Point(200, 240), new Point(170, 250));

        final Mat filledYellowPear = Viscad2.FillHolesCAD(yellowPear);
        final Mat filledRedApple = Viscad2.FillHolesCAD(redApple);
        final Mat filledGreenPear = Viscad2.FillHolesCAD(greenPear);

        final int imageAreaYellowPear = Viscad2.ImageTrueArea(filledYellowPear);
        final int imageAreaGreenPear = Viscad2.ImageTrueArea(filledGreenPear);
        final int imageAreaRedApple = Viscad2.ImageTrueArea(filledRedApple); // filledRedApple - 9500 // filledGreenPear
                                                                             // - 8100 // filledYellowPear - 7400

        SmartDashboard.putNumber("ImageAreaYellowPear", imageAreaYellowPear);
        SmartDashboard.putNumber("ImageAreaGreenPear", imageAreaGreenPear);
        SmartDashboard.putNumber("ImageAreaRedApple", imageAreaRedApple);

        // final Mat threshImage = Viscad2.Threshold(blurMat, redPoint, greenPoint,
        // bluePoint);

        // Mat threshImage = new Mat();

        // Core.inRange(hsvImage, new Scalar(redPoint.x, greenPoint.x, bluePoint.x), new
        // Scalar(redPoint.y, greenPoint.y, bluePoint.y), threshImage);

        // final int imageArea = Viscad2.ImageTrueArea(threshImage);

        outStream.putFrame(filledYellowPear);
        outStream2.putFrame(filledRedApple); // Передача обработанного кадра на выходной поток
        outStream3.putFrame(filledGreenPear); // Передача обработанного кадра на выходной поток

        blurMat.release();
        filledRedApple.release();
        hsvImage.release();
        filledYellowPear.release();
        filledGreenPear.release();

        if (imageAreaYellowPear > 7400) {
            return 1;
        } else if(imageAreaGreenPear > 8100){
            return 2;
        } else if(imageAreaRedApple > 9500) {
            return 3;
        } else {
            return 0; 
        }
        
    }

    // С этим нужно поиграться не могу сказать что 100% работает!
    public static void settingCameraParameters(final boolean mode) {
        if (mode) {
            camera.getProperty("white_balance_temperature_auto").set(0);
            // camera.getProperty("white_balance_temperature").set(4500);
            // camera.getProperty("focus_auto").set(1);
            camera.getProperty("focus_auto").set(0);
            camera.getProperty("exposure_auto").set(0);
            camera.getProperty("brightness").set(0);
            camera.getProperty("contrast").set(9);
            camera.getProperty("sharpness").set(30);
            camera.getProperty("exposure_absolute").set(3000);
            // camera.getProperty("backlight_compensation").set(10);
            camera.getProperty("focus_absolute").set(0); // 0-40
            camera.getProperty("saturation").set(100);
            camera.getProperty("power_line_frequency").set(2);
            camera.getProperty("pan_absolute").set(7200);
            camera.getProperty("tilt_absolute").set(-7200);
        } else {
            camera.getProperty("exposure_auto").set(1);
            camera.getProperty("white_balance_temperature_auto").set(1);
            camera.getProperty("focus_auto").set(1); 
        }
    }

}