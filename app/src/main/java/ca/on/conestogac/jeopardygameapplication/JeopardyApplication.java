package ca.on.conestogac.jeopardygameapplication;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class JeopardyApplication extends Application {

    private static final String DB_NAME = "db_scores";
    private static final int DB_VERSION = 2;

    private SQLiteOpenHelper helper;
    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION){
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_scores(" +
                        "user_id INTEGER, username TEXT, score INTEGER)");
            }

            /*
            This method is required to be written because sqliteopenhelper is an abstract class
            * The on upgrade function executes when the db version changes and can be used to upgrade the table
            */
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //no-op
            }
        };

        super.onCreate();
    }

    public void addGame(int score, int user_id, String username)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        //***Strings need to be enclosed in single quotes***
        String addGameStatement = "INSERT INTO tbl_scores (user_id, username, score) VALUES (" + user_id + ", '" + username + "', " + score + ")";

        db.execSQL(addGameStatement);
    }


    public Cursor getHighScores(){
        SQLiteDatabase db = helper.getReadableDatabase();
        //Lets us write the query out in SQL instead of trying to use other built-in functions to try and execute the SQL such as query(), or insert()
        Cursor cursor = db.rawQuery("SELECT user_id, username, MAX(score) FROM tbl_scores GROUP BY user_id", null);

        return cursor;
    }
    public void resetTableScores()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM tbl_scores");
    }

}
