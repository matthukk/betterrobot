// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.PWMLauncher;

// import frc.robot.subsystems.CANDrivetrain;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(CANDrivetrain drivetrain, PWMLauncher launcher, boolean driveAfterShoot) {
    /**
     * RunCommand is a helper class that creates a command from a single method, in this case we
     * pass it the arcadeDrive method to drive straight back at half power. We modify that command
     * with the .withTimeout(1) decorator to timeout after 1 second, and use the .andThen decorator
     * to stop the drivetrain after the first command times out
     */

    var prepareLaunchCommand = new PrepareLaunch(launcher)
      .withTimeout(LauncherConstants.kLauncherDelay)
      .andThen(new LaunchNote(launcher))
      .withTimeout(10.0)
      .handleInterrupt(() -> launcher.stop());
    
    var moveBackwardCommand = new RunCommand(() -> drivetrain.arcadeDrive(-0.5, 0), drivetrain)
      .withTimeout(1.0);

    RunCommand stopDrivetrainCommand = new RunCommand(() -> drivetrain.arcadeDrive(0, 0), drivetrain);

    // var shootAndDriveBack = prepareLaunchCommand.andThen(moveBackwardCommand).andThen(stopDrivetrainCommand);
    // var shootWithoutDrive = prepareLaunchCommand;

    return /*driveAfterShoot ? shootAndDriveBack : */prepareLaunchCommand;
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
