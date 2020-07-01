package dao;

import dao.mapper.Mapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * A DAO interface with crud operations.
 *
 * @param <K> Key type of the entity
 * @param <E> Entity type thats used in the DAO
 */
public interface DAO<K extends Serializable, E extends Entity1<K>> {

    /**
     * Get a record according to the key.
     *
     * @param key = primary key of the Entity in the database
     * @return an optional object according to the key
     */
    Optional<E> get(K key);

    /**
     * Get all records of a certain Entity from the database.
     *
     * @return a collection of entities
     */
    Collection<E> getAll();

    /**
     * Creates a new record in the database.
     *
     * @param e to be saved
     * @return the saved Object from the database
     */
    E save(E e);

    /**
     * Updated the according record in the database.
     * if no according record is found {@code dao.save(e)} is invoked
     *
     * @param e to be updated
     * @return the updated Object from the database
     */
    E update(E e);

    /**
     * Deletes the Object from the parameter.
     *
     * @param e to be deleted
     */
    void delete(E e);

    default String lastId(){
        return "0";
    }

    /**
     * Save all entities, returning the result in a collection with the saved
     * versions of the entities, generated fields and all.
     *
     * The caller is advised to give this DAO a transaction token, so this
     * operation can perform atomically for those persistence implementations
     * that support proper commit and rollback.
     *
     * Implementations that recreate resources on every call of
     * {@code dao.save(e)} should probably overwrite this default implementation
     * for improved performance and memory efficiency.
     *
     * @param entities to save
     * @return the saved entities
     *
     * {Code by Hom}
     */
    default Collection<E> saveAll(Iterable<E> entities) {
        return StreamSupport.stream( entities.spliterator(), false )
                .map( e -> this.save( e ) ).collect( toList() );
    }

    /**
     * Save the given entities, returning the result as a list.
     *
     * This default implementation will benefit from any improvemenst by an overwritten
     * {@code saveAll(Collection &lt;E&gt;entities)}
     * @param entities to save
     * @return the saved versions of the entities in a list.
     *
     * {Code by Hom}
     */
    default Collection<E> saveAll(E... entities) {

        return saveAll( Arrays.asList( entities ) );
    }

    /**
     * Gets a Collection of objects, containing the search results.
     *
     * @param column name of the column in the database
     * @param value search criterion
     * @return a collection of objects
     */
    default Collection<E> getByColumnValue(String column, String value) {throw new UnsupportedOperationException("No default implementation for this method!");};
}
