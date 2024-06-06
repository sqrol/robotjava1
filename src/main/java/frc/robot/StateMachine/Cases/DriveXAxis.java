// package frc.robot.StateMachine.Cases;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.RobotContainer;
// import frc.robot.functions.Function;

// import frc.robot.StateMachine.*;
// import frc.robot.subsystems.Training;

// // Новый Case для езды

// public class DriveXAxis implements IState {

//     public Training train = RobotContainer.train;

//     private double XPosition, ZPosition = 0; 
//     private boolean finishX, finishZ = false;
//     private double currentAxisX, outSpeedForX, outSpeedForZ  = 0; 

//     private boolean isFirst = true;

//     // private double[][] speedXArray = { { 0, 5, 10, 12, 14, 18, 35, 50, 100}, 
//     //                                    { 0, 12, 15, 25, 35, 50, 75, 85, 95} };

//     private double[][] speedXArray = { { 0, 1, 2.5, 5, 10, 12, 14, 18, 35, 50, 100}, 
//                                         { 0, 7, 10, 12, 18, 25, 35, 50, 75, 85, 95} };

//     private double[][] speedZArray = { { 0.1, 0.5, 1.5, 2, 3, 6, 12, 26, 32, 50 }, 
//                                        { 6, 11, 17, 20, 25, 30, 40, 53, 60, 70 } };

//     private double[][] speedZArrayJustTurn = { { 0, 1, 5, 8, 10, 26, 41, 60, 90 }, 
//                                                  { 0, 2, 5, 10, 15, 20, 35, 45, 70 } };

//     private double[][] startKoefSpeedForX = { { 0, 333, 666,  1000 }, { 0, 0.33, 0.66, 1 } };

//     public DriveXAxis(double XPosition, double ZPosition){
//         this.XPosition = XPosition;
//         this.ZPosition = ZPosition;
//         this.isFirst = true;
//         allReset();
//     }

//     @Override
//     public boolean execute() {

//         if (isFirst) {
//             allReset();
//             isFirst = false;
//         }

//         double startKoef = Function.TransitionFunction(System.currentTimeMillis() - StateMachine.iterationTime, startKoefSpeedForX);
//         double gyro = train.getLongYaw(); 

//         if (XPosition != 0) {
            
//             double currentRight = -train.getEncRightThread();
//             double currentLeft = -train.getEncLeftThread();
    
//             currentAxisX = ((currentRight + currentLeft) / 2) / 48;
    
//             double[] polar = Function.ReImToPolar(currentAxisX, 0);
//             double[] decard = Function.PolarToReIm(polar[0], (polar[1] + Math.toRadians(gyro))); 
    
//             double diffX = XPosition - decard[0];
            
//             outSpeedForX = Function.TransitionFunction(diffX, speedXArray);
//             outSpeedForZ = Function.TransitionFunction(-gyro, speedZArray);

//             finishX = Function.BooleanInRange(outSpeedForX, -2, 2); 
//             finishZ = Function.BooleanInRange(outSpeedForZ, -0.2, 0.2); 
    
//         } else {
//             outSpeedForX = 0;
//             SmartDashboard.putNumber("diffZ", ZPosition - gyro);
//             outSpeedForZ = Function.TransitionFunction(ZPosition - gyro, speedZArrayJustTurn);
//             finishX = true;
//             finishZ = Function.BooleanInRange(outSpeedForZ, -0.3, 0.3); 
//         }

//         train.setAxisSpeed(outSpeedForX * startKoef, outSpeedForZ);

//         SmartDashboard.putNumber("posX", currentAxisX);
//         SmartDashboard.putNumber("posZ", outSpeedForZ);
//         return finishX && finishZ;
//     }

//     private void allReset() {
//         train.resetEncRight();
//         train.resetEncLeft();
//         train.resetGyro();
//     }

// }
package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;

import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

// Новый Case для езды

public class DriveXAxis implements IState {

    public Training train = RobotContainer.train;

    private double XPosition, ZPosition = 0; 
    private boolean finishX, finishZ = false;
    private double outSpeedForX, outSpeedForZ  = 0; 
    public double currentAxisX = 0;
    private double lastRightEnc = 0; 
    private double lastLeftEnc = 0; 

    private double lastGyro = 0; 

    private boolean isReset = allReset();

    private boolean isFirst = true;

    // private double[][] speedXArray = { { 0, 0.7, 1.4, 3, 5, 10, 12, 14, 18, 35, 50, 100}, 
    //                                    { 0, 1, 2, 3, 4, 12, 25, 35, 50, 75, 85, 95} };

    private double[][] speedXArray = { { 0, 0.7, 1.4, 3, 5, 10, 12, 14, 18, 35, 50, 100}, 
                                       { 0, 1, 2, 3, 8, 15, 30, 45, 50, 65, 75, 95} };

    // private double[][] speedXArray = { { 0, 1, 2.5, 5, 10, 12, 14, 18, 35, 50, 100}, 
    //                                     { 0, 7, 10, 12, 18, 25, 35, 50, 75, 85, 95} };

    // private double[][] speedZArray = { { 0.1, 0.5, 1.5, 2, 3, 6, 12, 26, 32, 50 }, 
    //                                    { 6, 11, 17, 20, 25, 30, 40, 53, 60, 70 } };

   private double[][] speedZArray = { { 0.1, 0.5, 1.5, 2, 3, 6, 12, 26, 32, 50 }, 
                                       { 10, 15, 17, 20, 25, 30, 40, 53, 60, 70 } };

    private double[][] speedZArrayJustTurn = { { 0, 1, 5, 8, 10, 26, 41, 60, 90 }, 
                                                 { 0, 2, 5, 10, 15, 20, 35, 45, 70 } };

    // Sage
    // private double[][] speedXArray = { { 0, 2, 12, 35, 52, 67, 90 }, 
    //                                     { 0, 4, 15, , 45, 59, 76, 81, 98 } };
    

    private double[][] startKoefSpeedForX = { { 1 }, { 1 } };

    public DriveXAxis(double XPosition, double ZPosition){
        this.XPosition = XPosition;
        this.ZPosition = ZPosition;
        this.isFirst = true;
        currentAxisX = 0;
        // allReset();
    }

    @Override
    public boolean execute() {

        if (isReset) {
            if (isFirst) {

                
    
                train.resetEncRight();
                train.resetEncLeft();
                currentAxisX = 0;
                train.resetGyroValue = 0;
                train.resetGyroThreadOnce = true;
    
                isFirst = false;
    
            }
            isReset = false;
        }
        
        

        double startKoef = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.iterationTime, startKoefSpeedForX);
        double gyro = train.newGyroThread; 

        if (XPosition != 0 && !isFirst) {
            SmartDashboard.putNumber("isFirstCheck", 1111);
            SmartDashboard.putNumber("lastRightEnc", lastRightEnc); 
            SmartDashboard.putNumber("lastLeftEnc", lastLeftEnc); 
            
            double currentRight = train.getEncRightThread();
            double currentLeft = train.getEncLeftThread();

            SmartDashboard.putNumber("currentRight", currentRight); 
            SmartDashboard.putNumber("currentLeft", currentLeft); 
    
            currentAxisX = -((currentRight + currentLeft) / 2) / 48;
    
            double[] polar = Function.ReImToPolar(currentAxisX, 0);
            double[] decard = Function.PolarToReIm(polar[0], (polar[1] + Math.toRadians(gyro))); 
    
            double diffX = XPosition - decard[0];
            
            outSpeedForX = Function.TransitionFunction(diffX, speedXArray);
            outSpeedForZ = Function.TransitionFunction(-gyro, speedZArray);

            finishX = Function.BooleanInRange(diffX, -0.5, 0.5); 
            finishZ = Function.BooleanInRange(outSpeedForZ, -0.2, 0.2); 
    
        } else {
            SmartDashboard.putNumber("isFirstCheck", 2222);
            outSpeedForX = 0;
            
            // SmartDashboard.putNumber("diffZ", ZPosition - gyro);
            outSpeedForZ = Function.TransitionFunction(ZPosition - gyro, speedZArrayJustTurn);
            
            finishX = true;
            finishZ = Function.BooleanInRange(outSpeedForZ, -0.3, 0.3); 
        }

        train.setAxisSpeed(outSpeedForX * startKoef, outSpeedForZ);

        SmartDashboard.putNumber("posX", currentAxisX);
        SmartDashboard.putNumber("posZ", outSpeedForZ);
        if(finishX && finishZ && !isFirst) {

            train.resetGyroValue = 0;
            train.resetGyroThreadOnce = true;
            currentAxisX = 0;
            return true;
        }
        return false;
    }



    private boolean allReset() {
        train.resetEncRight();
        train.resetEncLeft();
        train.resetGyro();
        currentAxisX = 0;
        return Timer.getFPGATimestamp() - StateMachine.iterationTime > 0.5; 
    }

}