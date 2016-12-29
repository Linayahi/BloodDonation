package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dao.EventDAO;
import dao.LieuDAO;
import dao.UserDAO;

/**
 * Created by lina on 13/12/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "db.bloodD";
    private static final int DB_VERSION = 1;
    private static DatabaseHandler sInstance;

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) { sInstance = new DatabaseHandler(context); }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.USER_TABLE_CREATE);
        db.execSQL(LieuDAO.LIEU_TABLE_CREATE);
        db.execSQL(EventDAO.EVENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(UserDAO.USER_TABLE_DROP);
        db.execSQL(LieuDAO.LIEU_TABLE_DROP);
        db.execSQL(EventDAO.EVENT_TABLE_DROP);
        onCreate(db);
    }

}


