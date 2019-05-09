package dao.mapper;

import java.util.Optional;

public interface Mapper<K, E> {

    Class<E> entityType();

    Object[] explode(E e);

    E implode(Object[] e);

    String getTableName();

    Optional<String> pullSearchCriterion(E e);
}
