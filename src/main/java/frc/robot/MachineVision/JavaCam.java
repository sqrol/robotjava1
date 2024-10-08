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
import edu.wpi.first.wpilibj.Timer;
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
    private double countTimer = Timer.getFPGATimestamp();
    private int currentColor, lastColor = 0;
    private static int totalColors = 2; // Общее количество цветов
    private static boolean foundFlag = false;
  
    @Override
    public void run() {

        SmartDashboard.putNumber("RED1 YP", 0.0);
        SmartDashboard.putNumber("RED2 YP", 0.0);
        SmartDashboard.putNumber("GREEN1 YP", 0.0);
        SmartDashboard.putNumber("GREEN2 YP", 0.0);
        SmartDashboard.putNumber("BLUE1 YP", 0.0);
        SmartDashboard.putNumber("BLUE2 YP", 0.0);

        SmartDashboard.putNumber("RED1 RA", 0.0);
        SmartDashboard.putNumber("RED2 RA", 0.0);
        SmartDashboard.putNumber("GREEN1 RA", 0.0);
        SmartDashboard.putNumber("GREEN2 RA", 0.0);
        SmartDashboard.putNumber("BLUE1 RA", 0.0);
        SmartDashboard.putNumber("BLUE2 RA", 0.0);

        SmartDashboard.putNumber("RED1 GP", 0.0);
        SmartDashboard.putNumber("RED2 GP", 0.0);
        SmartDashboard.putNumber("GREEN1 GP", 0.0);
        SmartDashboard.putNumber("GREEN2 GP", 0.0);
        SmartDashboard.putNumber("BLUE1 GP", 0.0);
        SmartDashboard.putNumber("BLUE2 GP", 0.0);
        
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
                    RobotContainer.train.centersForClass = getFruitPosition(source, currentColor);
                    SmartDashboard.putNumber("currentColor: ", currentColor); 

                    if (!foundFlag) {
                        if (Timer.getFPGATimestamp() - countTimer > 1) {
                            train.detectionResult = "";
                            lastColor = (currentColor + 1) % totalColors; // Циклическое обновление цвета
                            countTimer = Timer.getFPGATimestamp(); // Сбрасываем таймер после обновления цвета
                        }
                    } else {
                        currentColor = lastColor;
                        if(currentColor == 0) {
                            train.detectionResult = "AppleBigRipe";
                            foundFlag = false;
                        }
                        if(currentColor == 1) {
                            train.detectionResult = "PeerRipe";
                            foundFlag = false;
                        }
                    }
                    
                }
                if(RobotContainer.train.nowTask == 4) {
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

    // public static List<Point> getFruitPosition(Mat orig) {

    //     List<Rect> currentCordinate = new ArrayList<>();

    //     double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
    //     double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

    //     double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
    //     double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

    //     double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
    //     double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);

    //     Point greenPoint21 = new Point(0, 255);   
    //     Point greenPoint22 = new Point(122, 255);
    //     Point greenPoint23 = new Point(127, 255);

    //     Point redPoint1 = new Point(0, 45);   
    //     Point redPoint2 = new Point(207, 255);
    //     Point redPoint3 = new Point(254, 255);

    //     Mat inImg = new Mat();
    //     Mat outPA = new Mat();

    //     // Пока не успел доделать
    //     if (RobotContainer.train.resizeForGlide) {
    //         inImg = Viscad2.ExtractImage(orig, new Rect(140, 80, 240, 360));  // Обрезаем картинку по линии стрелы
    //     } else {
    //         inImg = orig;
    //     }

    //     SmartDashboard.putBoolean("findNotEmptyMat()", findNotEmptyMat(orig).empty());

    //     if(!(findNotEmptyMat(orig) == null)) {
    //         currentCordinate = Viscad2.ParticleAnalysis(findNotEmptyMat(orig), outPA);
    //     }

    //     resizeGlide1.putFrame(inImg);

    //     releaseMats(outPA, inImg, orig);

    //     if (currentCordinate.isEmpty()) {

    //         SmartDashboard.putNumber("12121212212121", 1);
    //         return new ArrayList<>();
            
    //     } else {

    //         SmartDashboard.putNumber("12121212212121", 2);
    //         return processRectangles(inImg, currentCordinate);

    //     }   
    // }

    // public static Mat findNotEmptyMat(final Mat orig) {

    //     ArrayList<Mat> mats = new ArrayList<>();

    //     double startTimer = Timer.getFPGATimestamp();

    //     // double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
    //     // double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

    //     // double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
    //     // double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

    //     // double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
    //     // double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);

    //     Point greenPoint21 = new Point(0, 255);   
    //     Point greenPoint22 = new Point(122, 255);
    //     Point greenPoint23 = new Point(127, 255);

    //     Point redPoint1 = new Point(0, 45);   
    //     Point redPoint2 = new Point(207, 255);
    //     Point redPoint3 = new Point(254, 255);

    //     Mat blurMat = Viscad2.Blur(orig, 4);
    //     Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

    //     Mat maskRedApple = thresholdAndProcess(hsvImage, greenPoint21, greenPoint22, greenPoint23, 1, 1);
    //     Mat fillHolesRedApple = Viscad2.FillHolesCAD(maskRedApple);
    //     Mat yellowPear = thresholdAndProcess(hsvImage, redPoint1, redPoint2, redPoint3, 1, 1);

    //     final Mat outPA = new Mat();

    //     int outArea = Viscad2.ImageTrueArea(maskRedApple); 

    //     mats.add(fillHolesRedApple);
    //     mats.add(yellowPear);

    //     releaseMats(blurMat, hsvImage, maskRedApple, outPA, fillHolesRedApple, yellowPear);

    //     Mat empty = new Mat();
        
    //     for (int i = 0; i < mats.size(); i++) {
    //         double localTimer = Timer.getFPGATimestamp();
    //         if(Viscad2.ImageTrueArea(mats.get(i)) > 100) {
    //             if(localTimer - startTimer > 1) {
    //                 localTimer = 0;
    //                 return mats.get(i);
    //             }
    //         }
    //     }
    //     return empty;
    // }

    public static List<Point> getFruitPosition(Mat orig, int currentColor) {

        List<Rect> currentCordinate = new ArrayList<>();
        Point[] outPoints = new Point[3];

        double red1RA = SmartDashboard.getNumber("RED1 YP", 0);
        double red2RA = SmartDashboard.getNumber("RED2 YP", 0);

        double green1RA = SmartDashboard.getNumber("GREEN1 YP", 0);
        double green2RA = SmartDashboard.getNumber("GREEN2 YP", 0);

        double blue1RA = SmartDashboard.getNumber("BLUE1 YP", 0);
        double blue2RA = SmartDashboard.getNumber("BLUE2 YP", 0);

        Point greenPoint1 = new Point(0, 0);   
        Point greenPoint2 = new Point(0, 0);
        Point greenPoint3 = new Point(0, 0);

        Point yellowPoint1 = new Point(25, 45);   
        Point yellowPoint2 = new Point(78, 241);
        Point yellowPoint3 = new Point(123, 222);

        Point redPoint1 = new Point(0, 16);   
        Point redPoint2 = new Point(0, 255);
        Point redPoint3 = new Point(145, 255);

        // Point[] redPoints = {redPoint1, redPoint2, redPoint3};
        // Point[] yellowPoints = {yellowPoint1, yellowPoint2, yellowPoint3};

        Point[][] pointsArray = {
            // {greenPoint1, greenPoint2, greenPoint3},
            {redPoint1, redPoint2, redPoint3},
            {yellowPoint1, yellowPoint2, yellowPoint3}
        };

        if (currentColor >= 0 && currentColor < pointsArray.length) {
            outPoints = pointsArray[currentColor];
            foundFlag = true;
        } else {
            // outPoints = outPoints; 
        }

        

        Mat inImg = new Mat();
        Mat outPA = new Mat();

        // Пока не успел доделать
        if (RobotContainer.train.resizeForGlide) { inImg = Viscad2.ExtractImage(orig, new Rect(140, 80, 240, 360)); } // Обрезаем картинку по линии стрелы
        else { inImg = orig; }

        Mat blurMat = Viscad2.Blur(inImg, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

        Mat maskRedApple = thresholdAndProcess(hsvImage, outPoints[0], outPoints[1], outPoints[2], 1, 1);
        oustream3.putFrame(maskRedApple);
        
        outPA = new Mat();

        currentCordinate = Viscad2.ParticleAnalysis(maskRedApple, outPA);

        currentColor++;

        releaseMats(blurMat, hsvImage, maskRedApple, outPA, inImg, orig);

        if (currentCordinate.isEmpty()) {
            foundFlag = false;
            currentColor = (currentColor + 1) % totalColors;
            return new ArrayList<>();
            
        } else {
            return processRectangles(inImg, currentCordinate);

        }   
        
    }

    public static Mat findNotEmptyMat(final Mat orig) {

        ArrayList<Mat> mats = new ArrayList<>();

        Point[] outPoints = new Point[3]; 

        int currentFruit = 0; 

        // double red1RA = SmartDashboard.getNumber("RED1 RA", 0);
        // double red2RA = SmartDashboard.getNumber("RED2 RA", 0);

        // double green1RA = SmartDashboard.getNumber("GREEN1 RA", 0);
        // double green2RA = SmartDashboard.getNumber("GREEN2 RA", 0);

        // double blue1RA = SmartDashboard.getNumber("BLUE1 RA", 0);
        // double blue2RA = SmartDashboard.getNumber("BLUE2 RA", 0);

        Point greenPoint21 = new Point(0, 255);   
        Point greenPoint22 = new Point(122, 255);
        Point greenPoint23 = new Point(127, 255);

        Point redPoint1 = new Point(0, 45);   
        Point redPoint2 = new Point(207, 255);
        Point redPoint3 = new Point(254, 255);

        Point[] greenPoints = {greenPoint21, greenPoint22, greenPoint23};
        Point[] redPoints = {redPoint1, redPoint2, redPoint3};

        if (currentFruit == 1) {
            outPoints = greenPoints; 
        }

        if (currentFruit == 2) {
            outPoints = redPoints; 
        }

        Mat blurMat = Viscad2.Blur(orig, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);

        Mat mask = thresholdAndProcess(hsvImage, outPoints[0], outPoints[1], outPoints[2], 1, 1);
        Mat fillHoles = Viscad2.FillHolesCAD(mask);

        int outArea = Viscad2.ImageTrueArea(fillHoles); 

        releaseMats(blurMat, hsvImage, mask, fillHoles);
        Mat empty = new Mat();

        if (!fillHoles.empty()) {
            empty = fillHoles; 
        }
        
        return empty;
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
        // oustream3.putFrame(image);
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

        Point yellowPoint1 = new Point(37, 45);      //30, 36);  
        Point yellowPoint2 = new Point(52, 222);   // 100, 255);
        Point yellowPoint3 = new Point(245, 255);    //140, 255);

        Point redPoint1 = new Point(0, 45);      // Point(34, 255);
        Point redPoint2 = new Point(207, 255);    // Point(70, 255);
        Point redPoint3 = new Point(254, 255);    // Point(200, 255);

        Point greenPoint1 = new Point(1, 255);    //  43, 70);
        Point greenPoint2  = new Point(123, 255);    // 50, 220)
        Point greenPoint3 = new Point(254, 255);  // 190, 255

        Point greenPointPear1 = new Point(red1YP, red2YP);
        Point greenPointPear2 = new Point(green1YP, green2YP);
        Point greenPointPear3 = new Point(blue1YP, blue2YP);

        Point greenPoint21 = new Point(red1YP, red2YP); //(38, 189);   44 48 
        Point greenPoint22 = new Point(green1YP, green2YP); //(201, 229); 34 255
        Point greenPoint23 = new Point(blue1YP, blue2YP); //(95, 247); 45 255

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
        Mat maskGreenPear = thresholdAndProcess(hsvImage, greenPoint21, greenPoint22, greenPoint23, 3, 2);
        Mat maskAllWithoutWheels = thresholdAndProcess(hsvImage, greenPointPear1, greenPointPear2, greenPointPear3, 1, 1);
        Mat fillHolesGreenPear = Viscad2.FillHolesCAD(maskGreenPear);
        Mat filledRedApple = Viscad2.FillHolesCAD(maskRedApple);
        int imageAreaRedApple = Viscad2.ImageTrueArea(maskRedApple); 
        int imageAreaGreenApple = Viscad2.ImageTrueArea(maskGreenApple);
        int imageAreaYellowPear = Viscad2.ImageTrueArea(maskYellowPear);
        int imageAreaGreenPear = Viscad2.ImageTrueArea(fillHolesGreenPear);
        
        SmartDashboard.putNumber("ImageAreaRedApple", imageAreaRedApple);
        SmartDashboard.putNumber("ImageAreaGreenApple", imageAreaGreenApple);
        SmartDashboard.putNumber("ImageAreaYellowPear", imageAreaYellowPear);
        SmartDashboard.putNumber("ImageAreaGreenPear", imageAreaGreenPear);

        redOut.putFrame(filledRedApple);
        greenOut.putFrame(maskGreenApple);
        yellowOut.putFrame(maskYellowPear);
        noWheels.putFrame(autoImage);
        mask3.putFrame(blurMat);

        releaseMats(blurMat, hsvImage, orig, maskRedApple, maskGreenApple, maskYellowPear,
                        maskGreenPear, fillHolesGreenPear, cut, autoImage, imgTemplate, maskAllWithoutWheels);

        if(Function.BooleanInRange(imageAreaRedApple,       100, 3000)) {  // SmallRed
            train.detectionResult = "AppleSmallRipe";
            train.fruitFind = true; 
            return 6;
        } 
        if(Function.BooleanInRange(imageAreaGreenApple,     100, 10000)) { // SmallGreen
            train.detectionResult = "SMALL GREEN APPLE";
            train.fruitFind = true; 
            return 7;
        }  
        if(imageAreaRedApple > 10000) { // BigRed
            train.detectionResult = "AppleBigRipe";
            train.fruitFind = true; 
            return 1;
        } 
        if(imageAreaGreenPear > 8000) { // GreenPear
            train.detectionResult = "GREEN PEAR";
            train.fruitFind = true; 
            return 4;
        } 
        if(imageAreaGreenApple > 13000) { // BigGreen
            train.detectionResult = "BIG GREEN APPLE";
            train.fruitFind = true; 
            return 2; 
        } 
        if(imageAreaYellowPear > 15000) { // YellowPear
            train.detectionResult = "PeerRipe";
            train.fruitFind = true; 
            return 3;
        } 
        return 0;
    }
    

    public static int CheckRotten(final Mat orig) {


        double red1GP = SmartDashboard.getNumber("RED1 GP", 0);
        double red2GP = SmartDashboard.getNumber("RED2 GP", 0);

        double green1GP = SmartDashboard.getNumber("GREEN1 GP", 0);
        double green2GP = SmartDashboard.getNumber("GREEN2 GP", 0);

        double blue1GP = SmartDashboard.getNumber("BLUE1 GP", 0);
        double blue2GP = SmartDashboard.getNumber("BLUE2 GP", 0);

        Point greenPointPear1 = new Point(123, 255);
        Point greenPointPear2 = new Point(67, 255);
        Point greenPointPear3 = new Point(123, 255);

        // Mat cutRotten = Viscad2.ExtractImage(orig, new Rect(180, 150, 240, 200));
        Mat extractedRotten = Viscad2.ExtractImage(orig, new Rect(0, 0, 200, 180));
        Mat autoBrightMat = Viscad2.AutoBrightnessCAD(extractedRotten, orig, 150, true);

        Mat blurMat = Viscad2.Blur(autoBrightMat, 4);
        Mat hsvImage = Viscad2.ConvertBGR2HSV(blurMat);
        Mat rottenFruit = thresholdAndProcess(hsvImage, greenPointPear1, greenPointPear2, greenPointPear3, 1, 1);
        int imageAreaRottenFruit = Viscad2.ImageTrueArea(rottenFruit);

        SmartDashboard.putNumber("ImageAreaRotten", imageAreaRottenFruit);

        outBlur.putFrame(rottenFruit);
        releaseMats(extractedRotten, autoBrightMat, blurMat, hsvImage, rottenFruit);
        if(Function.BooleanInRange(imageAreaRottenFruit,    1000, 7000)) { 
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