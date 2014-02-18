/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2129.frc2014;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Louis
 */
public final class ArmControlSystem {
    public Jaguar armMotor;
    public DigitalInput lowerLimit;
    public DigitalInput upperLimit;
    public ArmControlSystem(){
        this.armMotor=new Jaguar(Constants.armPWM);
        this.lowerLimit=new DigitalInput(Constants.armLimitBottomID);
        this.upperLimit=new DigitalInput(Constants.armLimitTopID);
    }

    public void move(int d) {
        if ((this.lowerLimit.get())&&d==1){
            this.armMotor.set(0.75);
        }
        else if ((this.upperLimit.get())&&d==2){
            this.armMotor.set(-0.75);
        }
        else{
            this.armMotor.set(0);
        }
    }
}
