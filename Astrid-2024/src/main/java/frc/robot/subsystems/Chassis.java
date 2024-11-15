// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class Chassis extends SubsystemBase {
  //Creates all of the motors that are used for the wheels
  private TalonFX DriveFrontLeft;
  private TalonFX DriveFrontRight;
  private TalonFX DriveBackLeft;
  private TalonFX DriveBackRight;
  private DifferentialDrive drive;

  /** Creates a new Chassis. */
  public Chassis() {
    DriveFrontLeft = new TalonFX(Constants.ChassisConstants.kDriveFrontLeft);
    DriveFrontRight = new TalonFX(Constants.ChassisConstants.kDriveFrontRight);
    DriveBackLeft = new TalonFX(Constants.ChassisConstants.kDriveBackLeft);
    DriveBackRight = new TalonFX(Constants.ChassisConstants.kDriveBackRight);
    DifferentialDrive drive = new DifferentialDrive(DriveBackLeft, DriveBackRight);

    /*
    Used to make the back wheels follow their corresponding front wheels
    */
    DriveFrontLeft.setControl(
      new Follower(DriveBackLeft.getDeviceID(), Constants.kDontOpposeMasterDirection));

    DriveFrontRight.setControl(
      new Follower(DriveBackRight.getDeviceID(), Constants.kDontOpposeMasterDirection));

        /*
         * 
         * PLEASE
         * CHECK
         * WHEN
         * RUNNING
         * 
         */
        DriveBackLeft.setInverted(true);
        DriveBackRight.setInverted(false);

  }
 

    // Arcade Drive method
public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation);
    }
public void turnArcade(double turnSpeed) {
        // Set the speed for both sides of the drivetrain
        drive.arcadeDrive(0.0, turnSpeed);
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
