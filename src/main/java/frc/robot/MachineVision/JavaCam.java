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
    private static CvSource outStream, outBlur, outHSV, mask, oustream1;

    public int nowTask = 0;

    public String colorCube, colorStand;
    public boolean alignCamera = false;

    @Override
    public void run() {

        // SmartDashboard.putNumber("RED1 YP", 0.0);
        // SmartDashboard.putNumber("RED2 YP", 0.0);
        // SmartDashboard.putNumber("GREEN1 YP", 0.0);
        // SmartDashboard.putNumber("GREEN2 YP", 0.0);
        // SmartDashboard.putNumber("BLUE1 YP", 0.0);
        // SmartDashboard.putNumber("BLUE2 YP", 0.0);

        // SmartDashboard.putNumber("RED1 RA", 0.0);
        // SmartDashboard.putNumber("RED2 RA", 0.0);
        // SmartDashboard.putNumber("GREEN1 RA", 0.0);
        // SmartDashboard.putNumber("GREEN2 RA", 0.0);
        // SmartDashboard.putNumber("BLUE1 RA", 0.0);
        // SmartDashboard.putNumber("BLUE2 RA", 0.0);

        // SmartDashboard.putNumber("RED1 GP", 0.0);
        // SmartDashboard.putNumber("RED2 GP", 0.0);
        // SmartDashboard.putNumber("GREEN1 GP", 0.0);
        // SmartDashboard.putNumber("GREEN2 GP", 0.0);
        // SmartDashboard.putNumber("BLUE1 GP", 0.0);
        // SmartDashboard.putNumber("BLUE2 GP", 0.0);
        
        camera = CameraServer.getInstance().startAutomaticCapture(); // Находим доступные камеры и подсасывам его
        camera.setResolution(640, 480); // Разрешение
        camera.setFPS(30); // Частота кадров
        cvSink = CameraServer.getInstance().getVideo(camera);
        settingCameraParameters(false);
        // camera.getProperty("name").setString("value");
        
        outStream = CameraServer.getInstance().putVideo("outStream", 640, 480);
        outBlur = CameraServer.getInstance().putVideo("outBlur", 640, 480);
        mask = CameraServer.getInstance().putVideo("mask", 640, 480);
        oustream1 = CameraServer.getInstance().putVideo("outstream1", 640, 480);

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
        double red1YP = SmartDashboard.getNumber("RED1 YP", 0);
        double red2YP = SmartDashboard.getNumber("RED2 YP", 0);

        double green1YP = SmartDashboard.getNumber("GREEN1 YP", 0);
        double green2YP = SmartDashboard.getNumber("GREEN2 YP", 0);

        double blue1YP = SmartDashboard.getNumber("BLUE1 YP", 0);
        double blue2YP = SmartDashboard.getNumber("BLUE2 YP", 0);
        

        double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
        double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

        double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
        double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

        double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
        double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);


        double red1GP = SmartDashboard.getNumber("RED1 GP", 0);
        double red2GP = SmartDashboard.getNumber("RED2 GP", 0);

        double green1GP = SmartDashboard.getNumber("GREEN1 GP", 0);
        double green2GP = SmartDashboard.getNumber("GREEN2 GP", 0);

        double blue1GP = SmartDashboard.getNumber("BLUE1 GP", 0);
        double blue2GP = SmartDashboard.getNumber("BLUE2 GP", 0);

        // Point redPoint = new Point(red1, red2);
        // Point greenPoint = new Point(green1, green2);
        // Point bluePoint = new Point(blue1, blue2);

        Point yellowPoint1 = new Point(29, 234);      // Point(0, 200);
        Point yellowPoint2 = new Point(198, 236);  // Point(160, 255);
        Point yellowPoint3 = new Point(249, 255);    // Point(200, 255);

        Point redPoint1 = new Point(2, 23);      // Point(34, 255);
        Point redPoint2 = new Point(11, 255);    // Point(70, 255);
        Point redPoint3 = new Point(222, 255);    // Point(200, 255);

        Point greenPoint1 = new Point(43, 216);
        Point greenPoint2 = new Point(38, 255);
        Point greenPoint3 = new Point(226, 255);

        Point greenPoint21 = new Point(38, 189);  
        Point greenPoint22 = new Point(201, 229);
        Point greenPoint23 = new Point(95, 247);

        Mat blurMat = Viscad2.Blur(orig, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        
        Mat maskRedApple = Viscad2.Threshold(hsvImage, new Point(2, 23), new Point(11, 255), new Point(222, 255));
        Mat maskGreenApple = Viscad2.Threshold(hsvImage, new Point(45, 200), new Point(160, 255), new Point(178, 255));
        Mat maskYellowPear = Viscad2.Threshold(hsvImage, new Point(29, 234), new Point(198, 236), new Point(249, 255));
        Mat maskGreenPear = Viscad2.Threshold(hsvImage, new Point(38, 189), new Point(201, 229), new Point(95, 247));

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
        
        mask.putFrame(fillHolesGreenPear);
        oustream1.putFrame(dilateRedApple);

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
        orig.release();
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
            // camera.getProperty("exposure_auto").set(4);
            camera.getProperty("saturation").set(49);
            camera.getProperty("brightness").set(0);
            // camera.getProperty("exposure_absolute").set(100);
        }
    }

}