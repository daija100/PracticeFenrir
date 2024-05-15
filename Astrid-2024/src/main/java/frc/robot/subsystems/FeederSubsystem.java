// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem.IntakeEnumState;

public class FeederSubsystem extends SubsystemBase {
  /** Creates a new FeederSubsystem. */

  private TalonFX FeederMotor;

  private IntakeSubsystem mIntakeSubsystem;

  public static FeederState mFeederState;
  private int BallCount;

  public FeederSubsystem() {
  FeederMotor = new TalonFX(Constants.FeederConstants.kFeederMotor);
  mFeederState = FeederState.S_Feeder0Balls;
  BallCount = 0;
  }

  public enum FeederState{
    S_Feeder0Balls, S_Feeder1Ball,
    S_Feeder2Balls, S_Feeder3Balls,
    S_Feeder4Balls, S_Feeder5Balls,
    S_Feeding
  }

  private void RunFeederState(){
    switch (mFeederState) {
      case S_Feeder0Balls:
        Feeder0Balls();
        break;
      case S_Feeder1Ball:
        Feeder1Ball();
        break;
      case S_Feeder2Balls:
        Feeder2Balls();
        break;
      case S_Feeder3Balls:
        Feeder3Balls();
        break;
      case S_Feeder4Balls:
        Feeder4Balls();
        break;
      case S_Feeder5Balls:
        Feeder5Balls();
        break;                                        
    }
  }

  public void BallCounter(){
  if(!FeederBannerStatus()){
    BallCount = BallCount + 1;
      if(BallCount == 1){
       IntakeSubsystem.mIntakeEnumState = IntakeEnumState.S_Has1Ball;
       mFeederState = FeederState.S_Feeder1Ball;}
     else if(BallCount == 2){
       IntakeSubsystem.mIntakeEnumState = IntakeEnumState.S_Has2Balls;
       mFeederState = FeederState.S_Feeder2Balls;}
     else if(BallCount == 3){
       IntakeSubsystem.mIntakeEnumState = IntakeEnumState.S_Has3Balls;
       mFeederState = FeederState.S_Feeder3Balls;}
     else if(BallCount == 4){
       IntakeSubsystem.mIntakeEnumState = IntakeEnumState.S_Has4Balls;
       mFeederState = FeederState.S_Feeder4Balls;}
     else if(BallCount == 5){
       IntakeSubsystem.mIntakeEnumState = IntakeEnumState.S_Has5Balls;
       mFeederState = FeederState.S_Feeder5Balls;
       mIntakeSubsystem.IntakeStatus = false;}
     if(!FeederBannerStatus()){}
     else{BallCounter();}
    }
    else{}

  }  

  private void Feeder0Balls(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }
  }

  private void Feeder1Ball(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  private void Feeder2Balls(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  private void Feeder3Balls(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  private void Feeder4Balls(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  private void Feeder5Balls(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  public boolean FeederBannerStatus(){
    return (FeederMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround);
  }

  @Override
  public void periodic() {
    RunFeederState();
    SmartDashboard.putNumber("BallCount", BallCount);
    // This method will be called once per scheduler run
  }
}
