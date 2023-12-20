package vn.edu.tdc.moneymanagement.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.TotalMoney;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_db";
    private static final int VERSION = 1;
    private static Activity activity;

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Bang ban database


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (sqLiteDatabase != null) {

            //Tao bang fixed_account
            String sqlFixedAccount = "CREATE TABLE " + FixedAccount.TABLE_NAME + " ( " +
                    FixedAccount.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FixedAccount.MONEY + " INTEGER," +
                    FixedAccount.CONTENT + " TEXT," +
                    FixedAccount.DATE + " TEXT);";

            sqLiteDatabase.execSQL(sqlFixedAccount);


            //Tao bang total money
            String sqlTotalMoney = "CREATE TABLE " + TotalMoney.TABLE_NAME + " ( " +
                    TotalMoney.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TotalMoney.MONEY + " INTEGER," +
                    TotalMoney.CONTENT + " TEXT," +
                    TotalMoney.DATE + " TEXT);";

            sqLiteDatabase.execSQL(sqlTotalMoney);
        }
    }


    /*--------------------Fixed Account------------------*/
    //Them du lieu va bang fixed_account
    public long addFixedAccount(FixedAccount fixedAccount) {
        SQLiteDatabase db = getWritableDatabase();
        long result = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(FixedAccount.MONEY, fixedAccount.getMoney());
            values.put(FixedAccount.CONTENT, fixedAccount.getContent());
            values.put(FixedAccount.DATE, fixedAccount.getDate().format(DateTimeFormatter.ISO_DATE));

            result = db.insert(FixedAccount.TABLE_NAME, null, values);
        }
        return result;
    }

    //Lay tat ca du lieu tu bang fixed account
    public ArrayList<FixedAccount> getAllFixedAccount() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<FixedAccount>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + FixedAccount.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(FixedAccount.ID);
                    int iMoney = cursor.getColumnIndex(FixedAccount.MONEY);
                    int iContent = cursor.getColumnIndex(FixedAccount.CONTENT);
                    int iDate = cursor.getColumnIndex(FixedAccount.DATE);

                    int id = cursor.getInt(iId);
                    long money = cursor.getLong(iMoney);
                    String content = cursor.getString(iContent);
                    String dateString = cursor.getString(iDate);
                    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    FixedAccount fixedAccount = new FixedAccount(id, money, content, date);
                    fixedAccounts.add(fixedAccount);

                } while (cursor.moveToNext());

                cursor.close();
            }
        }
        return fixedAccounts;
    }

    // Hàm update cho bảng fixed_account
    public int updateFixedAccount(FixedAccount fixedAccount) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(FixedAccount.MONEY, fixedAccount.getMoney());
            values.put(FixedAccount.CONTENT, fixedAccount.getContent());
            values.put(FixedAccount.DATE, fixedAccount.getDate().format(DateTimeFormatter.ISO_DATE));

            affectedRows = db.update(
                    FixedAccount.TABLE_NAME,
                    values,
                    FixedAccount.ID + " = ?",
                    new String[]{String.valueOf(fixedAccount.getId())}
            );
        }
        return affectedRows;
    }

    // Hàm xóa cho bảng fixed_account
    public int deleteFixedAccount(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            affectedRows = db.delete(FixedAccount.TABLE_NAME, FixedAccount.ID + " = ?", new String[]{String.valueOf(accountId)});
        }
        return affectedRows;
    }

    //Ham lay tong tien cua thang hiẹn
    public long getTotalFixedAccountForCurrentMonth() {
        SQLiteDatabase db = getWritableDatabase();
        long totalMoney = 0;

        if (db != null) {
            Cursor cursor = db.rawQuery(
                    "SELECT SUM(" + FixedAccount.MONEY + ") AS totalMoney " +
                            "FROM " + FixedAccount.TABLE_NAME + " " +
                            "WHERE SUBSTR(" + FixedAccount.DATE + ", 1, 7) = strftime('%Y-%m', 'now')",
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int total = cursor.getColumnIndex("totalMoney");
                totalMoney = cursor.getLong(total);
                cursor.close();
            }
        }

        return totalMoney;
    }

    //Ham tim ghi chi từ ngày.... đến ngày
    public ArrayList<FixedAccount> getFixedAccountsInDateRange(LocalDate startDate, LocalDate endDate) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<>();

        if (db != null) {
            String query = "SELECT * FROM " + FixedAccount.TABLE_NAME + " " +
                    "WHERE " + FixedAccount.DATE + " BETWEEN ? AND ?";
            String[] selectionArgs = {startDate.format(DateTimeFormatter.ISO_DATE), endDate.format(DateTimeFormatter.ISO_DATE)};

            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(FixedAccount.ID);
                    int iMoney = cursor.getColumnIndex(FixedAccount.MONEY);
                    int iContent = cursor.getColumnIndex(FixedAccount.CONTENT);
                    int iDate = cursor.getColumnIndex(FixedAccount.DATE);

                    int id = cursor.getInt(iId);
                    long money = cursor.getLong(iMoney);
                    String content = cursor.getString(iContent);
                    String dateString = cursor.getString(iDate);
                    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);

                    FixedAccount fixedAccount = new FixedAccount(id, money, content, date);
                    fixedAccounts.add(fixedAccount);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        return fixedAccounts;
    }





    /*--------------------Total Money------------------*/

    //Ham them cho bang total_money
    public long addTotalMoney(TotalMoney totalMoney) {
        SQLiteDatabase db = getWritableDatabase();
        long result = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(TotalMoney.MONEY, totalMoney.getMoney());
            values.put(TotalMoney.CONTENT, totalMoney.getContent());
            values.put(TotalMoney.DATE, totalMoney.getDate().format(DateTimeFormatter.ISO_DATE));

            result = db.insert(TotalMoney.TABLE_NAME, null, values);
        }
        return result;
    }

    // Hàm update cho bảng total_count
    public int updateTotalMoney(TotalMoney totalMoney) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(TotalMoney.MONEY, totalMoney.getMoney());
            values.put(TotalMoney.CONTENT, totalMoney.getContent());
            values.put(TotalMoney.DATE, totalMoney.getDate().format(DateTimeFormatter.ISO_DATE));

            affectedRows = db.update(
                    TotalMoney.TABLE_NAME,
                    values,
                    TotalMoney.ID + " = ?",
                    new String[]{String.valueOf(totalMoney.getId())}
            );

        }
        return affectedRows;
    }

    // Hàm xóa cho bảng total_money_amount
    public int deleteTotalMoney(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            affectedRows = db.delete(TotalMoney.TABLE_NAME, TotalMoney.ID + " = ?", new String[]{String.valueOf(accountId)});
        }
        return affectedRows;
    }

    //Lay tat ca du lieu tu bang total_count
    public ArrayList<TotalMoney> getAllTotalMoney(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<TotalMoney> fixedAccounts = new ArrayList<TotalMoney>();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TotalMoney.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(TotalMoney.ID);
                    int iMoney = cursor.getColumnIndex(TotalMoney.MONEY);
                    int iContent = cursor.getColumnIndex(TotalMoney.CONTENT);
                    int iDate = cursor.getColumnIndex(TotalMoney.DATE);

                    int id = cursor.getInt(iId);
                    long money = cursor.getLong(iMoney);
                    String content = cursor.getString(iContent);
                    String dateString = cursor.getString(iDate);
                    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    TotalMoney totalMoney = new TotalMoney(id, money, content, date);
                    fixedAccounts.add(totalMoney);

                } while (cursor.moveToNext());

                cursor.close();
            }
        }
        return fixedAccounts;
    }

    //Ham lay tong tien cua thang hiẹn
    public long getTotalMoneyForCurrentMonth() {
        SQLiteDatabase db = getWritableDatabase();
        long totalMoney = 0;

        if (db != null) {
            Cursor cursor = db.rawQuery(
                    "SELECT SUM(" + TotalMoney.MONEY + ") AS totalMoney " +
                            "FROM " + TotalMoney.TABLE_NAME + " " +
                            "WHERE SUBSTR(" + TotalMoney.DATE + ", 1, 7) = strftime('%Y-%m', 'now')",
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int total = cursor.getColumnIndex("totalMoney");
                totalMoney = cursor.getLong(total);
                cursor.close();
            }
        }

        return totalMoney;
    }

    //Ham tim ghi chi từ ngày.... đến ngày
    public ArrayList<TotalMoney> getTotalMoneyInDateRange(LocalDate startDate, LocalDate endDate) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<TotalMoney> totalMonies = new ArrayList<>();

        if (db != null) {
            String query = "SELECT * FROM " + TotalMoney.TABLE_NAME + " " +
                    "WHERE " + TotalMoney.DATE + " BETWEEN ? AND ?";
            String[] selectionArgs = {startDate.format(DateTimeFormatter.ISO_DATE), endDate.format(DateTimeFormatter.ISO_DATE)};

            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(TotalMoney.ID);
                    int iMoney = cursor.getColumnIndex(TotalMoney.MONEY);
                    int iContent = cursor.getColumnIndex(TotalMoney.CONTENT);
                    int iDate = cursor.getColumnIndex(TotalMoney.DATE);

                    int id = cursor.getInt(iId);
                    long money = cursor.getLong(iMoney);
                    String content = cursor.getString(iContent);
                    String dateString = cursor.getString(iDate);
                    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);

                    TotalMoney totalMoney = new TotalMoney(id, money, content, date);
                    totalMonies.add(totalMoney);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        return totalMonies;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
