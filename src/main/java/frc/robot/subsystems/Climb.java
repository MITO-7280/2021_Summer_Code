// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  /** Creates a new SpinningCollect. */
  // public static Spark climbMasterMotor = new Spark(Constants.climbMasterMotor);
  // public static Spark climbSlaveMotor = new Spark(Constants.climbSlaveMotor);

  public Spark spark = new Spark(0);
  public Climb() {
  }

  public void startClimb(){
    // climbMasterMotor.setSpeed(Constants.climbSpeed);
    // double masterMotorVelocity = climbMasterMotor.getSpeed();
    // climbSlaveMotor.setSpeed(masterMotorVelocity);
    spark.set(0.5);
  }

  public void stopClimb(){
    // double position = climbMasterMotor.getPosition();
    // climbMasterMotor.setPosition(position);
    // climbSlaveMotor.setPosition(position);
    spark.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
