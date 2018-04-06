package com.example.saidi.popularmoviesstageone;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import static org.junit.Assert.assertNotEquals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.saidi.popularmoviesstageone.db.FavoritMovieDbHelper;
import com.example.saidi.popularmoviesstageone.db.FavoritMoviesContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

/**
 * Created by saidi on 06/04/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    /* Context used to perform operations on the database and create WaitlistDbHelper */
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    /* Class reference to help load the constructor on runtime */
    private final Class mDbHelperClass = FavoritMovieDbHelper.class;

    /**
     * Because we annotate this method with the @Before annotation, this method will be called
     * before every single method with an @Test annotation. We want to start each test clean, so we
     * delete the database to do so.
     */
    @Before
    public void setUp() {
        deleteTheDatabase();
    }

    /**
     * This method tests that our database contains all of the tables that we think it should
     * contain.
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void create_database_test() throws Exception{


        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        /* We think the database is open, let's verify that here */
        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        /* This Cursor will contain the names of each table in our database */
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME + "'",
                null);

        /*
         * If tableNameCursor.moveToFirst returns false from this query, it means the database
         * wasn't created properly. In actuality, it means that your database contains no tables.
         */
        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase,
                tableNameCursor.moveToFirst());

        /* If this fails, it means that your database doesn't contain the expected table(s) */
        assertEquals("Error: Your database was created without the expected tables.",
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME, tableNameCursor.getString(0));

        /* Always close a cursor when you are done with it */
        tableNameCursor.close();
    }

    /**
     * This method tests inserting a single record into an empty table from a brand new database.
     * The purpose is to test that the database is working as expected
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void insert_single_record_test() throws Exception{

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE, "test title");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT, "false");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID, "123445");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE, "March");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "120");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT, "8");

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
               FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                null,
                testValues);

        /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
               FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from waitlist query";
        assertTrue(emptyQueryError,
                wCursor.moveToFirst());

        /* Close cursor and database */
        wCursor.close();
        dbHelper.close();
    }


    /**
     * Tests to ensure that inserts into your database results in automatically
     * incrementing row IDs.
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void autoincrement_test() throws Exception{

        System.out.println("Hello StackOverflow");

        /* First, let's ensure we have some values in our table initially */
        insert_single_record_test();

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE, "test title");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT, "false");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID, "1445");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE, "March");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "120");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT, "8");

        ContentValues testValues2 = new ContentValues();
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE, "test title");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT, "false");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID, "12344");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE, "March");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "120");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT, "8");


        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                null,
                testValues);

        /* Insert ContentValues into database and get another row ID back */
        long secondRowId = database.insert(
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                null,
                testValues2);
        System.out.println("First Id" +firstRowId);
        assertEquals("ID Autoincrement test failed!",
                firstRowId + 1, secondRowId);



    }


    /**
     * Tests that onUpgrade works by inserting 2 rows then calling onUpgrade and verifies that the
     * database has been successfully dropped and recreated by checking that the database is there
     * but empty
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void upgrade_database_test() throws Exception{

        /* Insert 2 rows before we upgrade to check that we dropped the database correctly */

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE, "test title");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT, "false");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID, "1235544");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE, "March");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "120");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT, "8");

        ContentValues testValues2 = new ContentValues();
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_TITLE, "test title");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ADULT, "false");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_ID, "1234");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_POSTER_PATH, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_BACKDROP, "jpg");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_RELEASE_DATE, "March");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, "120");
        testValues.put(FavoritMoviesContract.FavoritMovieEntry.COLUMN_MOVIE_VOTE_COUNT, "8");

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                null,
                testValues);

        /* Insert ContentValues into database and get another row ID back */
        long secondRowId = database.insert(
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                null,
                testValues2);

        dbHelper.onUpgrade(database, 0, 1);
        database = dbHelper.getReadableDatabase();

        /* This Cursor will contain the names of each table in our database */
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME + "'",
                null);

        assertTrue(tableNameCursor.getCount() == 1);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
                FavoritMoviesContract.FavoritMovieEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */

        assertFalse("Database doesn't seem to have been dropped successfully when upgrading",
                wCursor.moveToFirst());

        tableNameCursor.close();
        database.close();
    }

    /**
     * Deletes the entire database.
     */
    void deleteTheDatabase(){
        try {
            /* Use reflection to get the database name from the db helper class */
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String)f.get(null));
        }catch (NoSuchFieldException ex){
            fail("Make sure you have a member called DATABASE_NAME in the WaitlistDbHelper");
        }catch (Exception ex){
            fail(ex.getMessage());
        }

    }
}