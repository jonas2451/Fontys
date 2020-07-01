package dao.mapper;

import java.util.Optional;

/**
 * An interface.
 *
 * @param <K> Key type
 * @param <E> Entity type
 */
public interface Mapper<K, E> {

    /**
     * Get the entity class of this mapper.
     *
     * @return class of its entity
     */
    Class<E> entityType();

    /**
     * Used to split an Object up into its attributes.
     *
     * @param e to be exploded
     * @return
     */
    Object[] explode(E e);

    /**
     * Make an entity object from an array of objects.
     *
     * @param e Object[] containing the attribute values for a new Object
     * @return a new imploded object
     */
    E implode(Object[] e);

    /**
     * (used for a relational database)
     * Get the table name of an entity in the database.
     *
     * @return the table name of the mappers entity
     */
    String getTableName();
}
