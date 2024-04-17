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

  /** Creates a new Chassis. */
  public Chassis() {
    DriveFrontLeft = new TalonFX(Constants.ChassisConstants.kDriveFrontLeft);
    DriveFrontRight = new TalonFX(Constants.ChassisConstants.kDriveFrontRight);
    DriveBackLeft = new TalonFX(Constants.ChassisConstants.kDriveBackLeft);
    DriveBackRight = new TalonFX(Constants.ChassisConstants.kDriveBackRight);

    DriveFrontLeft.setControl(
        new Follower(DriveBackLeft.getDeviceID(), Constants.kDontOpposeMasterDirection));

          DriveFrontRight.setControl(
     new Follower(DriveBackRight.getDeviceID(), Constants.kDontOpposeMasterDirection));

  }

  @Override
  public void periodic() {
   
    // This method will be called once per scheduler run
  }
}
