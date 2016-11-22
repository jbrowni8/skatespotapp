package csc472.project2.browningjamesfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Browning on 11/21/16.
 */

public class JBSQLiteDatabase extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    private static final String DATABASE_NAME = "SkateSpotsDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERdETAIL = "skate_spot";

    private static final String _ID = "_id";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String COVERED = "covered";
    private static final String RATING = "rating";
    private static final String ACCESSIBLETIMES = "accessibleTimes";

    String CREATE_SKATESPOT_TABLE = "CREATE TABLE " + TABLE_USERdETAIL +
            "(" +
            _ID + " INTEGER PRIMARY KEY ," +
            TYPE + " TEXT," +
            NAME + " TEXT," +
            DESCRIPTION + " TEXT," +
            COVERED + " BOOLEAN," +
            RATING + " DOUBLE" +
            ACCESSIBLETIMES + "TEXT"+
            ")";

    private static JBSQLiteDatabase mDbHelper;

    private JBSQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static synchronized JBSQLiteDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new JBSQLiteDatabase(context.getApplicationContext());
        }
        return mDbHelper;
    }


    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String CREATE_SKATESPOT_TABLE = "CREATE TABLE " + TABLE_USERdETAIL +
                "(" +
                _ID + " INTEGER PRIMARY KEY ," +
                TYPE + " TEXT," +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                COVERED + " BOOLEAN," +
                RATING + " DOUBLE" +
                ACCESSIBLETIMES + "TEXT"+
                ")";
        db.execSQL(CREATE_SKATESPOT_TABLE);


    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERdETAIL);

            onCreate(db);
        }
    }

     /*
   Insert a  user detail into database
   */

    public void insertSkateSpot(SkateSpot spot) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAME, spot.getName());
            values.put(TYPE, spot.getType());
            values.put(ACCESSIBLETIMES, spot.getAccessibleTimes());
            values.put(RATING, spot.getRating());
            values.put(COVERED, spot.isCovered());
            values.put(DESCRIPTION, spot.getDescription());

            db.insertOrThrow(TABLE_USERdETAIL, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }


    }


}