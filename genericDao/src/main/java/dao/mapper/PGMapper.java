package dao.mapper;

import dao.DAO;
import dao.Entity1;
import dao.PG.DateFormater;
import dao.PG.PGDAOFactory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class PGMapper<K extends Serializable, E extends Entity1<K>> implements Mapper<K, E> {

    protected final Class<K> keyType;
    protected final Class<E> entityType;
    private final Connection conn;

    public PGMapper(Class<K> keyType, Class<E> entityType, Connection conn) {
        this.keyType = keyType;
        this.entityType = entityType;
        this.conn = conn;
    }

    @Override
    public Class<E> entityType(){
        return this.entityType;
    }

    @Override
    public Object[] explode(E e) {

        //System.out.println("type: " + entityType);
        Field[] fields = entityType.getDeclaredFields();

        Object[] array = new Object[fields.length];

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                array[i] = fields[i].get(e);
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }



        System.out.println("explode: " + Arrays.toString(array));

        return array;
    }

    @Override
    public E implode(Object[] e) {
        System.out.println("implode in mapper");
        //System.out.println("entity type: " + entityType);

        //E entity = this.entityType.getClass().newInstance();

        try {

            Field[] fields = entityType.getDeclaredFields();

            Class[] types = new Class[fields.length];

            for (int i = 0; i < fields.length; i++) {
//                types[i] = fields[i].getGenericType().getTypeName().getClass(); // IS ALWAYS STRING /.-
                types[i] = fields[i].getType();
            }
//            System.out.println("\n types: " + Arrays.toString(types) + "\n");
            Constructor<E> constructor = this.entityType.getDeclaredConstructor(types);

            System.out.println("Constructor: " + constructor);
            System.out.println("E e before: " + Arrays.toString(e));

            //test if database returned a localdate
            System.out.println("type before: " + e[1].getClass());

            for (int i = 0; i < e.length; i++) {
                if (e[i].getClass().isAssignableFrom(java.sql.Date.class)) {
                    Date date = (Date) e[i];
                    LocalDate local = new java.sql.Date(date.getTime()).toLocalDate();
                    System.out.println("Local: " + new java.sql.Date(date.getTime()).toLocalDate());
                    e[i] = local;
                }
            }
            System.out.println("E e after: " + Arrays.toString(e));
            System.out.println("type after: " + e[1].getClass());






            return constructor.newInstance(e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public Object[] searchForNestedObjects(Object[] e) {
        for (int i = 0; i < e.length; i++) {
            if (e[i].getClass().isAssignableFrom(Entity1.class)){
                System.out.println(e[i] + " is an Object! D: You don't belong here! Go away!");

                Entity1 o = (Entity1) e[i];

                Class nestedEntityType = o.getClass();
                Class nestedKeyType = o.getKey().getClass();



//                PGDAOFactory<Serializable, Entity1<Serializable>> factory = new PGDAOFactory<Serializable, Entity1<Serializable>>(nestedKeyType, nestedEntityType, conn);
                PGDAOFactory factory = new PGDAOFactory();
                DAO newDao = factory.createDao(nestedKeyType, nestedEntityType, conn);
                newDao.save(o);
            } else if (e[i].getClass() == LocalDate.class) e[i] = DateFormater.formate((LocalDate)e[i]);
        }
        return null;
    }

    @Override
    public String getTableName() {
        return this.entityType.getSimpleName().toLowerCase() + "s";
    }

    @Override
    public Optional<String> pullSearchCriterion(E e) {
        return e.getSearchCriterion();
    }
}
