// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase {
  /** Creates a new Lit. */

  public static TalonFX shooterMasterMotor = new TalonFX(Constants.shooterMasterMotor);
  public static TalonFX shooterSlaveMotor = new TalonFX(Constants.shooterSlaveMotor);
  public static TalonFX ballDeliverMotor = new TalonFX(Constants.ballDeliverMotor);
  public static TalonFX shooterAngleMotor = new TalonFX(Constants.shooterAngleMotor);

  DoubleSolenoid ballDeliverSolenoid = new DoubleSolenoid(1,6,7);

  AnalogPotentiometer shooterAngle = new AnalogPotentiometer(0, 360, 0);
  DigitalInput reseter = new DigitalInput(0);

  public int shooterCurrentAngle = 0;

  public int targetPosition;
  public Shooter() {
    Constants.initFalconPID(shooterMasterMotor, 1);
    Constants.initFalconPID(shooterSlaveMotor, 1);
    Constants.initFalconPID(ballDeliverMotor, 1);
    Constants.initFalconPID(shooterAngleMotor, 1);

    shooterMasterMotor.setInverted(true);
    shooterSlaveMotor.setInverted(false);
    shooterAngleMotor.setInverted(false);

    shooterSlaveMotor.follow(shooterMasterMotor);

    shooterMasterMotor.setNeutralMode(NeutralMode.Coast);
    shooterSlaveMotor.setNeutralMode(NeutralMode.Coast);
    shooterAngleMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void startShoot(){
    // ballDeliverSolenoid.set(true);
    ballDeliverMotor.set(ControlMode.PercentOutput, 0.1);
    Robot.judge.isShooting = true;
  }

  public void stopShoot(){
    // ballDeliverSolenoid.set(false);
    ballDeliverMotor.set(ControlMode.PercentOutput, 0);
    Robot.judge.isShooting = false;
  }

  public void configVelocityPID(){
    Constants.setFalconPID(shooterMasterMotor, 0, 0.4, 0, 0);
    Constants.setFalconPID(shooterSlaveMotor, 0, 0.4, 0, 0);
  }

  public void increaseAngleTest(int _position){
    Constants.setFalconPID(shooterAngleMotor, 0, 0.018, 0, 0);
    shooterAngleMotor.set(ControlMode.Position, -_position);
  }

  public void decreaseAngleTest(int _position){
    Constants.setFalconPID(shooterAngleMotor, 0, 0.018, 0, 0);
    shooterAngleMotor.set(ControlMode.Position, _position);
  }

  public void angleMotorStop(){
    shooterAngleMotor.set(ControlMode.PercentOutput, 0);
  }

  public boolean angleTest(int _pos, int speed){
    Constants.setFalconPID(shooterAngleMotor, 0, 0.1, 0, 0);

        if(Math.abs(shooterAngleMotor.getSelectedSensorPosition())<_pos){
          shooterAngleMotor.set(ControlMode.Velocity, -speed);
          return false;
        } else {
          shooterAngleMotor.set(ControlMode.Velocity, 0);
          return true;
        }


  }

  public boolean angleTestdecrease(int _pos, int speed){
    Constants.setFalconPID(shooterAngleMotor, 0, 0.1, 0, 0);

        if(Math.abs(shooterAngleMotor.getSelectedSensorPosition())>_pos){
          shooterAngleMotor.set(ControlMode.Velocity, speed);
          return false;
        } else {
          shooterAngleMotor.set(ControlMode.Velocity, 0);
          return true;
        }
  }

  public boolean autoShooterTest(){
    if (Robot.networktable.shooterTargetAngle<-1){
      shooterAngleMotor.set(ControlMode.PercentOutput, -0.05);
      return false;
    } else if (Robot.networktable.shooterTargetAngle>1){
      shooterAngleMotor.set(ControlMode.PercentOutput, 0.05);
      return false;
    } else {
      shooterAngleMotor.set(ControlMode.PercentOutput, 0);
      return true;
    }
  }
   

 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("flywheelVelocity", shooterMasterMotor.getSelectedSensorVelocity());
    if (Robot.judge.isPreparing){
      if (Robot.judge.isReady){
        Constants.setFalconPID(shooterMasterMotor, 0, 0.4, 0, 0);
        Constants.setFalconPID(shooterSlaveMotor, 0, 0.4, 0, 0);
        shooterMasterMotor.set(ControlMode.Velocity, 22000);
        shooterSlaveMotor.set(ControlMode.Velocity, 22000);
      } else {
        Constants.setFalconPID(shooterMasterMotor, 0, 0.4, 0, 0);
        Constants.setFalconPID(shooterSlaveMotor, 0, 0.4, 0, 0);
        shooterMasterMotor.set(ControlMode.Velocity, 22000);
        shooterSlaveMotor.set(ControlMode.Velocity, 22000);
      }
    } else {
      shooterMasterMotor.set(ControlMode.PercentOutput, 0);
      shooterSlaveMotor.set(ControlMode.PercentOutput, 0);
    }

    SmartDashboard.putNumber("shooterAnglePosition", shooterAngleMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("shooterAngleVelocity", shooterAngleMotor.getSelectedSensorVelocity());

    SmartDashboard.putNumber("angle", shooterCurrentAngle);
    
    if (Robot.judge.isShooting){
      ballDeliverMotor.set(ControlMode.PercentOutput, 0.2);
      ballDeliverSolenoid.set(Value.kReverse);
    } else {
      ballDeliverMotor.set(ControlMode.PercentOutput, 0);
      ballDeliverSolenoid.set(Value.kForward);
    }

    if (shooterAngle.get()<150){
      shooterCurrentAngle = ((int)shooterAngle.get()+360-164)*18/60;
    } else{
      shooterCurrentAngle = ((int)shooterAngle.get()-164)*18/60;
    }

    if (Robot.judge.isManualRotate){
      if (Robot.oi.rotateStick.getPOV()==0){
        shooterAngleMotor.set(ControlMode.PercentOutput, -0.1);
      } else if (Robot.oi.rotateStick.getPOV()==180){
        shooterAngleMotor.set(ControlMode.PercentOutput, 0.1);
      } else {
        shooterAngleMotor.set(ControlMode.PercentOutput, 0);
      }
    } else if (Robot.judge.tableOn){
      if (Robot.networktable.shooterTargetAngle<-1){
        shooterAngleMotor.set(ControlMode.PercentOutput, -0.05);
      } else if (Robot.networktable.shooterTargetAngle>1){
        shooterAngleMotor.set(ControlMode.PercentOutput, 0.05);
      } else {
        shooterAngleMotor.set(ControlMode.PercentOutput, 0);
      }
    } else {
      shooterAngleMotor.set(ControlMode.PercentOutput, 0);
    }
  } 
}
