// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */

  private TalonFX ShooterLeft;
  private TalonFX ShooterRight;

  private DoubleSolenoid ShooterHoodSolenoid;
  private DoubleSolenoid ShooterLatchSolenoid;

  public static double UsedVelo;

  public static double InputVelocity;

  public static ShooterHoodState mShooterHoodState;

  public static ShooterSpinState mShooterSpinState;

  public ShooterSubsystem() {
    ShooterLeft = new TalonFX(Constants.ShooterConstants.kShooterLeft);
    ShooterRight = new TalonFX(Constants.ShooterConstants.kShooterRight);

    ShooterHoodSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterHoodForward,
    Constants.ShooterConstants.kShooterHoodReverse);

    ShooterLatchSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterLatchForward,
    Constants.ShooterConstants.kShooterLatchReverse);

    mShooterHoodState = ShooterHoodState.S_HoodIn;
    mShooterSpinState = ShooterSpinState.S_Off;

    ShooterRight.setControl()
  }

  public enum ShooterHoodState{
    S_HoodOutFull, S_HoodWithLatch,
    S_HoodIn, S_Accelerating,
    S_Shooting
  }

  public enum ShooterSpinState{
    S_Off, S_Accelerating,
    S_Shooting
  }

  public void RunShooterHoodState(){
    switch (mShooterHoodState) {
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

  public void RunShooterSpinState(){
    switch (mShooterSpinState) {
      case S_Off:
        Off();
        break;
      case S_Accelerating:
        Accelerating();
        break;
      case S_Shooting:
        Shooting();
        break;
    }
  }

  public void HoodOutFull(){
    ShooterLatchSolenoid.set(DoubleSolenoid.Value.kForward);
    new WaitCommand(0.2);
    ShooterHoodSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void HoodWithLatch(){
    ShooterLatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    new WaitCommand(0.2);
    ShooterHoodSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

  public void HoodIn(){
    ShooterLatchSolenoid.set(DoubleSolenoid.Value.kForward);
    new WaitCommand(0.2);
    ShooterHoodSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void Off(){
    ShooterLeft.set(0);
  }

  public void Accelerating(){
    ShooterLeft.set(UsedVelo);
  }

  public void Shooting(){
    ShooterLeft.set(UsedVelo);
  }

  @Override
  public void periodic() {
    RunShooterHoodState();
    RunShooterSpinState();
    InputVelocity = SmartDashboard.getNumber("InputVelocity", 0);
    // This method will be called once per scheduler run
  }
}
