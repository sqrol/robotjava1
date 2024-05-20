package frc.robot.MachineVision;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Main;
import frc.robot.RobotContainer;


public class JavaCam implements Runnable
{
    private static UsbCamera camera;
    private CvSink cvSink;
    private static CvSource outStream, outBlur, outHSV, mask;

    public int nowTask = 0;

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
        outBlur = CameraServer.getInstance().putVideo("outBlur", 640, 480);
        outHSV = CameraServer.getInstance().putVideo("outHSV", 640, 480);
        mask = CameraServer.getInstance().putVideo("mask", 640, 480);

        while (true) {
            try {
                final Mat source = new Mat();
                if (cvSink.grabFrame(source) == 0) {
                    continue;
                    
                }
                SmartDashboard.putNumber("nowTask", RobotContainer.train.nowTask);

                if (RobotContainer.train.nowTask == 1) {
                    RobotContainer.train.nowResult = CheckFruit(source); 
                }

                // if (Main.currentCameraCommand != null) {
                //     RobotContainer.train.cameraResult = Main.currentCameraCommand.execute(source);
                // }

                source.release();
            } catch (final Exception e) {
                DriverStation.reportError("An error occurred in JavaCam: " + e.getMessage(), false);
                e.printStackTrace();
            }
        }
    }

    public static int CheckFruit(final Mat orig) // Распознавание яблока
    {
        // Нужно заменить массивом
        double red1 = SmartDashboard.getNumber("RED1", 0);
        double red2 = SmartDashboard.getNumber("RED2", 0);

        double green1 = SmartDashboard.getNumber("GREEN1", 0);
        double green2 = SmartDashboard.getNumber("GREEN2", 0);

        double blue1 = SmartDashboard.getNumber("BLUE1", 0);
        double blue2 = SmartDashboard.getNumber("BLUE2", 0);

        // Point redPoint = new Point(red1, red2);
        // Point greenPoint = new Point(green1, green2);
        // Point bluePoint = new Point(blue1, blue2);

        Point redPoint1 = new Point(red1, red2);      // Point(0, 200);
        Point redPoint2 = new Point(green1, green2);  // Point(160, 255);
        Point redPoint3 = new Point(blue1, blue2);    // Point(200, 255);

        Point yellowPoint1 = new Point(0, 200);      // Point(34, 255);
        Point yellowPoint2 = new Point(160, 255);    // Point(70, 255);
        Point yellowPoint3 = new Point(200, 255);    // Point(200, 255);

        Mat blurMat = Viscad2.Blur(orig, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        
        Mat maskRedApple = Viscad2.Threshold(hsvImage, new Point(0, 24), new Point(25, 255), new Point(56, 255));
        Mat maskGreenApple = Viscad2.Threshold(hsvImage, new Point(34, 255), new Point(70, 255), new Point(200, 255));
        Mat maskYellowPear = Viscad2.Threshold(hsvImage, redPoint1, redPoint2, redPoint3);
        Mat maskGreenPear = Viscad2.Threshold(hsvImage, new Point(39, 50), new Point(210, 255), new Point(201, 255));

        Mat erodeRedApple = Viscad2.Erode(maskRedApple, 1); 
        Mat erodeGreenApple = Viscad2.Erode(maskGreenApple, 1);
        Mat erodeYellowPear = Viscad2.Erode(maskYellowPear, 1);
        Mat erodeGreenPear = Viscad2.Erode(maskGreenPear, 3);

        Mat dilateRedApple = Viscad2.Dilate(erodeRedApple, 1); 
        Mat dilateGreenApple = Viscad2.Dilate(erodeGreenApple, 4);
        Mat dilateYellowPear = Viscad2.Dilate(erodeYellowPear, 1);
        Mat dilateGreenPear = Viscad2.Dilate(erodeGreenPear, 2);

        Mat fillHolesGreenPear = Viscad2.FillHolesCAD(dilateGreenPear);

        int imageAreaRedApple = Viscad2.ImageTrueArea(dilateRedApple); 
        int imageAreaGreenApple = Viscad2.ImageTrueArea(dilateGreenApple);
        int imageAreaYellowPear = Viscad2.ImageTrueArea(dilateYellowPear);
        int imageAreaGreenPear = Viscad2.ImageTrueArea(fillHolesGreenPear);

        SmartDashboard.putNumber("ImageAreaRedApple", imageAreaRedApple);
        SmartDashboard.putNumber("ImageAreaGreenApple", imageAreaGreenApple);
        SmartDashboard.putNumber("ImageAreaYellowPear", imageAreaYellowPear);
        SmartDashboard.putNumber("ImageAreaGreenPear", imageAreaGreenPear);

        outStream.putFrame(blurMat);
        outBlur.putFrame(dilateYellowPear);
        outHSV.putFrame(dilateGreenApple);
        mask.putFrame(fillHolesGreenPear);

        erodeRedApple.release();
        erodeGreenApple.release();
        dilateGreenApple.release();
        dilateRedApple.release();
        maskRedApple.release();
        maskGreenApple.release();
        maskYellowPear.release();
        dilateYellowPear.release();
        erodeYellowPear.release();
        blurMat.release();
        dilateGreenPear.release();
        erodeGreenPear.release();
        maskGreenPear.release();
        fillHolesGreenPear.release();
        // filledRedApple.release();
        hsvImage.release();

        if(imageAreaRedApple > 15000) {
            return 1;
        } else {
            return 0; 
        }
        
    }

    // С этим нужно поиграться не могу сказать что 100% работает!
    public static void settingCameraParameters(final boolean mode) {
        if (mode) {
            // camera.getProperty("white_balance_temperature_auto").set(0);
            // // camera.getProperty("white_balance_temperature").set(4500);
            // // camera.getProperty("focus_auto").set(1);
            // camera.getProperty("focus_auto").set(0);
            // camera.getProperty("exposure_auto").set(0);
            // camera.getProperty("brightness").set(0);
            // camera.getProperty("contrast").set(9);
            // camera.getProperty("sharpness").set(30);
            // camera.getProperty("exposure_absolute").set(3000);
            // // camera.getProperty("backlight_compensation").set(10);
            // camera.getProperty("focus_absolute").set(0); // 0-40
            // camera.getProperty("saturation").set(100);
            // camera.getProperty("power_line_frequency").set(2);
            // camera.getProperty("pan_absolute").set(7200);
            // camera.getProperty("tilt_absolute").set(-7200);
        } else {
            // camera.getProperty("white_balance_temperature").set(9999);
            // camera.getProperty("white_balance_temperature_auto").set(1);
            camera.getProperty("focus_auto").set(0); 
            // camera.getProperty("exposure_auto").set(0);
            // camera.getProperty("brightness").set(0);
            // camera.getProperty("exposure_absolute").set(1);
        }
    }

}