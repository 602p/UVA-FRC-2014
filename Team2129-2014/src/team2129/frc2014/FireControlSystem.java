/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2129.frc2014;

import edu.wpi.first.wpilibj.Jaguar;

//1.6s

/**
 *
 * @author Louis
 */
public class FireControlSystem {
    public Jaguar releaseMotor;
    public String state="DO_NOTHING";
    public long time=2400L;
    
    private class timedFireThread implements Runnable{
        private FireControlSystem fireCS;
        
        public timedFireThread(FireControlSystem fireCS){
            this.fireCS=fireCS;
        }

        public void run() {
            this.fireCS.releaseMotor.setSafetyEnabled(false);
            this.fireCS.releaseMotor.set(-0.5);
            try {
                Thread.sleep(this.fireCS.time);
            } catch (InterruptedException ex) {
                this.fireCS.releaseMotor.setSafetyEnabled(true);
                this.fireCS.releaseMotor.set(0);
                this.fireCS.state="DO_NOTHING";
            }
            this.fireCS.releaseMotor.setSafetyEnabled(true);
            this.fireCS.releaseMotor.set(0);
            this.fireCS.state="DO_NOTHING";
        }
        
    }
    
    private class timedRewindThread implements Runnable{
        private FireControlSystem fireCS;
        
        public timedRewindThread(FireControlSystem fireCS){
            this.fireCS=fireCS;
        }

        public void run() {
            this.fireCS.releaseMotor.setSafetyEnabled(false);
            this.fireCS.releaseMotor.set(0.5);
            try {
                Thread.sleep(this.fireCS.time);
            } catch (InterruptedException ex) {
                this.fireCS.releaseMotor.setSafetyEnabled(true);
                this.fireCS.releaseMotor.set(0);
                this.fireCS.state="DO_NOTHING";
            }
            this.fireCS.releaseMotor.setSafetyEnabled(true);
            this.fireCS.releaseMotor.set(0);
            this.fireCS.state="DO_NOTHING";
        }
        
    }
    
    public FireControlSystem(){
        this.releaseMotor=new Jaguar(Constants.releasePWM);
        this.state="DO_NOTHING";
    }
    
    public void fire(){
        if (this.state.equals("DO_NOTHING")){
            this.state="FIRE";
            new Thread(new FireControlSystem.timedFireThread(this)).start();
        }
    }
    
    public void reWind(){
        if (this.state.equals("DO_NOTHING")){
            this.state="REWIND";
            new Thread(new FireControlSystem.timedRewindThread(this)).start();
        }
    }
}
