package dao.mapper;

import dao.DAO;
import dao.Entity1;
import dao.util.DateFormater;
import dao.PG.PGDAOFactory;
import dao.DAOConnection;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.*;

/**
 * A generic mapper class that maps {@Code Entity1'<>'} objects into the format of a relational postgres database.
 *
 * The mapper assumes that the Java classes and attributes
 * have the exact same naming like the relations and their attributes!!!
 *
 * @param <K> key type of the DAO that the mapper is used in
 * @param <E> entity type that is mapped
 */
public class PGMapper<K extends Serializable, E extends Entity1<K>> implements Mapper<K, E> {

    protected final Class<K> keyType;
    protected final Class<E> entityType;
    private final DAOConnection conn;

    public PGMapper(Class<K> keyType, Class<E> entityType, DAOConnection conn) {
        this.keyType = keyType;
        this.entityType = entityType;
        this.conn = conn;
    }

    @Override
    public Class<E> entityType(){
        return this.entityType;
    }

    /**
     * Explodes an entity into an array of objects.
     *
     * @param e to be exploded
     * @return
     */
    @Override
    public Object[] explode(E e) {

        //System.out.println("type: " + entityType);
        Field[] fields = this.filterTransientFields(entityType.getDeclaredFields());

        Object[] array = new Object[fields.length];

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                array[i] = fields[i].get(e);
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }



//        System.out.println("explode: " + Arrays.toString(array));

        return array;
    }

    /**
     * Creates an object from an array of objects.
     *
     * @param e Object[] containing the attribute values for a new Object
     * @return
     */
    @Override
    public E implode(Object[] e) {
//        System.out.println("implode in mapper");

        try {

            Field[] fields = this.filterTransientFields(entityType.getDeclaredFields());

            Class[] types = new Class[fields.length];

            for (int i = 0; i < fields.length; i++) {
                types[i] = fields[i].getType();
            }
//            System.out.println("\n<PGMapper implode()> types: " + Arrays.toString(types) + "\n");
            Constructor<E> constructor = this.entityType.getDeclaredConstructor(types);

            Class[] eTypes = new Class[e.length];

            for (int i = 0; i < e.length; i++) {
                if (e[i] != null){
                    eTypes[i] = e[i].getClass();
                }
            }

//            System.out.println("<PGMapper implode() Object[e]: >" + Arrays.toString(e));
//            System.out.println("<PGMapper implode() eTypes[e]: >" + Arrays.toString(eTypes));

//            System.out.println("<PGMapper implode()> Constructor: " + constructor);

            for (int i = 0; i < e.length; i++) {
                if (e[i] != null && e[i].getClass().isAssignableFrom(java.sql.Date.class)) {
                    Date date = (Date) e[i];
//                    LocalDate local = LocalDate.of(date.getYear(), date.getMonth(), date.getYear());
                    LocalDate local = new java.sql.Date(date.getTime()).toLocalDate();
//                    System.out.println("Local: " + local);
                    e[i] = local;
                }
            }

            return constructor.newInstance(e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
            System.out.println(e1.getMessage());
        }
        return null;
    }

    private Field[] filterTransientFields(Field[] fieldsUnfiltered) {
        Field[] fields = new Field[0];
        for (int i = 0; i < fieldsUnfiltered.length; i++) {
            if (!Modifier.isTransient(fieldsUnfiltered[i].getModifiers())) {

                int oldLength = fields.length;
                fields = Arrays.copyOf(fields, oldLength + 1);
                fields[oldLength] = fieldsUnfiltered[i];
            }
        }
//        System.out.println("<PGMapper filterTransientFields()> fieldsUnfiltered: " + Arrays.toString(fieldsUnfiltered));
//        System.out.println("<PGMapper filterTransientFields()> fields: " + Arrays.toString(fields));
        return fields;
    }

    @Ignore
    private Object[] searchForNestedObjects(Object[] e, Field[] f) {

        Object[] objects = new Object[0];

        for (int i = 0; i < e.length; i++) {

            //saves foreign key entities
            if (e[i].getClass().isAssignableFrom(Entity1.class)){
                System.out.println(e[i] + " is an Object! D: You don't belong here! Go crud yourself!");


                Entity1 found = (Entity1) e[i];

                Class nestedEntityType = found.getClass();
                Class nestedKeyType = found.getKey().getClass();

                //sets foreign key value
                objects = Arrays.copyOf(objects, objects.length + 1);
                objects[objects.length-1] = found.getId();

                e[i] = found.getId(); //save is save. nested field is definitely removed from the array and replaced with id

                //saves the nested entity
                PGDAOFactory factory = new PGDAOFactory();
                DAO newDao = factory.createDao(nestedKeyType, nestedEntityType, conn);
                newDao.save(found);
            } else if( Modifier.isTransient(f[i].getModifiers()) || e[i].getClass().isAssignableFrom(Collection.class)) {
                System.out.println(e[i] + "is a Collection of Objects! D: You don't belong here! Go crud yourself!");
                //Collection of Entities
                ArrayList list = (ArrayList) e[i];

                Entity1 exampleEntity = (Entity1) list.get(1);

                Class nestedKeyType = exampleEntity.getClass();
                Class nestedEntityType = exampleEntity.getKey().getClass();

                PGDAOFactory factory = new PGDAOFactory();
                DAO newDao = factory.createDao(nestedKeyType, nestedEntityType, conn);

                for (int j = 0; j < list.size(); j++) {
                    Entity1 entity = (Entity1) list.get(j);

                    Field[] fields = this.getFields(entity);

                    for (int k = 0; k < fields.length; k++) {
                        if (fields[i].getName().equals(this.entityType.getName())){
                            //TODO Saving of nested entities
                        }
                    }

                    newDao.save(entity);
                }
                e[i] = null;
            } else if (e[i].getClass() == LocalDate.class) {
                e[i] = DateFormater.formate((LocalDate)e[i]);
            } else {
                objects = Arrays.copyOf(objects, objects.length + 1);
                objects[objects.length - 1] = e[i];
            }
        }

        return null;
    }

    private Field[] getFields(Entity1 e) {
        return e.getClass().getDeclaredFields();
    }

    /**
     * Gets the name of the relation according to the entity, in a postgres database.
     *
     * @return the table name according to the entity that is supposed to be mapped
     */
    @Override
    public String getTableName() {
        return this.entityType.getSimpleName().toLowerCase() + "s";
    }
}
