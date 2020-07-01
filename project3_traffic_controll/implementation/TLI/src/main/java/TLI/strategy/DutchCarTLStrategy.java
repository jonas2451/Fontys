package TLI.strategy;

import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;

import java.util.Timer;
import java.util.TimerTask;


public class DutchCarTLStrategy implements LightStrategy {
    private Timer timer;
    private TimerTask drive;
    private TimerTask warning;
    private TimerTask wait;

    private int delayGreenToYellow, delayYellowToRed;

    /**
     * Switches the TrafficLight from red to green
     * @param trafficLight is the TrafficLight that is to be switched
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight go(TrafficLight trafficLight) {
        trafficLight.setState(State.GO);
        return trafficLight;
    }

    /**
     * Switches the TrafficLight from red to green
     * @param trafficLight is the TrafficLight that is to be switched
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight stop(TrafficLight trafficLight) {
        timer.scheduleAtFixedRate(warning, delayGreenToYellow, 0);
        timer.scheduleAtFixedRate(wait, delayYellowToRed,0);
        return trafficLight;
    }

    public void Orange(TrafficLight light){
        light.setState(State.WARNING);
    }
    public void Red(TrafficLight light){
        light.setState(State.WAIT);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getDelayGreenToYellow()
    {
        return delayGreenToYellow;
    }

    public void setDelayGreenToYellow(int delayGreenToYellow)
    {
        this.delayGreenToYellow = delayGreenToYellow;
    }

    public int getDelayYellowToRed()
    {
        return delayYellowToRed;
    }

    public void setDelayYellowToRed(int delayYellowToRed)
    {
        this.delayYellowToRed = delayYellowToRed;
    }
    //</editor-fold>
}
