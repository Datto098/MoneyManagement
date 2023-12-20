package vn.edu.tdc.moneymanagement.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kotlinx.coroutines.internal.CtorCache;
import vn.edu.tdc.moneymanagement.model.Category;
import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.SpendingAccount;
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
        if(sqLiteDatabase != null){

            //Tao bang fixed_account
            String sqlFixedAccount = "CREATE TABLE " + FixedAccount.TABLE_NAME + " ( " +
                    FixedAccount.ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FixedAccount.MONEY+ " INTEGER," +
                   FixedAccount.CONTENT+ " TEXT," +
                   FixedAccount.DATE +  " TEXT);";

            sqLiteDatabase.execSQL(sqlFixedAccount);


            //Tao bang total money
            String sqlTotalMoney = "CREATE TABLE " + TotalMoney.TABLE_NAME + " ( " +
                    TotalMoney.ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TotalMoney.MONEY+ " INTEGER," +
                    TotalMoney.CONTENT+ " TEXT," +
                    TotalMoney.DATE +  " TEXT);";

            sqLiteDatabase.execSQL(sqlTotalMoney);

            //Tao bang category
            String sqlCategory = "CREATE TABLE " + Category.TABLE_NAME + " ( " +
                    Category.ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Category.ICON+ " INTEGER," +
                    Category.CONTENT+ " TEXT);";

            sqLiteDatabase.execSQL(sqlCategory);


            //Tao bang spending account
            String sqlSpending = "CREATE TABLE " + SpendingAccount.TABLE_NAME + " ( " +
                    SpendingAccount.ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SpendingAccount.MONEY+ " INTEGER," +
                    SpendingAccount.CATEGORY_ID+ " INTEGER," +
                    SpendingAccount.DATE +  " TEXT);";

            sqLiteDatabase.execSQL(sqlSpending);
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
    public ArrayList<FixedAccount> getAllFixedAccount(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<FixedAccount>();
        if(db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + FixedAccount.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(FixedAccount.ID);
                    int iMoney = cursor.getColumnIndex(FixedAccount.MONEY);
                    int iContent = cursor.getColumnIndex(FixedAccount.CONTENT);
                    int  iDate = cursor.getColumnIndex(FixedAccount.DATE);

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
            affectedRows = db.delete(FixedAccount.TABLE_NAME,FixedAccount.ID + " = ?", new String[]{String.valueOf(accountId)});
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

    // Hàm update cho bang total_count
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

    // Hàm xóa cho bảng total_count
    public int deleteTotalMoney(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            affectedRows = db.delete(TotalMoney.TABLE_NAME,TotalMoney.ID + " = ?", new String[]{String.valueOf(accountId)});
        }
        return affectedRows;
    }

    //Lay tat ca du lieu tu bang total_count
    public ArrayList<TotalMoney> getAllTotalMoney(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<TotalMoney> totalMonies = new ArrayList<TotalMoney>();
        if(db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TotalMoney.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(TotalMoney.ID);
                    int iMoney = cursor.getColumnIndex(TotalMoney.MONEY);
                    int iContent = cursor.getColumnIndex(TotalMoney.CONTENT);
                    int  iDate = cursor.getColumnIndex(TotalMoney.DATE);

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


    /*--------------------Category------------------*/

    //Ham them category vao bang category
    public long addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        long result = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(Category.ICON, category.getIcon());
            values.put(Category.CONTENT, category.getContent());

            result = db.insert(Category.TABLE_NAME, null, values);
        }
        return result;
    }

    //Ham sua category
    public int updateCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(Category.ICON, category.getIcon());
            values.put(Category.CONTENT, category.getContent());

            affectedRows = db.update(
                    Category.TABLE_NAME,
                    values,
                    Category.ID + " = ?",
                    new String[]{String.valueOf(category.getId())}
            );
        }
        return affectedRows;
    }
    // Hàm xóa cho bảng  category
    public int deleteCategory(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            affectedRows = db.delete(Category.TABLE_NAME,Category.ID + " = ?", new String[]{String.valueOf(accountId)});
        }
        return affectedRows;
    }

    //Lay tat ca du lieu tu bang category
    public ArrayList<Category> getAllCategory(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Category> categories  = new ArrayList<Category>();
        if(db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + Category.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(Category.ID);
                    int iICon = cursor.getColumnIndex(Category.ICON);
                    int iContent = cursor.getColumnIndex(Category.CONTENT);

                    int id = cursor.getInt(iId);
                    long icon = cursor.getLong(iICon);
                    String content = cursor.getString(iContent);
                    Category category = new Category(id, icon, content);
                    categories.add(category);

                } while (cursor.moveToNext());

                cursor.close();
            }
        }
        return categories;
    }





    /*--------------------Spending account------------------*/
    //Ham them cho bang total_money
    public long addSpendingAccount(SpendingAccount spendingAccount) {
        SQLiteDatabase db = getWritableDatabase();
        long result = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(SpendingAccount.MONEY, spendingAccount.getMoney());
            values.put(SpendingAccount.CATEGORY_ID, spendingAccount.getCategory().getId());
            values.put(SpendingAccount.DATE, spendingAccount.getDate().format(DateTimeFormatter.ISO_DATE));

            result = db.insert(SpendingAccount.TABLE_NAME, null, values);
        }
        return result;
    }

    // Hàm update cho bang total_count
    public int updateSpendingAccount(SpendingAccount spendingAccount) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(SpendingAccount.MONEY, spendingAccount.getMoney());
            values.put(SpendingAccount.CATEGORY_ID, spendingAccount.getCategory().getId());
            values.put(SpendingAccount.DATE, spendingAccount.getDate().format(DateTimeFormatter.ISO_DATE));

            affectedRows = db.update(
                    SpendingAccount.TABLE_NAME,
                    values,
                    SpendingAccount.ID + " = ?",
                    new String[]{String.valueOf(spendingAccount.getId())}
            );
        }
        return affectedRows;
    }

    // Hàm xóa cho bảng total_count
    public int deleteSpendingAccount(int accountId) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = 0;
        if (db != null) {
            affectedRows = db.delete(SpendingAccount.TABLE_NAME,SpendingAccount.ID + " = ?", new String[]{String.valueOf(accountId)});
        }
        return affectedRows;
    }

    //Lay tat ca du lieu tu bang spending account
    public ArrayList<SpendingAccount> getAllSpendingAccounts() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<SpendingAccount> spendingAccounts = new ArrayList<>();

        if (db != null) {
            String query = "SELECT spending_accounts._id, spending_accounts.money, spending_accounts.category_id, spending_accounts.date, " +
                    "categories.icon, categories.content " +
                    "FROM spending_accounts " +
                    "INNER JOIN categories ON spending_accounts.category_id = categories._id";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int iId = cursor.getColumnIndex(SpendingAccount.ID);
                    int iMoney = cursor.getColumnIndex(SpendingAccount.MONEY);
                    int iCategoryId = cursor.getColumnIndex(SpendingAccount.CATEGORY_ID);
                    int iDate = cursor.getColumnIndex(SpendingAccount.DATE);
                    int iIcon = cursor.getColumnIndex(Category.ICON);
                    int iContent = cursor.getColumnIndex(Category.CONTENT);

                    int id = cursor.getInt(iId);
                    long money = cursor.getLong(iMoney);
                    int categoryId = cursor.getInt(iCategoryId);
                    LocalDate date = LocalDate.parse(cursor.getString(iDate), DateTimeFormatter.ISO_DATE);
                    long icon = cursor.getLong(iIcon);
                    String content = cursor.getString(iContent);

                    Category category = new Category(categoryId, icon, content);
                    SpendingAccount spendingAccount = new SpendingAccount(id, money, category, date);
                    spendingAccounts.add(spendingAccount);

                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        return spendingAccounts;
    }




    /*--------------------END------------------*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
