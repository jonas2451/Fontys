package dao.REST;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Some java types are not available in either the json library or produced the wrong kind of (de)serialisation.
 * This is the case for LoacalDate, from which we want ISO8601, YYYY-MM-DD e.g. 2019-04-17.
 * 
 * The approach is to have a converter that translates in two ways.
 * 
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class LocalDateJsonAdapter implements JsonSerializer<LocalDate>,
        JsonDeserializer<LocalDate> {

    public LocalDateJsonAdapter() {
    }

    @Override
    public JsonElement serialize( LocalDate src, Type typeOfSrc,
            JsonSerializationContext context ) {
        return new JsonPrimitive( src.toString() );
    }

//    @Override
//    public LocalDate deserialize( JsonElement json, Type typeOfT,
//            JsonDeserializationContext context ) throws JsonParseException {
//
////        {"year":2018,"month":10,"day":11}
////         0        1            2       3
//        String resultDate=json.toString();
//        String[] splitDate=resultDate.split(":");
//        String year=splitDate[1].split(",")[0];
//        String month=splitDate[2].split(",")[0];
//        String dates=splitDate[3].split("}")[0];
//        if(month.length()<2)
//            month='0'+month;
//        if(dates.length()<2)
//            dates='0'+dates;
//
//
//        return LocalDate.parse( year+'-'+ month+'-'+dates);
//    }

    @Override
    public LocalDate deserialize( JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context ) throws JsonParseException {

        return LocalDate.parse( json.getAsString() );
    }
}
