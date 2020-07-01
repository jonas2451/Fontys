/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TLI.trafficlight.factory;

import TLI.trafficlight.lightObservable.Observer;
import TLI.position.Position;
import TLI.strategy.EuropeanPedestrianTLStrategy;
import TLI.strategy.GermanCarTLStrategy;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

/**
 *
 * Creates German TrafficLight
 */
public class GermanTrafficLightFactory extends AbstractTrafficLightFactory{

    private GermanTrafficLightFactory() {
    }
    
    public static synchronized GermanTrafficLightFactory getInstance() {
        if (FACTORY != null) return (GermanTrafficLightFactory) FACTORY;
        else {
            FACTORY = new GermanTrafficLightFactory();
            return (GermanTrafficLightFactory) FACTORY;
        }
    }

    @Override
    public CarTrafficLight createCarTrafficLight(Position position) {
//        ConsoleLight light = new ConsoleLight();
        CarTrafficLight trafficLight = new CarTrafficLight(position, new GermanCarTLStrategy(1000, 1000));
//        trafficLight.registerObserver(light);
        return trafficLight;
    }

    @Override
    public CarTrafficLight createObservedCarLight(Position position, Observer... observer) {
        CarTrafficLight light = new CarTrafficLight(position, new GermanCarTLStrategy(1000, 1000));
        for (Observer o : observer) {
            light.registerObserver(o);
        }
        return light;
    }

    @Override
    public PedestrianTrafficLight createPedestrianTrafficLight(Position position) {
        return new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
    }

    @Override
    public PedestrianTrafficLight createObservedPedestrianTrafficLight(Position position, Observer... observer) {
        PedestrianTrafficLight light = new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
        for (Observer o : observer) {
            light.registerObserver(o);
        }
        return light;
    }
    
}
