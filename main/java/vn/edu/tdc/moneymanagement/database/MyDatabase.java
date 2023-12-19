package vn.edu.tdc.moneymanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.TotalMoney;

@Database(entities = {FixedAccount.class, TotalMoney.class}, version = MyDatabase.DB_VERSION)
@TypeConverters({FixedAccount.Converters.class, FixedAccount.Converters.class})
public abstract class MyDatabase extends RoomDatabase {

    // Dinh nghia cac thuoc tinh cho database
    public final static String DB_NAME = "expense_db";
    public final static int DB_VERSION = 4;
    //So luong co the su ly cung luc
    public static final int THREAD_NUMBER = 1000;
    public static final ExecutorService dbReadWriteExecutor = Executors.newFixedThreadPool(THREAD_NUMBER);
    private static volatile MyDatabase database;

    //Dinh nghia ham khoi tao database
    public static MyDatabase getDatabase(Context context) {
        //khoi tao database noi chua ton tai
        if (database == null) {
            synchronized (MyDatabase.class) {
                database = Room.databaseBuilder(context, MyDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    //Dinh nghia ham lay ve doi tuong
    public abstract FixedAccountDAO getFixedAccountDA0();

    public abstract TotalMoneyDAO getTotalMoneyDao();

}
