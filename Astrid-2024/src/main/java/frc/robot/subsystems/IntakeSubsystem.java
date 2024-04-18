// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  
  private TalonFX IntakeMotor;
  
  private final DoubleSolenoid IntakeSolenoid;
  
  public boolean IntakeStatus;
  private int BallCount;
  
  public static IntakeEnumState mIntakeEnumState;

  public IntakeSubsystem() {

  IntakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor);
 
  IntakeSolenoid =  new DoubleSolenoid(null, 
    Constants.IntakeConstants.kIntakeSolenoidForward, 
  Constants.IntakeConstants.kIntakeSolenoidReverse);

  mIntakeEnumState = IntakeEnumState.S_Has0Balls;
  IntakeStatus = true;
  BallCount = 0;
  }

  public enum IntakeEnumState{
    S_Has0Balls, S_Has1Ball,
    S_Has2Balls, S_Has3Balls,
    S_Has4Balls, S_Has5Balls
  }

    public void RunIntakeState(){
    switch (mIntakeEnumState) {
      case S_Has0Balls:
        Has0Balls();
        break;
      case S_Has1Ball:
        Has1Ball();
        break;      
      case S_Has2Balls:
        Has2Balls();
        break;
      case S_Has3Balls:
        Has3Balls();
        break;
      case S_Has4Balls:
        Has4Balls();
        break;
      case S_Has5Balls:
        Has5Balls();
        break;
   }
  }

  public void BallCounter(){
    if(!BannerStatus()){
      BallCount = BallCount + 1;
      if(BallCount == 1){
        mIntakeEnumState = IntakeEnumState.S_Has1Ball;}
      else if(BallCount == 2){
        mIntakeEnumState = IntakeEnumState.S_Has2Balls;}
      else if(BallCount == 3){
        mIntakeEnumState = IntakeEnumState.S_Has3Balls;}
      else if(BallCount == 4){
        mIntakeEnumState = IntakeEnumState.S_Has4Balls;}
      else if(BallCount == 5){
        mIntakeEnumState = IntakeEnumState.S_Has5Balls;
        IntakeStatus = false;}
    }
    else{}

  }  

  public void Has0Balls(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }
  }

  public void Has1Ball(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }
  }

  public void Has2Balls(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }
  }

  public void Has3Balls(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }
  }

  public void Has4Balls(){
    if(IntakeStatus){
      ActivateIntake();
    }
    else {
      DeactivateIntake();
    }
  }

  public void Has5Balls(){
DeactivateIntake();
  }
  
  public void ActivateIntake(){
    IntakeMotor.set(0.3);
    IntakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void DeactivateIntake(){
    IntakeMotor.set(0);
    IntakeSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public boolean BannerStatus(){
    return (IntakeMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround);
  }

  @Override
  public void periodic() {
    RunIntakeState();
    BallCounter();
    SmartDashboard.getNumber("BallCount", BallCount);
    SmartDashboard.getBoolean("IntakeActive", IntakeStatus);
    // This method will be called once per scheduler run
  }
}