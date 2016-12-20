package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import metier.User;

/**
 * Created by lina on 13/12/16.
 */
public class UserDAO extends DAOBase{

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

    public UserDAO(Context pContext) {
        super(pContext);
    }

    public void addUser(User u)
    {
        ContentValues value = new ContentValues();
        value.put(UserDAO.USER_NOM, u.getNom());
        value.put(UserDAO.USER_PRENOM, u.getPrenom());
        value.put(UserDAO.USER_AGE, u.getAge());
        value.put(UserDAO.USER_SEXE, u.getSexe());
        value.put(UserDAO.USER_EMAIL, u.getEmail());
        value.put(UserDAO.USER_PASSWORD, u.getPassword());
        mDb.insert(UserDAO.USER_TABLE_NAME, null, value);
    }


    public User getUserbyEmail(String email)
    {
        User u = new User();
        Cursor cursor = mDb.rawQuery("select * from " + USER_TABLE_NAME + " where "+USER_EMAIL+" = ?", new String[]{email});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            u.setId(cursor.getInt(0));
            u.setNom(cursor.getString(1));
            u.setPrenom(cursor.getString(2));
            u.setAge(cursor.getInt(3));
            u.setSexe(cursor.getString(4));
            u.setEmail(cursor.getString(5));
            u.setPassword(cursor.getString(6));
            cursor.close();
        }
        return u;
    }
}
