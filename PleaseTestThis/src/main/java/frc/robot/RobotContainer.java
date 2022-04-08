/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Auton2022.ShootBackUpAuton;
import frc.robot.commands.Auton2022.SimpleAuton;
import frc.robot.commands.MultiStepAuton.LowHighSequential;
import frc.robot.commands.TwoHighAuton.ProductionTwoHigh;
import frc.robot.subsystems.BreakBeams;
import frc.robot.subsystems.Climbers;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroAccelerometer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelights;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Solenoids;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.IntakeConstants;
import static frc.robot.Constants.*;




/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  public final GyroAccelerometer m_gyroAccel = new GyroAccelerometer();
  public final Conveyor m_conveyor = new Conveyor();
  public final Intake m_intake = new Intake(IntakeConstants.intakeMotorID);
  public final Shooter m_shooter = new Shooter();
  public final Climbers m_climbers = new Climbers();
  public final BreakBeams m_breakBeams = new BreakBeams();
  public final Drivetrain m_drivetrain = new Drivetrain();
  public final Solenoids m_solenoids = new Solenoids();
  public final Limelights m_limelights = new Limelights();

  //The robot's commands
  public SimpleAuton m_SimpleAuton = new SimpleAuton(m_drivetrain, m_intake, m_solenoids, m_shooter, m_conveyor, m_breakBeams);
  public ShootBackUpAuton m_ShootBackUpAuton = new ShootBackUpAuton(m_drivetrain, m_shooter, m_conveyor);
  public LowHighSequential m_LowHighSequential = new LowHighSequential(m_intake, m_drivetrain, m_conveyor, m_solenoids, m_shooter, m_breakBeams, m_limelights);
  public ProductionTwoHigh m_ProductionTwoHigh = new ProductionTwoHigh(m_drivetrain, m_intake, m_solenoids, m_shooter, m_limelights, m_conveyor, m_breakBeams);

  public SendableChooser<Command> autonChooser = new SendableChooser<>();

  public static final Joystick leftJoystick = new Joystick(0);
  public static final JoystickButton calibrateButton = new JoystickButton(leftJoystick, 11);
  public static final JoystickButton climbUp2Button = new JoystickButton(leftJoystick, 8);
  public static final JoystickButton climbDown2Button = new JoystickButton(leftJoystick, 10);
  
  public static final Joystick rightJoystick = new Joystick(1); 
  public static final JoystickButton solenoidClimbButton = new JoystickButton(rightJoystick, 8);
  public static final JoystickButton stageOneForceUp = new JoystickButton(rightJoystick, 6);
  public static final JoystickButton stageOneForceDown = new JoystickButton(rightJoystick, 4);
  public static final JoystickButton autoAimButton = new JoystickButton(rightJoystick, 1);//this is the trigger

  public static final XboxController operatorJoystick = new XboxController(2);
  public static final POVButton conveyorUpButton = new POVButton(operatorJoystick, 0);//up on dpad
  public static final POVButton conveyorDownButton = new POVButton(operatorJoystick, 180);//down on dpad
  public static final JoystickButton climbUp1Button = new JoystickButton(operatorJoystick, 4);//y button
  public static final JoystickButton climbDown1Button = new JoystickButton(operatorJoystick, 1);//a button
  public static final JoystickButton solenoidIntakeButton = new JoystickButton(operatorJoystick, 3);
  public static final JoystickButton solenoidClimbButtonOp = new JoystickButton(operatorJoystick, 5);
  public static final JoystickButton intakeButton = new JoystickButton(operatorJoystick, 2);//b button on xbox controller
  public static final JoystickButton shootHighTrigger = new JoystickButton(operatorJoystick, 6);//right bumper
  
  //public final XboxController driverController = new XboxController(3);
  //public final Joystick driverLeftJoystick = driverController.getRawAxis();
  public static boolean climbDown2ButtonOperator(){
    if(operatorJoystick.getRawAxis(1)>.5){
      return true;
    } else{
      return false;
    }
  }

  public static boolean climbUp2ButtonOperator(){
    if(operatorJoystick.getRawAxis(1)<-.5){
      return true;
    } else{
      return false;
    }
  }

  public static boolean shootSlowTrigger(){
    if(operatorJoystick.getRawAxis(3)> 0.1){
      return true;
    } else{
      return false;
    }
  }

  public static boolean shootSafeWall(){
    if(operatorJoystick.getRawAxis(2)>0.1){
      return true;
    } else{
      return false;
    }
  }

  public static double returnLeftAxis(int leftAxis){
    double leftStick = leftJoystick.getRawAxis(leftAxis);
        
    if(leftStick > deadzone || leftStick < -deadzone){
        return leftStick * powerRegulator;
    }
    else{
        return 0;
    }
  }
  public static double returnRightAxis(int rightAxis){
    double rightStick = rightJoystick.getRawAxis(rightAxis);
    if(rightStick > deadzone || rightStick < -deadzone){
        return rightStick * powerRegulator;
   }
   else{
        return 0;
   }
  }
  
  // @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    System.out.println(m_gyroAccel.ahrs.getPitch());
    System.out.println(m_gyroAccel.ahrs.getYaw());
    System.out.println(m_gyroAccel.ahrs.getRoll());
  }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    autonChooser.setDefaultOption("ShootTwoHigh", m_ProductionTwoHigh);
    autonChooser.addOption("ShootTwoLow", m_SimpleAuton);
    autonChooser.addOption("OneLow", m_ShootBackUpAuton);
    autonChooser.addOption("OneLowOneHigh", m_LowHighSequential);
    SmartDashboard.putData(autonChooser);
    // Configure the button bindings
    configureButtonBindings();
    //GyroAccelerometer.ahrs.calibrate();
  }
  
  public Command getAutonomousCommand(){
    return autonChooser.getSelected();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }
}

