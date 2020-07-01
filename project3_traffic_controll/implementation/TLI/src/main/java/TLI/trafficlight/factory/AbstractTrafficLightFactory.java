package TLI.trafficlight.factory;

import TLI.trafficlight.lightObservable.Observer;
import TLI.position.Position;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

/**
 * A Factory to create traffic lights.
 */
public abstract class AbstractTrafficLightFactory {

    protected static AbstractTrafficLightFactory FACTORY;


    /**
     * Creates car traffic light.
     *
     * @return car traffic light. Take care about observing yourself
     */
    public abstract CarTrafficLight createCarTrafficLight(Position position);

    /**
     * Creates car traffic light object.
     *
     * @param observer of that traffic light
     * @return car traffic light that has observers already registered
     */
    public abstract CarTrafficLight createObservedCarLight(Position position, Observer ...observer);

    /**
     * Creates pedestrian traffic light.
     *
     * @return a pedestrian traffic light. Take care about observing yourself
     */
    public abstract PedestrianTrafficLight createPedestrianTrafficLight(Position position);

    /**
     * Creates pedestrian traffic light.
     *
     * @param observer of that traffic light
     * @return a pedestrian traffic light that is observed by {@param observer}
     */
    public abstract PedestrianTrafficLight createObservedPedestrianTrafficLight(Position position, Observer ...observer);
}
