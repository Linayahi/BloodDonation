package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import database.DatabaseHandler;
import metier.Event;

/**
 * Created by lina on 13/12/16.
 */
public class EventDAO{

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_ID = "user_id";


    public static final String LIEU_TABLE_NAME = "LieuDon";
    public static final String LIEU_ID = "lieu_id";

    public static final String EVENT_ID = "event_id";
    public static final String EVENT_USER = "user_id";
    public static final String EVENT_LIEU = "lieu_id";
    public static final String EVENT_DATE = "date";


    public static final String EVENT_TABLE_NAME = "Event";
    public static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                    EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_USER+ " INTEGER, " +
                    EVENT_LIEU+ " INTEGER, " +
                    EVENT_DATE+ " TEXT,"+
                    " FOREIGN KEY ("+EVENT_USER+") REFERENCES "+USER_TABLE_NAME+"("+USER_ID+")" +
                    " FOREIGN KEY ("+EVENT_LIEU+") REFERENCES "+LIEU_TABLE_NAME+"("+LIEU_ID+")" +
                    ");";

    public static final String EVENT_TABLE_DROP = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME + ";";

    private DatabaseHandler maBaseSQLite;
    private SQLiteDatabase db;

    public EventDAO(Context context) {
        maBaseSQLite = DatabaseHandler.getInstance(context);
    }

    //Ouverture de la table en lecture/écriture
    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    //Fermeture de l'accès à la BDD
    public void close()
    {
        db.close();
    }

    public long addEvent(Event e)
    {
        ContentValues value = new ContentValues();
        value.put(EventDAO.EVENT_USER , e.getUser_id());
        value.put(EventDAO.EVENT_LIEU, e.getLieu_id());
        value.put(EventDAO.EVENT_DATE, e.getDate());

        return db.insert(EventDAO.EVENT_TABLE_NAME, null, value);
    }
}
