package frc.robot.MachineVision;

import java.util.ArrayList;
import java.util.Collections;
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
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;


public class JavaCam implements Runnable
{
    Training train = RobotContainer.train;
    private static UsbCamera camera;
    private CvSink cvSink;
    private static CvSource outStream, outBlur, outHSV, mask, oustream1, oustream2, 
    oustream3, mask2, mask3, cut2Out, redOut, greenOut, yellowOut, resizeGlide1, noWheels;

    public int nowTask = 1;
    
    public String colorCube, colorStand;
    public boolean alignCamera = false;
  
    @Override
    public void run() {

        SmartDashboard.putNumber("RED1 YP", 0.0);
        SmartDashboard.putNumber("RED2 YP", 0.0);
        SmartDashboard.putNumber("GREEN1 YP", 0.0);
        SmartDashboard.putNumber("GREEN2 YP", 0.0);
        SmartDashboard.putNumber("BLUE1 YP", 0.0);
        SmartDashboard.putNumber("BLUE2 YP", 0.0);

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
        // mask = CameraServer.getInstance().putVideo("mask", HEIGHT, HEIGHT);

        // oustream1 = CameraServer.getInstance().putVideo("outstream1", HEIGHT, HEIGHT);
        // oustream2 = CameraServer.getInstance().putVideo("outstream2", HEIGHT, HEIGHT);
        oustream3 = CameraServer.getInstance().putVideo("outstream3", 640, 480);

        mask2 = CameraServer.getInstance().putVideo("mask2", 640, 480);
        mask3 = CameraServer.getInstance().putVideo("mask3", 640, 480);

        redOut = CameraServer.getInstance().putVideo("redOut", 640, 480);
        greenOut = CameraServer.getInstance().putVideo("greenOut", 640, 480);
        yellowOut = CameraServer.getInstance().putVideo("yellowOut", 640, 480);
        noWheels = CameraServer.getInstance().putVideo("noWheels", 640, 480);
        resizeGlide1 = CameraServer.getInstance().putVideo("resizeGlide", 640, 480);

        while (true) {
            try {
                Mat source = new Mat();
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
                if(RobotContainer.train.nowTask == 228) {
                    RobotContainer.train.nowResult = CheckRotten(source);
                }
                if(RobotContainer.train.nowTask == 3) {
                    RobotContainer.train.nowResult = detectFruitInGripper(source);
                }

                source.release();
                
            } catch (final Exception e) {
                DriverStation.reportError("An error occurred in JavaCam: " + e.getMessage(), false);
                e.printStackTrace();
            }
        }
    }

    public static List<Point> getFruitPosition(Mat orig) {

        List<Rect> currentCordinate = new ArrayList<>();

        // double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
        // double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

        // double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
        // double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

        // double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
        // double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);

        Point greenPoint21 = new Point(0, 200);  
        Point greenPoint22 = new Point(0, 255);
        Point greenPoint23 = new Point(250, 255);

        Mat inImg = new Mat();

        // Пока не успел доделать
        if (RobotContainer.train.resizeForGlide) {
            inImg = Viscad2.ExtractImage(orig, new Rect(140, 0, 240, 440));  // Обрезаем картинку по линии стрелы
        } else {
            inImg = orig;
        }

        Mat blurMat = Viscad2.Blur(inImg, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

        Mat maskRedApple = thresholdAndProcess(hsvImage, greenPoint21, greenPoint22, greenPoint23, 1, 1);

        final Mat outPA = new Mat();

        currentCordinate = Viscad2.ParticleAnalysis(maskRedApple, outPA);
        
        resizeGlide1.putFrame(inImg);
        mask2.putFrame(maskRedApple);

        
        releaseMats(blurMat, hsvImage, maskRedApple, outPA, inImg, orig);
        // return new ArrayList<>();

        if (currentCordinate.isEmpty()) {

            SmartDashboard.putNumber("12121212212121", 1);
            return new ArrayList<>();
            
        } else {

            SmartDashboard.putNumber("12121212212121", 2);
            return processRectangles(inImg, currentCordinate);

        }   
        
    }

    public static List<Point> processRectangles(Mat image, List<Rect> currentCoordinate) {
        List<Point> centers = new ArrayList<>();
    
        if (currentCoordinate.isEmpty()) {
            return centers;
        } else {
            // Находим высоту изображения
            double imageHeight = image.rows();
            
            // Находим нижние точки всех прямоугольников и их расстояния до нижней границы изображения
            List<Double> distancesToBottom = new ArrayList<>();
            for (Rect rect : currentCoordinate) {
                double bottomY = rect.y + rect.height;
                double distance = imageHeight - bottomY;
                distancesToBottom.add(distance);
            }
            
            // Находим индекс ближайшего прямоугольника к нижней границе
            int nearestIndex = distancesToBottom.indexOf(Collections.min(distancesToBottom));
            Rect nearestRect = currentCoordinate.get(nearestIndex);
            
            int x = nearestRect.x;
            int y = nearestRect.y;
            int width = nearestRect.width;
            int height = nearestRect.height;
            
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
        // oustream3.putFrame(temp);
        // Сохраняем изображение с нарисованными прямоугольниками
        
        releaseMats(image);
        return centers;
    }

    public static int CheckFruit(final Mat orig) // Распознавание яблока
    {
        Training train = RobotContainer.train;
        // Нужно заменить массивом
        double red1YP = SmartDashboard.getNumber("RED1 YP", 0);
        double red2YP = SmartDashboard.getNumber("RED2 YP", 0);

        double green1YP = SmartDashboard.getNumber("GREEN1 YP", 0);
        double green2YP = SmartDashboard.getNumber("GREEN2 YP", 0);

        double blue1YP = SmartDashboard.getNumber("BLUE1 YP", 0);
        double blue2YP = SmartDashboard.getNumber("BLUE2 YP", 0);
        
        double red1GA = SmartDashboard.getNumber("RED1 RA", 0);
        double red2GA = SmartDashboard.getNumber("RED2 RA", 0);

        double green1GA = SmartDashboard.getNumber("GREEN1 RA", 0);
        double green2GA = SmartDashboard.getNumber("GREEN2 RA", 0);

        double blue1GA = SmartDashboard.getNumber("BLUE1 RA", 0);
        double blue2GA = SmartDashboard.getNumber("BLUE2 RA", 0);

        double red1GP = SmartDashboard.getNumber("RED1 GP", 0);
        double red2GP = SmartDashboard.getNumber("RED2 GP", 0);

        double green1GP = SmartDashboard.getNumber("GREEN1 GP", 0);
        double green2GP = SmartDashboard.getNumber("GREEN2 GP", 0);

        double blue1GP = SmartDashboard.getNumber("BLUE1 GP", 0);
        double blue2GP = SmartDashboard.getNumber("BLUE2 GP", 0);

        Point yellowPoint1 = new Point(30, 36);      // Point(0, 200);
        Point yellowPoint2 = new Point(100, 255);  // Point(160, 255);
        Point yellowPoint3 = new Point(140, 255);    // Point(200, 255);

        Point redPoint1 = new Point(0, 30);      // Point(34, 255);
        Point redPoint2 = new Point(100, 230);    // Point(70, 255);
        Point redPoint3 = new Point(170, 255);    // Point(200, 255);

        Point greenPoint1 = new Point(43, 70);
        Point greenPoint2 = new Point(50, 220);
        Point greenPoint3 = new Point(190, 255);

        Point greenPointPear1 = new Point(4, 40);
        Point greenPointPear2 = new Point(137, 251);
        Point greenPointPear3 = new Point(123, 255);

        // Point greenPoint21 = new Point(38, 189);  
        // Point greenPoint22 = new Point(201, 229);
        // Point greenPoint23 = new Point(95, 247);

        // Обрезаю изображение чтобы увеличить число кадров избавиться от лишнего шума
        Mat cut = Viscad2.ExtractImage(orig, new Rect(180, 150, 240, 200));

        // Обрезаю изображение для функции AutoBrightnessCAD
        Mat imgTemplate = Viscad2.ExtractImage(orig, new Rect(0, 0, 200, 180));

        // Это штука должна подгонять яркость то есть она всегда будет одинаковая
        Mat autoImage = Viscad2.AutoBrightnessCAD(imgTemplate, orig, 150, true);

        Mat blurMat = Viscad2.Blur(autoImage, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        
        Mat maskRedApple = thresholdAndProcess(hsvImage, redPoint1, redPoint2, redPoint3, 1, 1);
        Mat maskGreenApple = thresholdAndProcess(hsvImage, greenPoint1, greenPoint2, greenPoint3, 1, 1);
        Mat maskYellowPear = thresholdAndProcess(hsvImage, yellowPoint1, yellowPoint2, yellowPoint3, 1, 1);
        Mat maskGreenPear = thresholdAndProcess(hsvImage, greenPoint1, greenPoint2, greenPoint3, 3, 2);
        Mat maskAllWithoutWheels = thresholdAndProcess(hsvImage, greenPointPear1, greenPointPear2, greenPointPear3, 1, 1);
        Mat fillHolesGreenPear = Viscad2.FillHolesCAD(maskGreenPear);

        int imageAreaRedApple = Viscad2.ImageTrueArea(maskRedApple); 
        int imageAreaGreenApple = Viscad2.ImageTrueArea(maskGreenApple);
        int imageAreaYellowPear = Viscad2.ImageTrueArea(maskYellowPear);
        int imageAreaGreenPear = Viscad2.ImageTrueArea(fillHolesGreenPear);
        
        SmartDashboard.putNumber("ImageAreaRedApple", imageAreaRedApple);
        SmartDashboard.putNumber("ImageAreaGreenApple", imageAreaGreenApple);
        SmartDashboard.putNumber("ImageAreaYellowPear", imageAreaYellowPear);
        SmartDashboard.putNumber("ImageAreaGreenPear", imageAreaGreenPear);

        redOut.putFrame(maskRedApple);
        greenOut.putFrame(maskGreenApple);
        yellowOut.putFrame(maskYellowPear);
        noWheels.putFrame(autoImage);
        mask3.putFrame(blurMat);

        releaseMats(blurMat, hsvImage, orig, maskRedApple, maskGreenApple, maskYellowPear,
                        maskGreenPear, fillHolesGreenPear, cut, autoImage, imgTemplate, maskAllWithoutWheels);

        if(Function.BooleanInRange(imageAreaRedApple,       2000, 4000)) {  // SmallRed
            train.detectionResult = "SMALL RED APPLE";
            return 6;
        } 
        if(Function.BooleanInRange(imageAreaGreenApple,     2000, 3000)) { // SmallGreen
            train.detectionResult = "SMALL GREEN APPLE";
            return 7;
        }  
        if(Function.BooleanInRange(imageAreaRedApple,       1000, 30000)) { // BigRed
            train.detectionResult = "BIG RED APPLE";
            return 1;
        } 
        if(Function.BooleanInRange(imageAreaGreenPear,      10000, 20000)) { // GreenPear
            train.detectionResult = "GREEN PEAR";
            return 4;
        } 
        if(Function.BooleanInRange(imageAreaGreenApple,     15000, 24000)) { // BigGreen
            train.detectionResult = "BIG GREEN APPLE";
            return 2; 
        } 
        if(Function.BooleanInRange(imageAreaYellowPear,     5000, 20000)) { // YellowPear
            train.detectionResult = "YELLOW PEAR";
            return 3;
        } 
        return 0;
    }
    

    public static int CheckRotten(final Mat orig) {


        // double red1GP = SmartDashboard.getNumber("RED1 GP", 0);
        // double red2GP = SmartDashboard.getNumber("RED2 GP", 0);

        // double green1GP = SmartDashboard.getNumber("GREEN1 GP", 0);
        // double green2GP = SmartDashboard.getNumber("GREEN2 GP", 0);

        // double blue1GP = SmartDashboard.getNumber("BLUE1 GP", 0);
        // double blue2GP = SmartDashboard.getNumber("BLUE2 GP", 0);

        Point greenPointPear1 = new Point(145, 255);
        Point greenPointPear2 = new Point(78, 255);
        Point greenPointPear3 = new Point(123, 255);

        Mat cutRotten = Viscad2.ExtractImage(orig, new Rect(180, 150, 240, 200));
        Mat extractedRotten = Viscad2.ExtractImage(orig, new Rect(0, 0, 200, 180));
        Mat autoBrightMat = Viscad2.AutoBrightnessCAD(extractedRotten, cutRotten, 150, true);

        Mat blurMat = Viscad2.Blur(autoBrightMat, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        Mat rottenFruit = thresholdAndProcess(hsvImage, greenPointPear1, greenPointPear2, greenPointPear3, 1, 1);
        int imageAreaRottenFruit = Viscad2.ImageTrueArea(rottenFruit);

        SmartDashboard.putNumber("ImageAreaRotten", imageAreaRottenFruit);

        outBlur.putFrame(rottenFruit);
        releaseMats(cutRotten, extractedRotten, autoBrightMat, blurMat, hsvImage, rottenFruit);
        if(Function.BooleanInRange(imageAreaRottenFruit,    1000, 5000)) { 
            return 5; 
        }
        return 0;
    }

    private static Mat thresholdAndProcess(Mat hsvImage, Point lowerBound, Point upperBound, Point upperBound3, int erodeIterations, int dilateIterations) {
        Mat mask = Viscad2.Threshold(hsvImage, lowerBound, upperBound, upperBound3);
        Mat eroded = Viscad2.Erode(mask, erodeIterations);
        Mat dilated = Viscad2.Dilate(eroded, dilateIterations);

        eroded.release();
        mask.release();
        return dilated;
    }

    private static void releaseMats(Mat... mats) {
        for (Mat mat : mats) {
            mat.release();
        }
    }
    // Пока не доделал. Потом она будет выдавать строку о фрукте который в захвате
    private static int detectFruitInGripper(Mat orig) {
        double red1 = SmartDashboard.getNumber("RED1 YP", 0);
        double red2 = SmartDashboard.getNumber("RED2 YP", 0);

        double green1 = SmartDashboard.getNumber("GREEN1 YP", 0);
        double green2 = SmartDashboard.getNumber("GREEN2 YP", 0);

        double blue1 = SmartDashboard.getNumber("BLUE1 YP", 0);
        double blue2 = SmartDashboard.getNumber("BLUE2 YP", 0);
        
        Mat cutInGripper = Viscad2.ExtractImage(orig, new Rect(80, 180, 300, 300));
        
        Point greenPointPear1 = new Point(red1, red2);
        Point greenPointPear2 = new Point(green1, green2);
        Point greenPointPear3 = new Point(blue1, blue2);
        Mat hsvGrip = Viscad2.ConvertBGR2HSV(cutInGripper);
        Mat thresholdGrip = thresholdAndProcess(hsvGrip, greenPointPear1, greenPointPear2, greenPointPear3, 1, 1);
        noWheels.putFrame(thresholdGrip);
        releaseMats(hsvGrip, thresholdGrip, cutInGripper);
        return 1;
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
            camera.getProperty("brightness").set(7);
            // camera.getProperty("exposure_absolute").set(100);
        }
    }

}