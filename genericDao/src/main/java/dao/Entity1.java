package dao;

import java.io.Serializable;
import java.util.Optional;

/**
 *
 * @param <K> Key type
 */
public interface Entity1<K extends Serializable> {

//    Object[] explode();

    default Serializable getKey(){return String.class;};

    default Optional<String> getSearchCriterion(){return null;};

//    Entity1 implode(Object[] e);

    K getID();

    /**
     *
     * A method that return the name of the primary key attribute of an Object in Java and the Database
     * @return primary key
     */
    String getNaturalId();


}
