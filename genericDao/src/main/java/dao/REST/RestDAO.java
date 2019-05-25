package dao.REST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import dao.DAO;
import dao.DAOException;
import dao.Entity1;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A DAO that talks to a rest API that  provides GET, POST, PUT, and DELETE operations.
 *
 * @since V3.4
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 * @author Jonas Terschlüsen {@code j.terschlusen@student.fontys.nl}
 * @param <K> key type
 * @param <E> entity
 */
public class RestDAO<K extends Serializable, E extends Entity1<K>> implements DAO<K, E> {

    private final String baseUrl;
    private final Class<E> type;
    private final BiFunction<URL, String, HttpURLConnection> configurator;

    /**
     * Create a DAO connection to url  for entity type and configured by a configurator.
     * @param baseUrl to connect to
     * @param type to fetch or save
     * @param aConfigurator sets headers and a like.
     */
    RestDAO(
            String baseUrl, Class<E> type,
            BiFunction<URL, String, HttpURLConnection> aConfigurator ) {
        this.configurator = aConfigurator;
        //this.baseUrl = baseUrl.endsWith( "/" ) ? baseUrl : baseUrl + '/' + type.getName();
        if (baseUrl.endsWith("/")) {
            this.baseUrl = baseUrl + type.getSimpleName().toLowerCase();
        } else {
            this.baseUrl = baseUrl + "/" + type.getSimpleName().toLowerCase();
        }
        this.type = type;
        //DEBUGGIN
        System.out.println("type: " + type);
        System.out.println("base url: " + this.baseUrl);
    }

    private final String USER_AGENT = "Mozilla/5.0";
    private static final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LocalDate .class, new LocalDateJsonAdapter() );

    public static GsonBuilder gsonBuilder() {
        return gsonBuilder;
    }

    @Override
    public Optional<E> get( K key ) {
        Gson gson = gsonBuilder.create();
        String eLoc = baseUrl + key;
        try {
            HttpURLConnection con = getURL( eLoc );
            return readEntity( con, gson );
        } catch ( JsonIOException | JsonSyntaxException | IOException ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, null,
                    ex );
            throw new DAOException( ex.getMessage(), ex );
        }
    }

    private HttpURLConnection getURL(String eLoc ) throws ProtocolException, MalformedURLException, IOException {
        URL url = new URL( eLoc );
        return configurator.apply( url, "GET" );
    }

    private Optional<E> readEntity( HttpURLConnection con, Gson gson ) throws JsonSyntaxException, JsonIOException, IOException {
        Optional<E> result = Optional.empty();
        try (
                InputStream inputStream = con.getInputStream();
                Reader reader = new InputStreamReader( inputStream );
                )
        {
            E entity = gson.fromJson( reader, type );
            if ( entity != null ) {
                result = Optional.of( entity );
            }
        }
        return result;
    }

    @Override
    public Collection<E> getAll() {
        try {
            URL url = new URL( baseUrl );

            Type typeToken = TypeToken.getParameterized( ArrayList.class, type ).getType();
            System.out.println("<RestDAO getAll()> type token: " + typeToken);
            Reader reader = new InputStreamReader( url.openConnection().getInputStream(), StandardCharsets.UTF_8 );
            Gson gson = gsonBuilder.create();
            List<E> p = gson.fromJson( reader, typeToken );
            return p;
        } catch ( JsonIOException | JsonSyntaxException | IOException ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, null, ex );
            System.out.println("<RestDAO getAll()> Log: " + RestDAO.class.toGenericString());
            throw new DAOException( ex.getMessage(), ex );
        }
    }

    private HttpURLConnection postput( String eLoc, int bodySize, String verb ) throws ProtocolException, MalformedURLException, IOException {
        URL url = new URL( eLoc );
        HttpURLConnection con = configurator.apply( url, verb );
        setJsonHeaders( con, bodySize );

        return con;
    }

    private void setJsonHeaders( HttpURLConnection con, int bodySize ) {
        con.setInstanceFollowRedirects( false );
        basicJsonHeaders( con );
        con.setRequestProperty( "Content-Length", "" + bodySize );
    }

    private HttpURLConnection delete( String baseUrl, K key ) throws Exception {
        baseUrl = baseUrl + "/";
        URL url = new URL( baseUrl + key );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        basicJsonHeaders( con );
        con.setRequestMethod( "DELETE" );
        return con;
    }

    private void basicJsonHeaders( HttpURLConnection con ) {
    }

    @Override
    public E save( E e ) {
        E result = null;
        try {
            Gson gson = gsonBuilder.create();
            String toJson = gson.toJson( e, type );
            int length = toJson.length();
                System.out.println("<RestDAO save()> ToJson: " + toJson);
                System.out.println("<RestDAO save()> ToJson.length(): " + length);
            HttpURLConnection con = postput( baseUrl, length, "POST" );
                System.out.println("<RestDAO save()> conn: " + con);
            result = createOrUpdate( con, toJson, gson );
        } catch ( Exception ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, "No result from backend", ex );
            throw new DAOException( ex.getMessage(), ex );
        }
        return result;
    }

    private E createOrUpdate( HttpURLConnection con, String toJson, Gson gson ) throws Exception {
        E result;

        System.out.println(con.getOutputStream());

        try ( OutputStream out = con.getOutputStream();
                DataOutputStream wr = new DataOutputStream( out ); ) {
            wr.write( toJson.getBytes() );
            wr.flush();
            wr.close();
                System.out.println("<RestDAO createOrUpdate()> ---- made it to sending the post request ----");
            try ( BufferedReader br = new BufferedReader( new InputStreamReader( con.getInputStream(), "utf-8" ) ) ) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ( ( responseLine = br.readLine() ) != null ) {
                    response.append( responseLine.trim() );
                }
                System.out.println( "response=" + response.toString() );
                result = gson.fromJson( response.toString(), type );
            }
        }
        return result;
    }

    @Override
    public E update( E e ) {
        E result = null;
        try {
            Gson gson = gsonBuilder.create();
            String toJson = gson.toJson( e, type );
            int length = toJson.length();
            HttpURLConnection con = postput( baseUrl, length, "PUT" );
            result = createOrUpdate( con, toJson, gson );
        } catch ( Exception ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, null, ex );
            throw new DAOException( ex.getMessage(), ex );
        }
        return result;
    }

    @Override
    public void delete( E e ) {
        try {
            K key = e.getId();
            HttpURLConnection con = delete( baseUrl, key );
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            if ( responseCode != 200 ) {
                throw new DAOException( "delete failed for entity " + e );
            }
        } catch ( Exception ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, null, ex );
            throw new DAOException( ex.getMessage(), ex );
        }
    }

    @Override
    public Collection<E> getByColumnValue(String column, String value) {

        if (value.isEmpty()) value = "%20";

        Gson gson = gsonBuilder.create();
        String eLoc = baseUrl + "/" + column + "/" + value;
        try {
            HttpURLConnection con = getURL( eLoc );

            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new DAOException("Error code: " + responseCode);
            }

            Type typeToken = TypeToken.getParameterized( ArrayList.class, type ).getType();
            Reader reader = new InputStreamReader( con.getInputStream(), StandardCharsets.UTF_8 );
            List<E> p = gson.fromJson( reader, typeToken );

            return p;
        } catch ( JsonIOException | JsonSyntaxException | IOException ex ) {
            Logger.getLogger( RestDAO.class.getName() ).log( Level.SEVERE, "getByColumnValue() failed! :(",
                    ex );
            throw new DAOException( ex.getMessage(), ex );
        }
    }
}
