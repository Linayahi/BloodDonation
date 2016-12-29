package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import database.DatabaseHandler;
import metier.LieuDon;

/**
 * Created by lina on 13/12/16.
 */
public class LieuDAO {
    public static final String LIEU_ID = "lieu_id";
    public static final String LIEU_NOM = "nom";
    public static final String LIEU_ADRESSE = "adresse";
    public static final String LIEU_LAT = "latitude";
    public static final String LIEU_LON = "longitude";
    public static final String LIEU_DESC = "description";

    public static final String LIEU_TABLE_NAME = "LieuDon";
    public static final String LIEU_TABLE_CREATE =
            "CREATE TABLE " + LIEU_TABLE_NAME + " (" +
                    LIEU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LIEU_NOM+ " TEXT, " +
                    LIEU_ADRESSE+ " TEXT, " +
                    LIEU_LAT+ " REAL, " +
                    LIEU_LON+ " REAL, " +
                    LIEU_DESC+ " TEXT);";

    public static final String LIEU_TABLE_DROP = "DROP TABLE IF EXISTS " + LIEU_TABLE_NAME + ";";

    private DatabaseHandler maBaseSQLite;
    private SQLiteDatabase db;

    public LieuDAO(Context context) {
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

    public long addLieu(LieuDon lieu)
    {
        ContentValues value = new ContentValues();
        value.put(LieuDAO.LIEU_NOM, lieu.getNom());
        value.put(LieuDAO.LIEU_ADRESSE, lieu.getAdresse());
        value.put(LieuDAO.LIEU_LAT , lieu.getLatitude());
        value.put(LieuDAO.LIEU_LON , lieu.getLongitude());
        value.put(LieuDAO.LIEU_DESC, lieu.getDesc());

       return db.insert(LieuDAO.LIEU_TABLE_NAME, null, value);
    }


}
