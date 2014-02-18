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
public final class PullbackControlSystem {
    public Jaguar pullbackMotor;
    public DigitalInput pullbackLimit;
    public String state="DO_NOTHING";
    public int count=0;
    public long time=3500L;
    
    private class timedRewindThread implements Runnable{
        private PullbackControlSystem pullCS;
        
        public timedRewindThread(PullbackControlSystem pullCS){
            this.pullCS=pullCS;
        }

        public void run() {
            this.pullCS.pullbackMotor.setSafetyEnabled(false);
            this.pullCS.pullbackMotor.set(-1);
            try {
                Thread.sleep(this.pullCS.time);
            } catch (InterruptedException ex) {
                this.pullCS.pullbackMotor.setSafetyEnabled(true);
                this.pullCS.pullbackMotor.set(0);
                this.pullCS.state="DO_NOTHING";
            }
            this.pullCS.pullbackMotor.setSafetyEnabled(true);
            this.pullCS.pullbackMotor.set(0);
            this.pullCS.state="DO_NOTHING";
        }
        
    }
    
    public PullbackControlSystem() {
        this.pullbackMotor=new Jaguar(Constants.pullbackPWM);
        this.pullbackLimit=new DigitalInput(Constants.pendulumLimitID);
        this.pullBack();
    }

    public void pullBack() {
        if (this.state.equals("DO_NOTHING")){
            this.state="PULL_BACK";
        }
    }
    
    public void update() {
        if (this.state.equals("PULL_BACK")){
            if (this.pullbackLimit.get()){
                this.pullbackMotor.set(1);
            }else{
                this.state="REWIND";
                new Thread(new timedRewindThread(this)).start();
            }
        }else if (this.state.equals("REWIND")){
        }else{
            this.pullbackMotor.set(0);
        }
    }
}
