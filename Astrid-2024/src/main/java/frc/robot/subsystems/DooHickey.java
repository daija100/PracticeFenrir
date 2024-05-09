package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DooHickey extends SubsystemBase{
 
  private static final DoubleSolenoid.Value kHickeyEngage = DoubleSolenoid.Value.kReverse;
  private static final DoubleSolenoid.Value kHickeyDisengage = DoubleSolenoid.Value.kForward;

  private final WPI_TalonSRX mSpinner;
  private final DoubleSolenoid mUpPushyThang;
  private final ColorSensorV3 mColorSensor;
  private final ColorMatch mColorMatcher;

  private double mMotorSpeed;
  private double mTargetPos;
  private boolean isRotationControlFinished;

  private static final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  private static final Color kGreenTarget = new Color(0.197, 0.561, 0.240);
  private static final Color kRedTarget = new Color(0.561, 0.232, 0.114);
  private static final Color kYellowTarget = new Color(0.361, 0.524, 0.113);


public DooHickey(){
    mSpinner = spinner;
    mUpPushyThang = upPushyThang;
    mColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    mColorMatcher = new ColorMatch();
    mColorMatcher.addColorMatch(kBlueTarget);
    mColorMatcher.addColorMatch(kGreenTarget);
    mColorMatcher.addColorMatch(kRedTarget);
    mColorMatcher.addColorMatch(kYellowTarget);
    setUpMotionMagic();
}
private void setUpMotionMagic() {
    mSpinner.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
    mSpinner.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
    mSpinner.configNominalOutputForward(0, 10);
    mSpinner.configNominalOutputReverse(0, 10);
    mSpinner.configPeakOutputForward(1.0, 10);
    mSpinner.configPeakOutputReverse(-1.0, 10);
    mSpinner.selectProfileSlot(0, 0);
    mSpinner.configMotionCruiseVelocity(1024, 10);
    mSpinner.configMotionAcceleration(1024, 10);
  }


  public void moveSpinner(double speed) {
    updateSpeed(speed);
  }

  public void setHickeyPosition(double position) {
    mTargetPos = position;
    updatePos(mTargetPos);
  }

  private void updateSpeed(double speed) {
    mSpinner.set(speed);
  }

  private void updatePos(double pos) {
    mSpinner.set(ControlMode.MotionMagic, mTargetPos);
  }

  public void pushThang() {
    mUpPushyThang.set(kHickeyEngage);
  }

  public void retractThang() {
    mUpPushyThang.set(kHickeyDisengage);
  }


  @Override
  public void periodic() {
   
    // This method will be called once per scheduler run
  }
}
