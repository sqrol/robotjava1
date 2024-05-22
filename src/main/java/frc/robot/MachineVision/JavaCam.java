package frc.robot.MachineVision;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
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
    private static CvSource outStream, outBlur, outHSV, mask, oustream1, oustream2, oustream3;

    public int nowTask = 0;

    public String colorCube, colorStand;
    public boolean alignCamera = false;

    static List<Point> centersForClass = new ArrayList<>();

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

        oustream2 = CameraServer.getInstance().putVideo("con", 640, 480);

        oustream3 = CameraServer.getInstance().putVideo("con2", 640, 480);

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

                if (RobotContainer.train.nowTask == 2) {
                    RobotContainer.train.centersForClass = getFruitPosition(source);
                }

                // if (Main.currentCameraCommand != null) {
                // RobotContainer.train.cameraResult =
                // Main.currentCameraCommand.execute(source);
                // }

                source.release();
            } catch (final Exception e) {
                DriverStation.reportError("An error occurred in JavaCam: " + e.getMessage(), false);
                e.printStackTrace();
            }
        }
    }

    public static List<Point> getFruitPosition(final Mat orig) {

        final Mat blurMat = Viscad2.Blur(orig, 4);
        final Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

        final Mat maskRedApple = Viscad2.Threshold(hsvImage, new Point(2, 23), new Point(11, 255), new Point(222, 255));

        final Mat erodeRedApple = Viscad2.Erode(maskRedApple, 1);
        final Mat dilateRedApple = Viscad2.Dilate(erodeRedApple, 1);

        final Mat outPA = new Mat();

        List<Rect> currentCordinate = Viscad2.ParticleAnalysis(dilateRedApple, outPA);

        oustream2.putFrame(outPA);

        blurMat.release();
        hsvImage.release();
        maskRedApple.release();
        erodeRedApple.release();
        dilateRedApple.release();
        outPA.release();

        return processRectangles(orig, currentCordinate);
    }

    public static List<Point> processRectangles(Mat image, List<Rect> currentCordinate) {
        List<Point> centers = new ArrayList<>();

        for (Rect rect : currentCordinate) {
            int x = rect.x;
            int y = rect.y;
            int width = rect.width;
            int height = rect.height;

            // Рисуем прямоугольник на изображении
            Imgproc.rectangle(image, new Point(x, y), new Point(x + width, y + height), new Scalar(0, 255, 0), 2);

            // Выводим информацию о прямоугольнике
            System.out.println("Rectangle: ");
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("width: " + width);
            System.out.println("height: " + height);

            // Вычисляем центр прямоугольника
            int centerX = x + width / 2;
            int centerY = y + height / 2;
            Point center = new Point(centerX, centerY);

            centers.add(center);

            SmartDashboard.putNumber("centerX", centerX);
            SmartDashboard.putNumber("centerY", centerY);

            System.out.println("Center: (" + centerX + ", " + centerY + ")");
        }

        // Сохраняем изображение с нарисованными прямоугольниками
        oustream3.putFrame(image);
        return centers; 
    }

    public static int CheckFruit(final Mat orig) // Распознавание яблока
    {
        // Нужно заменить массивом
        final double red1YP = SmartDashboard.getNumber("RED1 YP", 0);
        final double red2YP = SmartDashboard.getNumber("RED2 YP", 0);

        final double green1YP = SmartDashboard.getNumber("GREEN1 YP", 0);
        final double green2YP = SmartDashboard.getNumber("GREEN2 YP", 0);

        final double blue1YP = SmartDashboard.getNumber("BLUE1 YP", 0);
        final double blue2YP = SmartDashboard.getNumber("BLUE2 YP", 0);

        final double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
        final double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

        final double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
        final double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

        final double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
        final double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);

        final double red1GP = SmartDashboard.getNumber("RED1 GP", 0);
        final double red2GP = SmartDashboard.getNumber("RED2 GP", 0);

        final double green1GP = SmartDashboard.getNumber("GREEN1 GP", 0);
        final double green2GP = SmartDashboard.getNumber("GREEN2 GP", 0);

        final double blue1GP = SmartDashboard.getNumber("BLUE1 GP", 0);
        final double blue2GP = SmartDashboard.getNumber("BLUE2 GP", 0);

        // Point redPoint = new Point(red1, red2);
        // Point greenPoint = new Point(green1, green2);
        // Point bluePoint = new Point(blue1, blue2);

        final Point yellowPoint1 = new Point(29, 234); // Point(0, 200);
        final Point yellowPoint2 = new Point(198, 236); // Point(160, 255);
        final Point yellowPoint3 = new Point(249, 255); // Point(200, 255);

        final Point redPoint1 = new Point(2, 23); // Point(34, 255);
        final Point redPoint2 = new Point(11, 255); // Point(70, 255);
        final Point redPoint3 = new Point(222, 255); // Point(200, 255);

        final Point greenPoint1 = new Point(43, 216);
        final Point greenPoint2 = new Point(38, 255);
        final Point greenPoint3 = new Point(226, 255);

        final Point greenPoint21 = new Point(38, 189);
        final Point greenPoint22 = new Point(201, 229);
        final Point greenPoint23 = new Point(95, 247);

        final Mat blurMat = Viscad2.Blur(orig, 4);
        final Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

        final Mat maskRedApple = Viscad2.Threshold(hsvImage, new Point(2, 23), new Point(11, 255), new Point(222, 255));
        final Mat maskGreenApple = Viscad2.Threshold(hsvImage, new Point(45, 200), new Point(160, 255),
                new Point(178, 255));
        final Mat maskYellowPear = Viscad2.Threshold(hsvImage, new Point(29, 234), new Point(198, 236),
                new Point(249, 255));
        final Mat maskGreenPear = Viscad2.Threshold(hsvImage, new Point(38, 189), new Point(201, 229),
                new Point(95, 247));

        final Mat erodeRedApple = Viscad2.Erode(maskRedApple, 1);
        final Mat erodeGreenApple = Viscad2.Erode(maskGreenApple, 1);
        final Mat erodeYellowPear = Viscad2.Erode(maskYellowPear, 1);
        final Mat erodeGreenPear = Viscad2.Erode(maskGreenPear, 3);

        final Mat dilateRedApple = Viscad2.Dilate(erodeRedApple, 1);
        final Mat dilateGreenApple = Viscad2.Dilate(erodeGreenApple, 4);
        final Mat dilateYellowPear = Viscad2.Dilate(erodeYellowPear, 1);
        final Mat dilateGreenPear = Viscad2.Dilate(erodeGreenPear, 2);

        final Mat fillHolesGreenPear = Viscad2.FillHolesCAD(dilateGreenPear);

        final int imageAreaRedApple = Viscad2.ImageTrueArea(dilateRedApple);
        final int imageAreaGreenApple = Viscad2.ImageTrueArea(dilateGreenApple);
        final int imageAreaYellowPear = Viscad2.ImageTrueArea(dilateYellowPear);
        final int imageAreaGreenPear = Viscad2.ImageTrueArea(fillHolesGreenPear);

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