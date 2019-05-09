package dao;

import dao.mapper.Mapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public interface DAO<K extends Serializable, E extends Entity1<K>> {

    Optional<E> get(String id);

    List<E> getAll();

    E save(E e);

    E update(E e);

    void delete(E e);

    default String lastId(){
        return "0";
    }

    void loadNewMapper(Mapper<K, E> mapper);

    Collection<E> searchFor(E e, String search);

    /**hom
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
     * @since 0.4
     */
    default Collection<E> saveAll(Iterable<E> entities) {
        return StreamSupport.stream( entities.spliterator(), false )
                .map( e -> this.save( e ) ).collect( toList() );
    }

    /**hom
     * Save the given entities, returning the result as a list.
     *
     * This default implementation will benefit from any improvemenst by an overwritten
     * {@code saveAll(Collection &lt;E&gt;entities)}
     * @param entities to save
     * @return the saved versions of the entities in a list.
     * @since 0.4
     */
    default Collection<E> saveAll(E... entities) {

        return saveAll( Arrays.asList( entities ) );
    }
}
