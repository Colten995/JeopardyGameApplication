package ca.on.conestogac.jeopardygameapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {


    public static final int version = 1;
    public static final String dbName = "Users.db";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT );";
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME + ";";

    public UserDatabaseHelper(Context context) {
        super(context, dbName, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
    }
    public boolean AddUser(UserAccount userAccount){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, userAccount.getUserName());
        contentValues.put(COLUMN_PASSWORD, userAccount.getUserPassword());
        Long result = sqlDb.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor GetUsers(){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDb.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
}
