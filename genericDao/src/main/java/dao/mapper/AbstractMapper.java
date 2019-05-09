package dao.mapper;

public abstract class AbstractMapper<K, E> implements Mapper<K, E> {
    protected final Class<K> keyType;
    protected final Class<E> entityType;

    public AbstractMapper(Class<K> keyType, Class<E> entityType) {
        this.keyType = keyType;
        this.entityType = entityType;
    }

    @Override
    public Class<E> entityType(){
        return this.entityType;
    }

    @Override
    public String getTableName() {
        System.out.println("Table Name: " + entityType.getSimpleName());
        return this.entityType.getSimpleName().toLowerCase() + "es";
    }
}
