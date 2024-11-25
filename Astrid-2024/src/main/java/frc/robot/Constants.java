// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kCoDriverControllerPort = 1;
  }

  public static class IntakeConstants{
    public static final int kIntakeMotor = 5;
    public static final int kIntakeSolenoidForward = 1;
    public static final int kIntakeSolenoidReverse = 2;
  }
  
  public static class FeederConstants{
    public static final int kFeederMotor = 6;
  }

  public static class ShooterConstants {
    public static final int kShooterLeft = 7;
    public static final int kShooterRight = 8;
    public static final int kShooterHoodForward = 3;
    public static final int kShooterHoodReverse = 4;
    public static final int kShooterLatchForward = 5;
    public static final int kShooterLatchReverse = 6;

    public static final double kV = 0.01;
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }

}
