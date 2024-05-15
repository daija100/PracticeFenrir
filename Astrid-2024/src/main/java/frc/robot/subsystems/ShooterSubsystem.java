// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Set;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.Velocity;
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

  private boolean SetpointMet = false;
  private boolean HasPassedSetpoint = false;
  private boolean Ready = false;

  private int counter;

  public static double UsedVelo;

  public static double InputVelocity;

  public static ShooterHoodState mShooterHoodState;

  public static ShooterSpinState mShooterSpinState;

  public ShooterSubsystem() {
    var Slot0Configs = new Slot0Configs();
    Slot0Configs.kV  = Constants.ShooterConstants.kV;
    Slot0Configs.kP  = Constants.ShooterConstants.kP;
    Slot0Configs.kI  = Constants.ShooterConstants.kI;
    Slot0Configs.kD  = Constants.ShooterConstants.kD;

    ShooterLeft = new TalonFX(Constants.ShooterConstants.kShooterLeft);
    ShooterRight = new TalonFX(Constants.ShooterConstants.kShooterRight);

    counter = 0;

    ShooterHoodSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterHoodForward,
    Constants.ShooterConstants.kShooterHoodReverse);

    ShooterLatchSolenoid = new DoubleSolenoid(null,
     Constants.ShooterConstants.kShooterLatchForward,
    Constants.ShooterConstants.kShooterLatchReverse);

    mShooterHoodState = ShooterHoodState.S_HoodIn;
    mShooterSpinState = ShooterSpinState.S_Off;

    ShooterRight.setControl(new Follower(ShooterLeft.getDeviceID(), true));
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
    if(Ready){}
    VelocityVoltage ShootSpeed = new VelocityVoltage(UsedVelo).withSlot(0);

    ShooterLeft.setControl(ShootSpeed);
  }

  public void Shooting(){
    VelocityVoltage ShootSpeed = new VelocityVoltage(UsedVelo).withSlot(0);

    ShooterLeft.setControl(ShootSpeed);
  }

  private void CheckShooterReady(){
    if (CheckShooterUp2Sped()){
      SetpointMet = true;
    } else if(SetpointMet && !CheckShooterUp2Sped()){
      HasPassedSetpoint = true;
    }

    if(SetpointMet && HasPassedSetpoint){
      counter = counter + 1;
      SetpointMet = false;
      HasPassedSetpoint = false;
    }
    if(counter >= 2){
      Ready = true;
    }
  }

  private boolean CheckShooterUp2Sped(){
    if(RealMotorSpeed() <= UsedVelo + 0.003 && RealMotorSpeed() >= UsedVelo - 0.003){
      return true;
    }
    else {
      return false;
    }
  }

  private double RealMotorSpeed(){
    return ShooterLeft.getVelocity().getValueAsDouble();
  }

  @Override
  public void periodic() {
    RunShooterHoodState();
    RunShooterSpinState();
    InputVelocity = SmartDashboard.getNumber("InputVelocity", 0);
    // This method will be called once per scheduler run
  }
}
