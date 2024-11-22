// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem.IntakeEnumState;

public class FeederSubsystem extends SubsystemBase {
  /** Creates a new FeederSubsystem. */

  private static TalonFX FeederMotor;

  private IntakeSubsystem mIntakeSubsystem;

  public static FeederState mFeederState;
  public static int BallCount;

  public FeederSubsystem() {
  FeederMotor = new TalonFX(Constants.FeederConstants.kFeederMotor);
  mFeederState = FeederState.S_HasSpace;
  BallCount = 0;

  BallCounter();
  }

  public enum FeederState{
    S_HasSpace, S_FeederFull,
    S_Feeding
  }

  private void RunFeederState(){
    switch (mFeederState) {
      case S_HasSpace:
        HasSpace();
        break;
      case S_FeederFull:
        FeederFull();
        break;
      case S_Feeding:
        Feeding();
        break;                                     
    }
  }

  public void BallCounter(){
  if(!FeederBannerStatus()){
    while(!FeederBannerStatus()){
      new WaitCommand(0.1);
    }
    BallCount++;
  }
  BallCounter();
  }  

  private void HasSpace(){
    FeederLogic();
  }

  private void FeederFull(){
    FeederMotor.set(0);
  }

  private void Feeding(){
    FeederMotor.set(-0.3);
  }

  private void FeederLogic(){
    if(!FeederBannerStatus()){
      FeederMotor.set(0.3);
    }
    else{
      FeederMotor.set(0);
    }    
  }

  public static void ReturnFeederState(){
    if(BallCount >= 5){
      mFeederState = FeederState.S_FeederFull;
    } else{
      mFeederState = FeederState.S_HasSpace;
    }
  }

  public static boolean FeederBannerStatus(){
    return (FeederMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround);
  }

  @Override
  public void periodic() {
    RunFeederState();
    SmartDashboard.putNumber("BallCount", BallCount);
    SmartDashboard.putString("FeederState", mFeederState.toString());
    SmartDashboard.putBoolean("IntakeBanner", FeederBannerStatus());
    // This method will be called once per scheduler run
  }
}
