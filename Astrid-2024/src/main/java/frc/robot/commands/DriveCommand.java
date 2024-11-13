// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.units.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.StatusSignal;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.wpilibj.Notifier;

public class DriveCommand extends Command {
  private static final double MAX_UPDATE_PERIOD = 0.05; // Wait up to 50ms
  private final DoubleSupplier m_throttle;
  private final Chassis m_drivebase;
  private final StatusSignal<Angle> m_yawGetter;

  private double m_yawHolder = 0;

  private final Notifier m_driveThread;

  /** Creates a new DriveStraightC. */
  public DriveCommand(Chassis drivebase, DoubleSupplier throttle) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_throttle = throttle;
    m_drivebase = drivebase;
    m_yawGetter = m_drivebase.getYaw();
    m_driveThread = new Notifier(this::driveExecution);
    addRequirements(drivebase);
  }

  /**
  * This is the threaded context method that gets called as fast as possible.
  * The timing is determined by how fast the Pigeon2 reports its yaw, up to MAX_UPDATE_PERIOD
  */
  private void driveExecution() {
    /* Get our current yaw and find the error from the yaw we want to hold */
    final double err = m_yawHolder - m_yawGetter.waitForUpdate(MAX_UPDATE_PERIOD).getValue().in(Degrees);
    /* Simple P-loop, where 30 degrees off corresponds to 100% output */
    final double kP = 1.0 / 30;
    double correction = err * kP;
    /* And apply it to the arcade drive */
    m_drivebase.arcadeDrive(m_throttle.getAsDouble(), -correction);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    /* On initialize, latch the current yaw and begin correction */
    m_yawHolder = m_yawGetter.waitForUpdate(MAX_UPDATE_PERIOD).getValue().in(Degrees);
    /* Update as fast as possible, the waitForUpdate will manage the loop period */
    m_driveThread.startPeriodic(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveThread.stop();
  }

}
