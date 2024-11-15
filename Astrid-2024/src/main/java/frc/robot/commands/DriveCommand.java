// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Notifier;

public class DriveCommand extends Command {
  //Gets the degrees that the joystick is at in a double variable
  private final DoubleSupplier m_throttleX;
  private final DoubleSupplier m_throttleY;

  //The subsystem
  private final Chassis m_drivebase;

  //Helps report
  private final Notifier m_driveThread;

  /** Creates a new DriveStraightC. */
  public DriveCommand(Chassis drivebase, DoubleSupplier throttleX, DoubleSupplier throttleY) {
    //Gets the values and assigns them to their variable
    m_throttleX = throttleX;
    m_throttleY = throttleY;
    m_drivebase = drivebase;
    m_driveThread = new Notifier(this::driveExecution);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivebase);
  }

  /**
  * This is the threaded context method that gets called as fast as possible.
  * The timing is determined by how fast the Pigeon2 reports its yaw, up to MAX_UPDATE_PERIOD
  */
  private void driveExecution() {
    m_drivebase.arcadeDrive(m_throttleY.getAsDouble(), m_throttleX.getAsDouble());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    /* Update as fast as possible, the waitForUpdate will manage the loop period */
    m_driveThread.startPeriodic(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveThread.stop();
  }

}
