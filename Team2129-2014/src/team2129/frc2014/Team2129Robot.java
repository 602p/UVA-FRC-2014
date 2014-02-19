package team2129.frc2014;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

public final class Team2129Robot extends SimpleRobot {
    public RobotDrive robotdrive = new RobotDrive(Constants.leftDrivePWM,Constants.rightDrivePWM);
    public ArmControlSystem armCS = new ArmControlSystem();
    public PullbackControlSystem pullCS;
    public FireControlSystem fireCS = new FireControlSystem();
    public Joystick joystick1 = new Joystick(Constants.joy1ID);
    public Joystick joystick2 = new Joystick(Constants.joy2ID);
    public DriverStation driverstation = DriverStation.getInstance();
    public OverrideControlSystem overrideCS = new OverrideControlSystem(this);
    
    public boolean getButtonPressed(int ID){
        return this.joystick1.getRawButton(ID)||this.joystick2.getRawButton(ID);
    }
    
    public void operatorControl() {
        while (isOperatorControl()&&isEnabled()){
            this.driverstation.setDigitalOut(1, this.armCS.lowerLimit.get());
            this.driverstation.setDigitalOut(2, this.armCS.upperLimit.get());
            this.driverstation.setDigitalOut(3, this.pullCS.pullbackLimit.get());
            
            this.robotdrive.tankDrive(this.joystick1, this.joystick2);
            
            if (this.getButtonPressed(Constants.armDownButtonID)){
                this.armCS.move(1);
            }else if (this.getButtonPressed(Constants.armUpButtonID)){
                this.armCS.move(2);
            }else{
                this.armCS.move(0);
            }
            
            if (this.getButtonPressed(Constants.pullbackButtonID)){
                this.pullCS.pullBack();
            }this.pullCS.update();
            
            if (this.getButtonPressed(Constants.fireButtonID)){
                this.fireCS.fire();
            }else if (this.getButtonPressed(Constants.releaseButtonID)){
                this.fireCS.reWind();
            }
            
            this.overrideCS.update();
        }
     }
    
    public void autonomous(){
        if (this.isAutonomous()&&this.isEnabled()){
            this.armCS.armMotor.setSafetyEnabled(false);
            this.armCS.move(1);
            Timer.delay(5);
            this.armCS.move(0);
            this.armCS.armMotor.setSafetyEnabled(true);
            this.pullCS=new PullbackControlSystem();
            while (this.isAutonomous()&&this.isEnabled()){
                this.pullCS.update();
            }
        }
    }
}
