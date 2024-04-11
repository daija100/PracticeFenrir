// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.networktables.PubSub;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  
  private TalonFX IntakeMotor;
  

  public static IntakeEnumState mIntakeEnumState;

  public IntakeSubsystem() {

  IntakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor);

  mIntakeEnumState = IntakeEnumState.S_WaitingForBall;
  }

  public enum IntakeEnumState{
    S_WaitingForBall, S_HasBall
  }

    public void RunIntakeState(){
    switch (mIntakeEnumState) {
      case S_WaitingForBall:
        WaitingForBall();
        break;
      case S_HasBall:
        HasBall();
        break;      
   }
  }
  
  public void WaitingForBall(){
    if(BannerStatus()){
    IntakeMotor.set(0);
    mIntakeEnumState = IntakeEnumState.S_HasBall;
    }
    else {IntakeMotor.set(.5);
    }
  }

  public void HasBall(){

  }

  public boolean BannerStatus(){
    return (IntakeMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround)
  }
  @Override
  public void periodic() {
    RunIntakeState();
    // This method will be called once per scheduler run
  }
}
