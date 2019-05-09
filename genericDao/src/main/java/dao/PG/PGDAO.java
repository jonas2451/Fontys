package dao.PG;

import dao.PG.connection.PgJDBC;
import dao.DAO;
import dao.Entity1;
import dao.mapper.Mapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PGDAO<K extends Serializable, E extends Entity1<K>> implements DAO<K, E> {

    private Connection conn;
    private Mapper<K, E> mapper;

    private static String GET_QUERY = "";

    public PGDAO(Mapper<K, E> mapper, Connection conn) {
        this.mapper = mapper;
        this.conn = conn;
    }

    @Override
    public Optional<E> get(String id) {

        ResultSet rs = PgJDBC.doQuery(conn, "select * from "+ mapper.getTableName() +" where id = '" + id + "';");
        //System.out.println("select * from "+ mapper.getTableName() +" where id = '" + id + "';");
        //Object[] array = null;

        try {
            Object[] array = this.makeFinalArray(rs);

            if (array.length != 0) {
                E e = mapper.implode(array);
                //System.out.println("Imploded Object inside mapper: " + e + "\n");
                //System.out.println(Optional.of(e));
                return Optional.of(e);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<E> getAll() {

        ResultSet rs = PgJDBC.doQuery(conn, "select * from " + mapper.getTableName() + ";");

        List<E> list = new ArrayList<E>();

        try {
            Object[] array = null;

            //iterating the result set and making new Objects that are added to the list
            while (rs.next()){
                array = makeFinalArray(rs);
                list.add(mapper.implode(array));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
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

        //System.out.println("result set: " + rs.getObject(1) + ", Size: " + length);

        Object[] array = new Object[length];

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            array[i-1] = rs.getObject(i);
        }
        System.out.println("Final array: " + Arrays.toString(array) + "\n");

        return array;
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public E save(E e) {
        try {

            Object[] array = this.mapper.explode(e);

            //put single quotes around every item
            for (int i = array.length -1; i >= 0; i--) {
                array[i] = "'" + array[i] + "'";
            }

            ResultSet rs = PgJDBC.doQuery(conn,
                    "insert into " + this.mapper.getTableName() + " " +
                            "values(" + Arrays.toString(array).substring(1, Arrays.toString(array).length() - 1) + ")" +
                            "on conflict (id)" +
                            "do nothing;");

        } catch (Exception ex){                                                                                         //TODO
            System.out.println("Save exception: " );
            ex.printStackTrace();
        }
        return e;
    }

    @Override
    public E update(E e) {

        Field[] fields = e.getClass().getDeclaredFields();

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

        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        String query = "update " + this.mapper.getTableName() + " set " +
                builder.toString() + " where " + e.getNaturalId() + " = '" + e.getID() + "';";

        if (PgJDBC.doQuery(conn, query) == null) return null;

        //System.out.println(query);
        return e;
    }

    @Override
    public void delete(E e) {
        PgJDBC.doQuery(conn, "delete from " + mapper.getTableName() + " where " + e.getNaturalId() + " = " + e.getID());
    }

    @Override
    public void loadNewMapper(Mapper<K, E> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Collection<E> searchFor(E e, String search) {
        ArrayList<E> list = new ArrayList();
        String query = "select * from customers where " + mapper.pullSearchCriterion(e).get() + " ilike '%" + search + "%';";
        System.out.println(query);
        try {
            ResultSet rs = PgJDBC.doQuery(conn, query);

            while (rs.next()) {
                Object[] array = makeFinalArray(rs);
                list.add(mapper.implode(array));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Connection getConn() {
        return conn;
    }
}
