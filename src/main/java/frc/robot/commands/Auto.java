// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class Auto extends SequentialCommandGroup {
  /** Creates a new Giveball. */
  public Auto() {
    // Use addRequirements() here to declare subsystem dependencies.

    addCommands(new Forward(100000),
    
    new Turn(-100000)

    );
  }


  

}
