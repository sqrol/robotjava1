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
    private static CvSource outStream, outStream2, outStream3, outStream4;

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
        outStream2 = CameraServer.getInstance().putVideo("outBlur", 640, 480);
        outStream3 = CameraServer.getInstance().putVideo("outHSV", 640, 480);
        outStream4 = CameraServer.getInstance().putVideo("mask", 640, 480);

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

        Point redPoint1 = new Point(0, 200);
        Point redPoint2 = new Point(160, 255);
        Point redPoint3 = new Point(200, 255);

        Point yellowPoint1 = new Point(0, 200);
        Point yellowPoint2 = new Point(160, 255);
        Point yellowPoint3 = new Point(200, 255);

        Mat blurMat = Viscad2.Blur(orig, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        
        Mat maskRedApple = Viscad2.Threshold(hsvImage, redPoint1, redPoint2, redPoint3);

        Mat erode = Viscad2.Erode(maskRedApple, 1); 
        Mat dilate = Viscad2.Dilate(erode, 1); 

        int imageAreaRedApple = Viscad2.ImageTrueArea(dilate); 

        SmartDashboard.putNumber("ImageAreaRedApple", imageAreaRedApple);

        outStream.putFrame(blurMat);
        outStream2.putFrame(hsvImage);
        outStream3.putFrame(dilate);
        
        erode.release();
        dilate.release();
        maskRedApple.release();
        blurMat.release();
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