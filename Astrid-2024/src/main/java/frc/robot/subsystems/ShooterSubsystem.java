// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */

  private TalonFX ShooterLeft;
  private TalonFX ShooterRight;

  private DoubleSolenoid ShooterHoodSolenoid;
  private DoubleSolenoid ShooterLatchSolenoid;

  public static ShooterState mShooterState;

  public ShooterSubsystem() {
    ShooterLeft = new TalonFX(Constants.ShooterConstants.kShooterLeft);
    ShooterRight = new TalonFX(Constants.ShooterConstants.kShooterRight);

    ShooterHoodSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterHoodForward,
    Constants.ShooterConstants.kShooterHoodReverse);

    ShooterLatchSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterLatchForward,
    Constants.ShooterConstants.kShooterLatchReverse);

    mShooterState = ShooterState.S_HoodIn;

  }

  private enum ShooterState{
    S_HoodOutFull, S_HoodWithLatch,
    S_HoodIn
  }

  public void RunShooterState(){
    switch (mShooterState) {
      case S_HoodOutFull:
        HoodOutFull();
        break;
      case S_HoodWithLatch:
        HoodWithLatch();
        break;
      case S_HoodIn:
        HoodIn();
        break;
    }
  }

  public void HoodOutFull(){}

  public void HoodWithLatch(){}

  public void HoodIn(){}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
