/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2129.frc2014;

/**
 *
 * @author Louis
 */
public class OverrideControlSystem {
    Team2129Robot robot;
    public OverrideControlSystem(Team2129Robot robot){
        this.robot=robot;
    }
    
    public void update(){ //6/7 8/9 10/11
        if (this.robot.getButtonPressed( 6)){
            this.robot.pullCS.pullbackMotor.set(-1);
        }
        if (this.robot.getButtonPressed( 7)){
            this.robot.pullCS.pullbackMotor.set( 1);
        }
        if (this.robot.getButtonPressed( 8)){
            this.robot.fireCS.releaseMotor .set(-1);
        }
        if (this.robot.getButtonPressed( 9)){
            this.robot.fireCS.releaseMotor .set( 1);
        }
        if (this.robot.getButtonPressed(10)){
            this.robot.armCS.armMotor      .set(-1);
        }
        if (this.robot.getButtonPressed(11)){
            this.robot.armCS.armMotor      .set( 1);
        }
    }
}
