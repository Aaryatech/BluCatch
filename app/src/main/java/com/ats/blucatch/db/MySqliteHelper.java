package com.ats.blucatch.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ats.blucatch.bean.Expense;
import com.ats.blucatch.bean.Fish;
import com.ats.blucatch.bean.Transaction;
import com.ats.blucatch.bean.TripDisplay;

import java.util.ArrayList;

/**
 * Created by maxadmin on 11/9/17.
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DataDB";

    private static final String TABLE_FISH = "fish";
    private static final String TABLE_TRIP_DISPLAY = "tripDisplay";
    private static final String TABLE_EXPENSES = "expenses";
    private static final String TABLE_TRANSACTION = "transaction";

    private static final String FISH_COLUMN_ID = "Id";
    private static final String FISH_COLUMN_FID = "fishId";
    private static final String FISH_COLUMN_NAME = "fishName";
    private static final String FISH_COLUMN_TYPE = "fishType";
    private static final String FISH_COLUMN_IS_USED = "fishIsUsed";
    private static final String FISH_COLUMN_MIN_RATE = "fishMinRate";
    private static final String FISH_COLUMN_MAX_RATE = "fishMaxRate";
    private static final String FISH_COLUMN_SIZE = "fishSize";

    private static final String TRIP_COLUMN_TRIP_AUTO_ID = "id";
    private static final String TRIP_COLUMN_TRIP_ID = "tripId";
    private static final String TRIP_COLUMN_BOAT_ID = "boatId";
    private static final String TRIP_COLUMN_START_DATE = "tripStartDate";
    private static final String TRIP_COLUMN_END_DATE = "tripEndDate";
    private static final String TRIP_COLUMN_TANDEL_ID = "tandelId";
    private static final String TRIP_COLUMN_AUCTIONER_ID = "auctionerId";
    private static final String TRIP_COLUMN_TRIP_STAFF = "tripStaff";
    private static final String TRIP_COLUMN_TRIP_SETTLED = "tripSettled";
    private static final String TRIP_COLUMN_TRIP_STATUS = "tripStatus";
    private static final String TRIP_COLUMN_TRIP_DELETE = "tripDelete";
    private static final String TRIP_COLUMN_BOAT_NAME = "boatName";
    private static final String TRIP_COLUMN_TANDEL_NAME = "tandelName";
    private static final String TRIP_COLUMN_AUCTIONER_NAME = "auctionerName";
    private static final String TRIP_COLUMN_CO_ID = "coId";
    private static final String TRIP_COLUMN_ENTER_DATE = "enterDate";
    private static final String TRIP_COLUMN_ENTER_BY = "enterBy";


    private static final String EXPENSES_COLUMN_ID = "id";
    private static final String EXPENSES_COLUMN_EXP_ID = "expId";
    private static final String EXPENSES_COLUMN_NAME = "expName";
    private static final String EXPENSES_COLUMN_TYPE = "expType";
    private static final String EXPENSES_COLUMN_ACC_TYPE = "expAccType";
    private static final String EXPENSES_COLUMN_ENTRY_TYPE = "expEntryType";
    private static final String EXPENSES_COLUMN_PHOTO_REQ = "expIsPhotoReq";
    private static final String EXPENSES_COLUMN_ACCESS_TO = "expAccessTo";
    private static final String EXPENSES_COLUMN_IS_USED = "expIsUsed";
    private static final String EXPENSES_COLUMN_CO_ID = "coId";
    private static final String EXPENSES_COLUMN_ENTER_DATE = "expEnterDate";
    private static final String EXPENSES_COLUMN_ENTER_BY = "expEnterBy";
    private static final String EXPENSES_COLUMN_COMBO_VALUES = "comboValues";

    private static final String TRANSACTION_COLUMN_AUTO_ID = "autoId";
    private static final String TRANSACTION_COLUMN_SEASON_ID = "seasonId";
    private static final String TRANSACTION_COLUMN_TR_DATE = "trDate";
    private static final String TRANSACTION_COLUMN_TR_SENDER_ID = "senderId";
    private static final String TRANSACTION_COLUMN_TR_RECEIVER_ID = "receiverId";
    private static final String TRANSACTION_COLUMN_TR_AMOUNT = "amount";
    private static final String TRANSACTION_COLUMN_TR_TYPE = "trType";
    private static final String TRANSACTION_COLUMN_TRIP_ID = "tripId";
    private static final String TRANSACTION_COLUMN_EXP_TYPE = "expType";
    private static final String TRANSACTION_COLUMN_EXP_RATE = "expRate";
    private static final String TRANSACTION_COLUMN_EXP_QTY = "expQty";
    private static final String TRANSACTION_COLUMN_EXP_TOTAL = "expTotal";
    private static final String TRANSACTION_COLUMN_EXP_ADDED_AMOUNT = "expAddedAmt";
    private static final String TRANSACTION_COLUMN_EXP_ADDED_AMT_TITLE = "expAddedAmtTitle";
    private static final String TRANSACTION_COLUMN_GEN_REMARK = "remark";
    private static final String TRANSACTION_COLUMN_PHOTO_1 = "photo1";
    private static final String TRANSACTION_COLUMN_PHOTO_2 = "photo2";
    private static final String TRANSACTION_COLUMN_PHOTO_3 = "photo3";
    private static final String TRANSACTION_COLUMN_LOGIN_USER_ID = "loginUserId";
    private static final String TRANSACTION_COLUMN_LOGIN_DATE = "loginDate";
    private static final String TRANSACTION_COLUMN_IS_AUTH = "isAuthorised";
    private static final String TRANSACTION_COLUMN_AUTH_REMARK = "authRemark";
    private static final String TRANSACTION_COLUMN_AUTH_USER_ID = "authUserId";
    private static final String TRANSACTION_COLUMN_AUTH_DATE = "authDate";
    private static final String TRANSACTION_COLUMN_REJECT_REMARK = "rejRemark";
    private static final String TRANSACTION_COLUMN_REJECT_USER_ID = "rejUserId";
    private static final String TRANSACTION_COLUMN_REJECT_DATE = "rejDate";
    private static final String TRANSACTION_COLUMN_EXP_ID = "expId";
    private static final String TRANSACTION_COLUMN_CO_ID = "coId";
    private static final String TRANSACTION_COLUMN_ENTER_DATE = "enterDate";
    private static final String TRANSACTION_COLUMN_ENTER_BY = "enterBy";
    private static final String TRANSACTION_COLUMN_IS_SYNC = "isSync";


    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryFish = "CREATE TABLE " + TABLE_FISH + "(" + FISH_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FISH_COLUMN_FID + " INTEGER, " + FISH_COLUMN_NAME + " TEXT, " + FISH_COLUMN_TYPE + " TEXT, " + FISH_COLUMN_IS_USED + " INTEGER, " + FISH_COLUMN_SIZE + " TEXT, " + FISH_COLUMN_MIN_RATE + " TEXT, " + FISH_COLUMN_MAX_RATE + " TEXT);";
        Log.e("MySqliteHelper : ", " FISH TABLE CREATION : " + queryFish);
        db.execSQL(queryFish);

        String queryTrip = "CREATE TABLE " + TABLE_TRIP_DISPLAY + "(" + TRIP_COLUMN_TRIP_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TRIP_COLUMN_TRIP_ID + " INTEGER, " + TRIP_COLUMN_BOAT_ID + " INTEGER, " + TRIP_COLUMN_START_DATE + " TEXT, " + TRIP_COLUMN_END_DATE + " TEXT, " + TRIP_COLUMN_TANDEL_ID + " TEXT, " + TRIP_COLUMN_AUCTIONER_ID + " TEXT, " + TRIP_COLUMN_TRIP_STAFF + " TEXT, " + TRIP_COLUMN_TRIP_SETTLED + " INTEGER, " + TRIP_COLUMN_TRIP_STATUS + " TEXT, " + TRIP_COLUMN_TRIP_DELETE + " INTEGER, " + TRIP_COLUMN_BOAT_NAME + " TEXT, " + TRIP_COLUMN_TANDEL_NAME + " TEXT, " + TRIP_COLUMN_AUCTIONER_NAME + " TEXT, " + TRIP_COLUMN_CO_ID + " INTEGER, " + TRIP_COLUMN_ENTER_DATE + " TEXT, " + TRIP_COLUMN_ENTER_BY + " INTEGER);";
        Log.e("MySqliteHelper : ", " TRIP TABLE CREATION : " + queryTrip);
        db.execSQL(queryTrip);

        String queryExpense = "CREATE TABLE " + TABLE_EXPENSES + " (" + EXPENSES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXPENSES_COLUMN_EXP_ID + " INTEGER, " + EXPENSES_COLUMN_NAME + " TEXT, " + EXPENSES_COLUMN_TYPE + " TEXT, " + EXPENSES_COLUMN_ACC_TYPE + " TEXT, " + EXPENSES_COLUMN_ENTRY_TYPE + " TEXT, " + EXPENSES_COLUMN_PHOTO_REQ + " INTEGER, " + EXPENSES_COLUMN_ACCESS_TO + " TEXT, " + EXPENSES_COLUMN_IS_USED + " INTEGER, " + EXPENSES_COLUMN_CO_ID + " INTEGER, " + EXPENSES_COLUMN_ENTER_DATE + " TEXT, " + EXPENSES_COLUMN_ENTER_BY + " INTEGER, " + EXPENSES_COLUMN_COMBO_VALUES + " TEXT);";
        Log.e("MySqliteHelper : ", " EXPENSES TABLE CREATION : " + queryExpense);
        db.execSQL(queryExpense);

        String queryTransaction = "CREATE TABLE " + TABLE_TRANSACTION + "(" + TRANSACTION_COLUMN_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TRANSACTION_COLUMN_SEASON_ID + " INTEGER, " + TRANSACTION_COLUMN_TR_DATE + " TEXT, " + TRANSACTION_COLUMN_TR_SENDER_ID + " INTEGER, " + TRANSACTION_COLUMN_TR_RECEIVER_ID + " INTEGER, " + TRANSACTION_COLUMN_TR_AMOUNT + " TEXT, " + TRANSACTION_COLUMN_TR_TYPE + " TEXT, " + TRANSACTION_COLUMN_TRIP_ID + " INTEGER, " + TRANSACTION_COLUMN_EXP_TYPE + " TEXT, " + TRANSACTION_COLUMN_EXP_RATE + " TEXT, " + TRANSACTION_COLUMN_EXP_QTY + " INTEGER, " + TRANSACTION_COLUMN_EXP_TOTAL + " TEXT, " + TRANSACTION_COLUMN_EXP_ADDED_AMOUNT + " TEXT, " + TRANSACTION_COLUMN_EXP_ADDED_AMT_TITLE + " TEXT, " + TRANSACTION_COLUMN_GEN_REMARK + " TEXT, " + TRANSACTION_COLUMN_PHOTO_1 + " TEXT, " + TRANSACTION_COLUMN_PHOTO_2 + " TEXT, " + TRANSACTION_COLUMN_PHOTO_3 + " TEXT, " + TRANSACTION_COLUMN_LOGIN_USER_ID + " INTEGER, " + TRANSACTION_COLUMN_LOGIN_DATE + " TEXT, " + TRANSACTION_COLUMN_IS_AUTH + " INTEGER, " + TRANSACTION_COLUMN_AUTH_REMARK + " TEXT, " + TRANSACTION_COLUMN_AUTH_USER_ID + " INTEGER, " + TRANSACTION_COLUMN_AUTH_DATE + " TEXT, " + TRANSACTION_COLUMN_REJECT_REMARK + " TEXT, " + TRANSACTION_COLUMN_REJECT_USER_ID + " INTEGER, " + TRANSACTION_COLUMN_REJECT_DATE + " TEXT, " + TRANSACTION_COLUMN_EXP_ID + " INTEGER, " + TRANSACTION_COLUMN_CO_ID + " INTEGER, " + TRANSACTION_COLUMN_ENTER_DATE + " TEXT, " + TRANSACTION_COLUMN_ENTER_BY + " INTEGER, " + TRANSACTION_COLUMN_IS_SYNC + " INTEGER);";
        Log.e("MySqliteHelper : ", " TRANSACTION TABLE CREATION : " + queryTransaction);
        db.execSQL(queryTransaction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_FISH);
        this.onCreate(db);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_TRIP_DISPLAY);
        this.onCreate(db);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_EXPENSES);
        this.onCreate(db);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_TRANSACTION);
        this.onCreate(db);
    }

    public void addFishToSqlite(Fish fish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FISH_COLUMN_ID, fish.getFishId());
        cv.put(FISH_COLUMN_NAME, fish.getFishName());
        cv.put(FISH_COLUMN_TYPE, fish.getFishType());
        cv.put(FISH_COLUMN_IS_USED, fish.getFishIsUsed());
        cv.put(FISH_COLUMN_MIN_RATE, fish.getFishMinRate());
        cv.put(FISH_COLUMN_MAX_RATE, fish.getFishMaxRate());
        cv.put(FISH_COLUMN_SIZE, fish.getFishSize());
        db.insert(TABLE_FISH, null, cv);
        db.close();
    }

    public void deleteFish() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FISH);
    }

    public ArrayList<Fish> getAllFishData() {
        ArrayList<Fish> fishArray = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FISH;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Fish fishItem = null;

        if (cursor.moveToFirst()) {
            do {

                fishItem = new Fish();
                fishItem.setFishId(cursor.getInt(0));
                fishItem.setFishName(cursor.getString(2));
                fishItem.setFishType(cursor.getString(3));
                fishItem.setFishIsUsed(cursor.getInt(4));
                fishItem.setFishSize(cursor.getString(5));
                fishItem.setFishMinRate(Double.parseDouble(cursor.getString(6)));
                fishItem.setFishMaxRate(Double.parseDouble(cursor.getString(7)));

                fishArray.add(fishItem);
            } while (cursor.moveToNext());
        }

        Log.e("LIST OF Fish : ", "" + fishArray.toString());

        return fishArray;
    }


    public void addTripToSqlite(TripDisplay trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRIP_COLUMN_TRIP_AUTO_ID, trip.getTripId());
        cv.put(TRIP_COLUMN_TRIP_ID, trip.getTripId());
        cv.put(TRIP_COLUMN_BOAT_ID, trip.getBoatId());
        cv.put(TRIP_COLUMN_START_DATE, trip.getTripStartDate());
        cv.put(TRIP_COLUMN_END_DATE, trip.getTripEndDate());
        cv.put(TRIP_COLUMN_TANDEL_ID, trip.getTripTandelId());
        cv.put(TRIP_COLUMN_AUCTIONER_ID, trip.getTripAuctionerId());
        cv.put(TRIP_COLUMN_TRIP_STAFF, trip.getTripStaff());
        cv.put(TRIP_COLUMN_TRIP_SETTLED, trip.getTripSettled());
        cv.put(TRIP_COLUMN_TRIP_STATUS, trip.getTripStatus());
        cv.put(TRIP_COLUMN_TRIP_DELETE, trip.getTripDelete());
        cv.put(TRIP_COLUMN_BOAT_NAME, trip.getBoatName());
        cv.put(TRIP_COLUMN_TANDEL_NAME, trip.getTandelName());
        cv.put(TRIP_COLUMN_AUCTIONER_NAME, trip.getAuctionerName());
        cv.put(TRIP_COLUMN_CO_ID, trip.getCoId());
        cv.put(TRIP_COLUMN_ENTER_DATE, trip.getEnterDate());
        cv.put(TRIP_COLUMN_ENTER_BY, trip.getEnterBy());
        db.insert(TABLE_TRIP_DISPLAY, null, cv);
        db.close();
    }

    public void deleteTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRIP_DISPLAY);
    }

    public ArrayList<TripDisplay> getAllTripData() {
        ArrayList<TripDisplay> tripArray = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TRIP_DISPLAY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TripDisplay tripItem = null;

        if (cursor.moveToFirst()) {
            do {
                tripItem = new TripDisplay();
                tripItem.setTripId(cursor.getInt(0));
                tripItem.setBoatId(cursor.getInt(2));
                tripItem.setTripStartDate(Long.parseLong(cursor.getString(3)));
                tripItem.setTripEndDate(Long.parseLong(cursor.getString(4)));
                tripItem.setTripTandelId(cursor.getInt(5));
                tripItem.setTripAuctionerId(cursor.getInt(6));
                tripItem.setTripStaff(cursor.getString(7));
                tripItem.setTripSettled(cursor.getInt(8));
                tripItem.setTripStatus(cursor.getString(9));
                tripItem.setTripDelete(cursor.getInt(10));
                tripItem.setBoatName(cursor.getString(11));
                tripItem.setTandelName(cursor.getString(12));
                tripItem.setAuctionerName(cursor.getString(13));
                tripItem.setCoId(cursor.getInt(14));
                tripItem.setEnterDate(Long.parseLong(cursor.getString(15)));
                tripItem.setEnterBy(cursor.getInt(16));

                tripArray.add(tripItem);
            } while (cursor.moveToNext());
        }

        Log.e("LIST OF Trip : ", "" + tripArray.toString());

        return tripArray;
    }


    public void addExpensesToSqlite(Expense exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXPENSES_COLUMN_ID, exp.getExpId());
        cv.put(EXPENSES_COLUMN_EXP_ID, exp.getExpId());
        cv.put(EXPENSES_COLUMN_NAME, exp.getExpName());
        cv.put(EXPENSES_COLUMN_TYPE, exp.getExpType());
        cv.put(EXPENSES_COLUMN_ACC_TYPE, exp.getExpAccType());
        cv.put(EXPENSES_COLUMN_ENTRY_TYPE, exp.getExpEntryType());
        cv.put(EXPENSES_COLUMN_PHOTO_REQ, exp.getExpIsPhotoReq());
        cv.put(EXPENSES_COLUMN_ACCESS_TO, exp.getExpAccessTo());
        cv.put(EXPENSES_COLUMN_IS_USED, exp.getExpIsUsed());
        cv.put(EXPENSES_COLUMN_CO_ID, exp.getCoId());
        cv.put(EXPENSES_COLUMN_ENTER_DATE, exp.getEnterDate());
        cv.put(EXPENSES_COLUMN_ENTER_BY, exp.getEnterBy());
        cv.put(EXPENSES_COLUMN_COMBO_VALUES, exp.getComboValues());
        db.insert(TABLE_EXPENSES, null, cv);
        db.close();
    }

    public void deleteExpense() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSES);
    }

    public ArrayList<Expense> getAllExpensesData() {
        ArrayList<Expense> expArray = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Expense exp = null;

        if (cursor.moveToFirst()) {
            do {
                exp = new Expense();
                exp.setExpId(cursor.getInt(0));
                exp.setExpName(cursor.getString(2));
                exp.setExpType(cursor.getString(3));
                exp.setExpAccType(cursor.getString(4));
                exp.setExpEntryType(cursor.getString(5));
                exp.setExpIsPhotoReq(cursor.getInt(6));
                exp.setExpAccessTo(cursor.getString(7));
                exp.setExpIsUsed(cursor.getInt(8));
                exp.setCoId(cursor.getInt(9));
                exp.setEnterDate(Long.parseLong(cursor.getString(10)));
                exp.setEnterBy(cursor.getInt(11));
                exp.setComboValues(cursor.getString(12));

                expArray.add(exp);
            } while (cursor.moveToNext());
        }

        Log.e("LIST OF Expenses : ", "" + expArray.toString());

        return expArray;
    }


    public void addTransactionToSqlite(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_COLUMN_SEASON_ID, transaction.getSeasonId());
        cv.put(TRANSACTION_COLUMN_TR_DATE, transaction.getTransactionDate());
        cv.put(TRANSACTION_COLUMN_TR_SENDER_ID, transaction.getSenderId());
        cv.put(TRANSACTION_COLUMN_TR_RECEIVER_ID, transaction.getReceiverId());
        cv.put(TRANSACTION_COLUMN_TR_AMOUNT, transaction.getAmount());
        cv.put(TRANSACTION_COLUMN_TR_TYPE, transaction.getTrType());
        cv.put(TRANSACTION_COLUMN_TRIP_ID, transaction.getTripId());
        cv.put(TRANSACTION_COLUMN_EXP_TYPE, transaction.getExpType());
        cv.put(TRANSACTION_COLUMN_EXP_RATE, transaction.getExpRate());
        cv.put(TRANSACTION_COLUMN_EXP_QTY, transaction.getExpQty());
        cv.put(TRANSACTION_COLUMN_EXP_TOTAL, transaction.getExpTotal());
        cv.put(TRANSACTION_COLUMN_EXP_ADDED_AMOUNT, transaction.getExpAddedAmt());
        cv.put(TRANSACTION_COLUMN_EXP_ADDED_AMT_TITLE, transaction.getExpAddedAmtTitle());
        cv.put(TRANSACTION_COLUMN_GEN_REMARK, transaction.getGenRemark());
        cv.put(TRANSACTION_COLUMN_PHOTO_1, transaction.getGenPhoto1());
        cv.put(TRANSACTION_COLUMN_PHOTO_2, transaction.getGenPhoto2());
        cv.put(TRANSACTION_COLUMN_PHOTO_3, transaction.getGenPhoto3());
        cv.put(TRANSACTION_COLUMN_LOGIN_USER_ID, transaction.getLoginUserId());
        cv.put(TRANSACTION_COLUMN_LOGIN_DATE, transaction.getLoginDate());
        cv.put(TRANSACTION_COLUMN_IS_AUTH, transaction.getIsAuthorised());
        cv.put(TRANSACTION_COLUMN_AUTH_REMARK, transaction.getAuthRemark());
        cv.put(TRANSACTION_COLUMN_AUTH_USER_ID, transaction.getAuthUserId());
        cv.put(TRANSACTION_COLUMN_AUTH_DATE, transaction.getAuthDate());
        cv.put(TRANSACTION_COLUMN_REJECT_REMARK, transaction.getRejectRemark());
        cv.put(TRANSACTION_COLUMN_REJECT_USER_ID, transaction.getRejectUserId());
        cv.put(TRANSACTION_COLUMN_REJECT_DATE, transaction.getRejectDate());
        cv.put(TRANSACTION_COLUMN_EXP_ID, transaction.getExpId());
        cv.put(TRANSACTION_COLUMN_CO_ID, transaction.getCoId());
        cv.put(TRANSACTION_COLUMN_ENTER_DATE, transaction.getEnterDate());
        cv.put(TRANSACTION_COLUMN_ENTER_BY, transaction.getEnterBy());
        cv.put(TRANSACTION_COLUMN_IS_SYNC, transaction.getIsSync());
        db.insert(TABLE_TRANSACTION, null, cv);
        db.close();
    }

    public void deleteTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRANSACTION);
    }

    public ArrayList<Transaction> getAllTransactionData() {
        ArrayList<Transaction> transactionArray = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TRANSACTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Transaction transaction = null;

        if (cursor.moveToFirst()) {
            do {
                transaction = new Transaction();
                transaction.setTransactionId(cursor.getInt(0));
                transaction.setSeasonId(cursor.getInt(1));
                transaction.setTransactionDate(Long.parseLong(cursor.getString(2)));
                transaction.setSenderId(cursor.getInt(3));
                transaction.setReceiverId(cursor.getInt(4));
                transaction.setAmount(Double.parseDouble(cursor.getString(5)));
                transaction.setTrType(cursor.getString(6));
                transaction.setTripId(cursor.getInt(7));
                transaction.setExpType(cursor.getString(8));
                transaction.setExpRate(Double.parseDouble(cursor.getString(9)));
                transaction.setExpQty(cursor.getInt(10));
                transaction.setExpTotal(Double.parseDouble(cursor.getString(11)));
                transaction.setExpAddedAmt(Double.parseDouble(cursor.getString(12)));
                transaction.setExpAddedAmtTitle(cursor.getString(13));
                transaction.setGenRemark(cursor.getString(14));
                transaction.setGenPhoto1(cursor.getString(15));
                transaction.setGenPhoto2(cursor.getString(16));
                transaction.setGenPhoto3(cursor.getString(17));
                transaction.setLoginUserId(cursor.getInt(18));
                transaction.setLoginDate(Long.parseLong(cursor.getString(19)));
                transaction.setIsAuthorised(cursor.getInt(20));
                transaction.setAuthRemark(cursor.getString(21));
                transaction.setAuthUserId(cursor.getInt(22));
                transaction.setAuthDate(Long.parseLong(cursor.getString(23)));
                transaction.setRejectRemark(cursor.getString(24));
                transaction.setRejectUserId(cursor.getInt(25));
                transaction.setRejectDate(Long.parseLong(cursor.getString(26)));
                transaction.setCoId(cursor.getInt(27));
                transaction.setExpId(Long.parseLong(cursor.getString(28)));
                transaction.setEnterDate(Long.parseLong(cursor.getString(29)));
                transaction.setEnterBy(cursor.getInt(30));
                transaction.setIsSync(cursor.getInt(31));

                transactionArray.add(transaction);
            } while (cursor.moveToNext());
        }

        Log.e("LIST OF Transaction : ", "" + transactionArray.toString());

        return transactionArray;
    }
}
