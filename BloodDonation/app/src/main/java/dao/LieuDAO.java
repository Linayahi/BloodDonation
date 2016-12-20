package dao;

import android.content.ContentValues;
import android.content.Context;

import metier.LieuDon;

/**
 * Created by lina on 13/12/16.
 */
public class LieuDAO extends DAOBase {
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

    public LieuDAO(Context pContext) {
        super(pContext);
    }

    public void addLieu(LieuDon lieu)
    {
        ContentValues value = new ContentValues();
        value.put(LieuDAO.LIEU_NOM, lieu.getNom());
        value.put(LieuDAO.LIEU_ADRESSE, lieu.getAdresse());
        value.put(LieuDAO.LIEU_LAT , lieu.getLatitude());
        value.put(LieuDAO.LIEU_LON , lieu.getLongitude());
        value.put(LieuDAO.LIEU_DESC, lieu.getDesc());

        mDb.insert(LieuDAO.LIEU_TABLE_NAME, null, value);
    }


}
