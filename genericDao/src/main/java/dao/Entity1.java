package dao;

import java.io.Serializable;
import java.util.Optional;

/**
 * This interface has to be implemented by every class that is used within a DAO.
 *
 * @param <K> Key type
 */
public interface Entity1<K extends Serializable> {

    /**
     * get the key type of this entity.
     *
     * @return by default String for the usage of GUIDs / UUIDs
     */
    default Serializable getKey(){return String.class;};

    /**
     * Get the unique identification item of this entity.
     *
     * @return the id / primary key value of this entity instance
     */
    default K getId() {return null;};

    /**
     * (used with a relational database)
     * Get the name of the primary key in the database according to this entity.
     *
     * @return primary key name of this entity
     */
    default String getNaturalId() {return "id";};


}
