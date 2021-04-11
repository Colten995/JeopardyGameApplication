package ca.on.conestogac.jeopardygameapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JeopardyApplication extends Application {

    private static final String DB_NAME = "db_scores";
    private static final int DB_VERSION = 1;

    private SQLiteOpenHelper helper;
    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION){
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_scores(" +
                        "user_id INT, username TEXT, score INT)");
            }

            /*
            This method is required to be written because sqliteopenhelper is an abstract class
            * The on upgrade function executes when the db version changes and can be used to upgrade the table
            */
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                // No-op
            }
        };

        super.onCreate();
    }

    public void addTransaction( int score, int user_id, String username)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("INSERT INTO tbl_scores (user_id, username, score) "
                + "VALUES(" + user_id + ", " + username + ", " + score +")");
    }

    public void resetTableStats()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM tbl_scores");
    }

}
