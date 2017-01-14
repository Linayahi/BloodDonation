package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dao.EventDAO;
import dao.LieuDAO;
import dao.UserDAO;
import metier.LieuDon;

/**
 * Created by lina on 13/12/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "db.bloodD";
    private static final int DB_VERSION = 1;
    private static DatabaseHandler sInstance;
    private SQLiteDatabase dbase;

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) { sInstance = new DatabaseHandler(context); }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        db.execSQL(UserDAO.USER_TABLE_CREATE);
        db.execSQL(LieuDAO.LIEU_TABLE_CREATE);
        db.execSQL(EventDAO.EVENT_TABLE_CREATE);
        addLieux();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(UserDAO.USER_TABLE_DROP);
        //db.execSQL(LieuDAO.LIEU_TABLE_DROP);
        //db.execSQL(EventDAO.EVENT_TABLE_DROP);
       // onCreate(db);

        switch(oldVersion)
        {
            case 1: {
            }
            case 2: {
            }
            case 3: {
            }
        }
    }

    public void addLieux(){

        LieuDon l1=new LieuDon("Hôpital Pitié-Salpétrière","12 rue Bruant Ou 47 Bd Hôpital - Pavillon Laveran 75013 Paris",48.8399391,2.3617921,"du lundi au samedi de 9h à 15h30");
        this.addLieu(l1);

        LieuDon l2=new LieuDon("Hôpital Saint-Louis","38 rue Bichat 75010 Paris",48.872110,2.367963,"lundi 8h-16h / du mardi au vendredi 8h-18h / samedi 8h-15h30");
        this.addLieu(l2);

        LieuDon l3=new LieuDon("Saint-Antoine Crozatier","21 rue Crozatier 75012 Paris",48.850815,2.381706,"lundi 8h-16h / du mardi au vendredi 8h-18h / samedi 8h-15h30");
        this.addLieu(l3);

        LieuDon l4=new LieuDon("Site de Prélèvement de la Trinité","55 rue de Châteaudun 75009 Paris",48.881146,2.333655,"lundi 8h30-16h / du mardi au vendredi 8h30-18h / samedi 8h30-16h");
        this.addLieu(l4);

        LieuDon l5=new LieuDon("EFS Cabanel","6 rue Alexandre Cabanel 75015 Paris",48.849159,2.302041,"lundi 8h-16h / mardi, jeudi, vendredi 8h-18h / mercredi 7h30-18h / samedi 8h-15h30");
        this.addLieu(l5);

    }

    public void addLieu(LieuDon lieu)
    {
        ContentValues value = new ContentValues();
        value.put(LieuDAO.LIEU_NOM, lieu.getNom());
        value.put(LieuDAO.LIEU_ADRESSE, lieu.getAdresse());
        value.put(LieuDAO.LIEU_LAT , lieu.getLatitude());
        value.put(LieuDAO.LIEU_LON , lieu.getLongitude());
        value.put(LieuDAO.LIEU_DESC, lieu.getDesc());
        dbase.insert(LieuDAO.LIEU_TABLE_NAME, null, value);
    }


}


