// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {
  private TalonFX DriveFrontLeft;
  private TalonFX DriveFrontRight;
  private TalonFX DriveBackLeft;
  private TalonFX DriveBackRight;
  private DifferentialDrive drive;
  private Notifier notifier;
  private NetworkTable mTable;

  private static final NetworkTable mTable = NetworkTableInstance.getDefault().getTable("limelight");

  private static final double kTicksPerRotation = 2048;
  private static final double kHundredMillisPerSecond = 10;
  private static final double kSecondsPerMin = 60;

  private static final int kMotionAcceleration = 1500;
  private static final int kMotionCruiseVelocity = 1500;


  private static double feetToTicks(double feet) {
      return feet * 13000;
    }

    private static final double kMaxVelocityError = 3500 - 3000;
  

  /** Creates a new Chassis. */
  public Chassis() {
    DriveFrontLeft = new TalonFX(Constants.ChassisConstants.kDriveFrontLeft);
    DriveFrontRight = new TalonFX(Constants.ChassisConstants.kDriveFrontRight);
    DriveBackLeft = new TalonFX(Constants.ChassisConstants.kDriveBackLeft);
    DriveBackRight = new TalonFX(Constants.ChassisConstants.kDriveBackRight);
    drive = new DifferentialDrive(backLeft, backRight);
    mTable = NetworkTableInstance.getDefault().getTable("limelight");
      // Initialize notifier for periodic tasks
    notifier = new Notifier(this::periodicTask);
   

    DriveFrontLeft.setControl(
        new Follower(DriveBackLeft.getDeviceID(), Constants.kDontOpposeMasterDirection));

          DriveFrontRight.setControl(
     new Follower(DriveBackRight.getDeviceID(), Constants.kDontOpposeMasterDirection));

        DriveBackLeft.setInverted(true);
        DriveBackRight.setInverted(false);

        DriveFrontLeft.setSensorPhase(false);
        DriveBackLeft.setSensorPhase(true);

    


  
  }
 
public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }
      // Turn method for tank drive
public void turn(double turnSpeed) {
        // Set the speed of the left and right sides of the drivetrain
        drive.tankDrive(turnSpeed, -turnSpeed);
    }

    // Arcade Drive method
public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation);
    }
public void turnArcade(double turnSpeed) {
        // Set the speed for both sides of the drivetrain
        drive.arcadeDrive(0.0, turnSpeed);
    }

 private void configureMotionProfile(WPI_TalonFX motor) {
        motor.configMotionAcceleration(kMotionAcceleration);
        motor.configMotionCruiseVelocity(kMotionCruiseVelocity);
    }
 
private void periodicTask() {
        // Process motion profile buffer or any other periodic tasks
    }

    public static class Components {
        public TalonFX DriveFrontLeft;
        public TalonFX DriveFrontRight;
        public TalonFX DriveBackLeft;
        public TalonFX DriveBackRight;
    }


  @Override
  public void periodic() {
   
    // This method will be called once per scheduler run
  }
}
