package dao.PG;

import dao.*;
import dao.PG.connection.PgJDBC;
import dao.mapper.Mapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A DAO implementation, compatible with a relational postgres database.
 *
 * @param <K> Key of an entity
 * @param <E> Type of an entity
 */
public class PGDAO<K extends Serializable, E extends Entity1<K>> implements DAO<K, E> {

    private Mapper<K, E> mapper;
    private DAOConnection connection;

    public PGDAO(Mapper<K, E> mapper, DAOConnection conn) {
        this.mapper = mapper;
        this.connection = conn;
//        Logger.getLogger(this.getClass().getName()).setLevel(Level.OFF);
    }

    @Override
    public Optional<E> get(K key) {

        Entity1 helperEntity = this.makeHelperObject();
        try (Connection conn = connection.connect().getConnection()) {
            ResultSet rs = PgJDBC.doQuery(conn, "select * from " + mapper.getTableName() + " where " + helperEntity.getNaturalId() + " = '" + key + "';");
//        System.out.println("select * from "+ mapper.getTableName() +" where " + helperEntity.getNaturalId() + " = '" + key + "';");

            rs.next();
            Object[] array = this.makeFinalArray(rs);

            if (array.length != 0) {
                E e = mapper.implode(array);
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Successfully got: " + e);
                return Optional.of(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Request failed. No such record in the database?", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<E>();

        try (Connection conn = connection.connect().getConnection()) {
            ResultSet rs = PgJDBC.doQuery(conn, "select * from " + mapper.getTableName() + ";");

            Object[] array = null;

            /*iterating the result set and making new Objects that are added to the list*/
            while (rs.next()) {
                array = makeFinalArray(rs);
                list.add(mapper.implode(array));
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Request failed. No such record in the database?", ex);
        }
        return list;
    }

    /**
     * Creates a helper object to use the methods provided by the Entity1 interface.
     *
     * Requires an empty constructor!!!
     * @return helper object of the entity type
     * @since V3.8
     */
    private Entity1 makeHelperObject() {
        Entity1 helperEntity = null;
        try {
            helperEntity = mapper.entityType().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "There is no default / empty constructor found for: " + mapper.entityType(), ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "The default constructor could not be accessed. Please check the modifiers for: " + mapper.entityType(), ex);
        }
        return helperEntity;
    }

    /**
     * Makes an array from {@code ResultSet rs} an result set given as parameter.
     *
     * @param rs a result set which is the result of a PgJDBC {@code PgJDBC.doQuery()}
     * @return  an array for the the {@code mapper.implode} method
     * @throws SQLException when {@code next()} fails
     */
    private Object[] makeFinalArray(ResultSet rs) throws SQLException, NullPointerException{

        int length = rs.getMetaData().getColumnCount();

        Object[] array = new Object[length];

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            array[i-1] = rs.getObject(i);
        }
        return array;
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public E save(E e) {
        Optional<E> optional = Optional.empty();


        try (Connection conn = connection.connect().getConnection()) {

            Object[] array = this.mapper.explode(e);

            //put single quotes around every item
            for (int i = array.length -1; i >= 0; i--) {
                if (array[i] == null){
                    array[i] = array[i];
                } else {
                    array[i] = "'" + array[i] + "'";
                }
            }

            ResultSet rs = PgJDBC.doQuery(conn,
                    "insert into " + this.mapper.getTableName() + " " +
                            "values(" + Arrays.toString(array).substring(1, Arrays.toString(array).length() - 1) + ")" +
                            "on conflict (" + e.getNaturalId() + ") " +
                            "do nothing;");

        optional = this.get(e.getId());
        } catch (SQLException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Saving has failed.", ex);
        }
        if (optional.isPresent()) {
            E newE = optional.get();
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Saving successful! Saved: " + newE);
            return newE;
        } else {
            throw new DAOException("No object has been saved!");
        }
    }

    @Override
    public E update(E e) {

        Field[] fields = e.getClass().getDeclaredFields();

        /*Builds the values of the query needed for an update statement*/
        StringBuilder builder = new StringBuilder(fields.length);
        try {

            for (int i = 0; i < fields.length - 1; i++) {
                fields[i].setAccessible(true);
                builder.append(fields[i].getName());
                builder.append(" = '");
                builder.append(fields[i].get(e));
                builder.append("', ");
            }
            fields[fields.length-1].setAccessible(true);
            builder.append(fields[fields.length-1].getName());
            builder.append(" = '");
            builder.append(fields[fields.length-1].get(e));
            builder.append("' ");

        } catch (IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error whilst reflecting " + e.getClass() + " fields.", ex);
        }

        String query = "update " + this.mapper.getTableName() + " set " +
                builder.toString() + " where " + e.getNaturalId() + " = '" + e.getId() + "';";

        try (Connection conn = connection.connect().getConnection()) {
            PgJDBC.doQuery(conn, query);
        } catch (SQLException ex) {
            return this.save(e);
        }
        return e;
    }

    @Override
    public void delete(E e) {
        try (Connection conn = connection.connect().getConnection()) {
            PgJDBC.doQuery(conn, "delete from " + mapper.getTableName() + " where " + e.getNaturalId() + " = '" + e.getId() + "';");
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Delete has failed. ", ex);
            throw new DAOException("Delete has failed. ", ex);
        }
    }

    @Override
    public Collection<E> getByColumnValue(String column, String value) {
//        System.out.println("<PGDAO getByColumnValue()> value: " + value);
        String query;

        if (!value.equals(" ")) {
            query = "select * from " + mapper.getTableName().toLowerCase() + " where " + column + " ilike '" + value + "%';";
        } else {
            query = "select * from " + mapper.getTableName().toLowerCase() + ";";
        }

        ArrayList<E> list = new ArrayList<E>();

//        System.out.println("<PGDAO getByColumnValue()> query: " + query);
        try(Connection conn = connection.connect().getConnection()) {
            ResultSet rs = PgJDBC.doQuery(conn, query);

            while (rs.next()) {
                Object[] array = makeFinalArray(rs);
                list.add(mapper.implode(array));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("No results for this column value!");
        }
        return list;
    }
}
