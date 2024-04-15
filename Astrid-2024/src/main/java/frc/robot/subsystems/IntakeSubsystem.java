// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  
  private TalonFX IntakeMotor;
  
  private DoubleSolenoid IntakeSolenoid;
  
  public boolean IntakeStatus;
  
  public static IntakeEnumState mIntakeEnumState;

  public IntakeSubsystem() {

  IntakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor);
 
  IntakeSolenoid =  new DoubleSolenoid(null, 
    Constants.IntakeConstants.kIntakeSolenoidForward, 
  Constants.IntakeConstants.kIntakeSolenoidReverse);

  mIntakeEnumState = IntakeEnumState.S_Has0Balls;
  }

  public enum IntakeEnumState{
    S_Has0Balls, S_Has1Ball,
    S_Has2Balls, S_Has3Balls
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
   }
  }
  
  public void Has0Balls(){
    if(IntakeStatus){
    if(BannerStatus()){
    IntakeMotor.set(0);
    mIntakeEnumState = IntakeEnumState.S_Has1Ball;
    }
    else {IntakeMotor.set(.5);
    }
  }
  else{}
  }

  public void Has1Ball(){

  }

  public void Has2Balls(){

  }

  public void Has3Balls(){

  }
  public boolean BannerStatus(){
    return (IntakeMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround);
  }
  @Override
  public void periodic() {
    RunIntakeState();
    // This method will be called once per scheduler run
  }
}
