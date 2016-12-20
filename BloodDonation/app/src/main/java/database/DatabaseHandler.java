package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lina on 13/12/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    /************************************************* TABLE USER *********************************************/

    public static final String USER_ID = "user_id";
    public static final String USER_NOM = "nom";
    public static final String USER_PRENOM = "prenom";
    public static final String USER_AGE = "age";
    public static final String USER_SEXE = "sexe";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NOM+ " TEXT, " +
                    USER_PRENOM+ " TEXT, " +
                    USER_AGE+ " INTEGER, " +
                    USER_SEXE+ " TEXT, " +
                    USER_EMAIL+ " TEXT, " +
                    USER_PASSWORD+ " TEXT);";

    public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";
    public static final String LIEU_TABLE_NAME = "LieuDon";

    /************************************************* TABLE LIEU *********************************************/


    public static final String LIEU_ID = "lieu_id";
    public static final String LIEU_NOM = "nom";
    public static final String LIEU_ADRESSE = "adresse";
    public static final String LIEU_LAT = "latitude";
    public static final String LIEU_LON = "longitude";
    public static final String LIEU_DESC = "description";

    public static final String LIEU_TABLE_CREATE =
            "CREATE TABLE " + LIEU_TABLE_NAME + " (" +
                    LIEU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LIEU_NOM+ " TEXT, " +
                    LIEU_ADRESSE+ " TEXT, " +
                    LIEU_LAT+ " REAL, " +
                    LIEU_LON+ " REAL, " +
                    LIEU_DESC+ " TEXT);";

    public static final String LIEU_TABLE_DROP = "DROP TABLE IF EXISTS " + LIEU_TABLE_NAME + ";";

    /************************************************* TABLE Event *********************************************/

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






    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(LIEU_TABLE_CREATE);
        db.execSQL(EVENT_TABLE_CREATE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(USER_TABLE_DROP);
        db.execSQL(LIEU_TABLE_DROP);
        db.execSQL(EVENT_TABLE_DROP);
        onCreate(db);
    }

}


