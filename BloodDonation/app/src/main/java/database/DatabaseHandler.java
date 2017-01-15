package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        addLieux();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    /**
     * Remplir la base de données avec des LieuDon
     */
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
        LieuDon l6=new LieuDon("Hôpital Robert Debré","48 Boulevard Sérurier - Point Jaune - Niveau -2 75019 Paris",48.879056,2.401997,"Pour des raisons humaines et matérielles, fermeture temporaire de la cabine Robert Debré.");
        this.addLieu(l6);
        LieuDon l7=new LieuDon("Hôpital Bichat","46 rue Henri Huchard - Secteur Claude Bernard 75018 Paris ",48.899394,2.330896,"du lundi au vendredi 10h-16h30 / samedi 8h30-15h");
        this.addLieu(l7);
        LieuDon l8=new LieuDon("Hôpital Européen Georges Pompidou","20 rue Leblanc - Rdc du Hall A 75015 Paris",48.839999,2.272933,"lundi, mercredi, vendredi, samedi 9h-15h30 / mardi, jeudi 10h30-17h");
        this.addLieu(l8);
        LieuDon l9=new LieuDon("Hôpital Beaujon","100 bld du Général Leclerc Secteur Bleu - Porte 15 - Niveau -1 92110 Clichy ",48.908611,2.307707,"du lundi au samedi 8h30-15h");
        this.addLieu(l9);
        LieuDon l10=new LieuDon("Hôpital Avicenne","125 rue de Stalingrad - Secteur Orange - Porte 93009 Bobigny",48.914716, 2.423900,"du lundi au samedi : 9h-15h30");
        this.addLieu(l10);
        LieuDon l11=new LieuDon("Site Créteil l'Echat","1 Voie Félix Eboué 94010 Créteil",48.797313,2.449398,"du lundi au vendredi 9h-15h30 / samedi 8h30-15h");
        this.addLieu(l11);
        LieuDon l12=new LieuDon("Site de Prélèvement du Chesnay","2 Rue Jean-Louis Forain 78153 Le Chesnay",48.836211,2.122625,"du lundi au vendredi : 8h30-15h / Samedi : 8h -14h30");
        this.addLieu(l12);
        LieuDon l13=new LieuDon("Maison du Don","2 av du Canada Courtaboeuf 2 91943 Les Ulis",48.684867,2.195332,"du lundi au vendredi ainsi que les 1er et 3e samedis du mois de 8h30 à 15h");
        this.addLieu(l13);
        LieuDon l14=new LieuDon("Poissy","9 rue Champ Gaillard 78300 Poissy",48.919335,2.022289,"lundi, mardi, mercredi, vendredi 9h-15h30 / jeudi 12h30-19h / samedi 8h30-15h");
        this.addLieu(l14);
        LieuDon l15=new LieuDon("Courcouronnes","Rue du Pont Amar Quartier du Canal 91080 Courcouronnes",48.632201,2.414682,"du lundi au vendredi 9h-15h30 / samedi (semaines paires) 9h-15h30");
        this.addLieu(l15);
        LieuDon l16=new LieuDon("Cergy Pontoise","Avenue de l'Ile de France 95301 Cergy Pontoise",49.063906,2.093888,"du lundi au samedi : 9h-15h30");
        this.addLieu(l16);
        LieuDon l17=new LieuDon("Meaux","Centre Hospitalier de Meaux 6/8 rue Saint Fiacre 77100 Meaux",48.959785,2.887759,"Mardi: 14h-16h / Mercredi & vendredi: 9h-12h30 & 14h-16h / Jeudi: 12h30-19h/ 1er & 3e samedis du mois: 8h30-15h");
        this.addLieu(l17);
        LieuDon l18=new LieuDon("Melun","Centre Hospitalier Marc Jacquet 8 rue de Vaux le Pénil 77011 Melun",48.539728,2.673334,"du lundi au vendredi : 8h30-15h / Samedi (semaines impaires) : 8h30-15h");
        this.addLieu(l18);
        LieuDon l19=new LieuDon("Luisant","5 rue des Chênes 28600 Luisant",48.426031,1.459069,"");
        this.addLieu(l19);
        LieuDon l20=new LieuDon("Evreux","Hôpital du Cambolle Rue Léon Schwartzenberg 27000 Evreux",49.035365,1.114413,"Le jeudi de 10h à 18h non-stop");
        this.addLieu(l20);


    }

    /**
     * Cette fonction permet d'ajouter un LieuDon passé en paramètre
     * @param lieu
     */
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


