package ca.on.conestogac.jeopardygameapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabaseHelper extends SQLiteOpenHelper {

    public static final int version = 1;
    public static final String dbName = "Scores.db";
    public static final String TABLE_NAME = "Scores";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "Score";

    private static final String CREATE_SCORE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SCORE + " INTEGER );";
    private static final String DROP_SCORE_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME + ";";

    public ScoreDatabaseHelper(Context context) {
        super(context, dbName, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCORE_TABLE);
    }
    public boolean AddScore(HighscoreRecord highscoreRecord){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, highscoreRecord.getUserName());
        contentValues.put(COLUMN_SCORE, highscoreRecord.getScore());
        Long result = sqlDb.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor GetScore(){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDb.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC;", null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
}
