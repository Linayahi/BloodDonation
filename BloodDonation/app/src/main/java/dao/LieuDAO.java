package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import metier.LieuDon;

/**
 * Created by lina on 13/12/16.
 * Cette classe contient les méthodes qui permettent de gérer la table LieuDon
 *
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


    /**
     * Cette fonction prend en entrée un LieuDon et l'insère en BD
     * @param lieu
     * @return l'id du lieu crée
     */
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

    /**
     * Cette fonction permet de récupérer la liste de tous les LieuDon
     * @return
     */
    public List<LieuDon> getAllLieux() {
        List<LieuDon> lieuList = new ArrayList<LieuDon>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + LIEU_TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LieuDon lieu = new LieuDon();
                lieu.setId(Integer.parseInt(cursor.getString(0)));
                lieu.setNom(cursor.getString(1));
                lieu.setAdresse(cursor.getString(2));
                lieu.setLatitude(Double.parseDouble(cursor.getString(3)));
                lieu.setLongitude(Double.parseDouble(cursor.getString(4)));
                lieu.setDesc(cursor.getString(5));
                lieuList.add(lieu);
            } while (cursor.moveToNext());
        }

        return lieuList;
    }


    /**
     * Cette fonction récupère les détails d'un LieuDon à partir de son nom
     * @param nom
     * @return
     */
    public LieuDon getLieuByName(String nom)
    {
        Cursor cursor = db.rawQuery("select * from " + LIEU_TABLE_NAME + " where "+LIEU_NOM+" = ? " , new String[]{nom});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            LieuDon lieu = new LieuDon();
            lieu.setId(Integer.parseInt(cursor.getString(0)));
            lieu.setNom(cursor.getString(1));
            lieu.setAdresse(cursor.getString(2));
            lieu.setLatitude(Double.parseDouble(cursor.getString(3)));
            lieu.setLongitude(Double.parseDouble(cursor.getString(4)));
            lieu.setDesc(cursor.getString(5));
            return lieu;
        }
        else
        {
            cursor.close();
            return null;
        }
    }

}
