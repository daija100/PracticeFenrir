// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.FeederSubsystem.FeederState;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  
  private TalonFX IntakeMotor;
  
  private final DoubleSolenoid IntakeSolenoid;
  
  public static boolean IntakeStatus;

  
  public static IntakeEnumState mIntakeEnumState;

  public IntakeSubsystem() {

  IntakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor);
 
  IntakeSolenoid =  new DoubleSolenoid(null, 
    Constants.IntakeConstants.kIntakeSolenoidForward, 
  Constants.IntakeConstants.kIntakeSolenoidReverse);

  mIntakeEnumState = IntakeEnumState.S_WaitingForBalls;
  IntakeStatus = false;
  }

  public enum IntakeEnumState{
    S_WaitingForBalls, S_FullBalls,
    S_Feeding
  }

    public void RunIntakeState(){
    switch (mIntakeEnumState) {
      case S_WaitingForBalls:
        WaitingForBalls();
        break;
      case S_FullBalls:
        FullBalls();
        break;      
      case S_Feeding:
        Feeding();
        break;
   }
  }

  private void WaitingForBalls(){
    if(FeederSubsystem.BallCount == 5){
      mIntakeEnumState = IntakeEnumState.S_FullBalls;
    } else{
      BallLogicStuff();
    }
  }

  private void FullBalls(){
    DeactivateIntake();
  }

  private void Feeding(){
    IntakeMotor.set(-0.3);
  }

  public static void ReturnIntakeState(){
  if(FeederSubsystem.BallCount >= 5){
    mIntakeEnumState = IntakeEnumState.S_FullBalls;
  } else{
    mIntakeEnumState = IntakeEnumState.S_WaitingForBalls;
  }
  }


  public void BallLogicStuff(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }    
  }
  
  //these two functions are in BallLogicStuff
  public void ActivateIntake(){
    IntakeMotor.set(0.3);
    IntakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void DeactivateIntake(){
    IntakeMotor.set(0);
    IntakeSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    RunIntakeState();
    SmartDashboard.getBoolean("IntakeActive", IntakeStatus);
    // This method will be called once per scheduler run
  }
}
