package vn.edu.tdc.moneymanagement.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.tdc.moneymanagement.model.FixedAccount;

@Database(entities = {FixedAccount.class}, version = MyDatabase.DB_VERSION)
@TypeConverters(FixedAccount.Converters.class)
public abstract class MyDatabase extends RoomDatabase {

    // Dinh nghia cac thuoc tinh cho database
    public final static String DB_NAME = "expense_db";
    public final static int DB_VERSION = 1;
    private static volatile  MyDatabase database;

    //So luong co the su ly cung luc
    public static final int THREAD_NUMBER = 10;
    public static final ExecutorService dbReadWriteExecutor = Executors.newFixedThreadPool(THREAD_NUMBER);

    //Dinh nghia ham lay ve doi tuong
    public abstract FixedAccountDAO getFixedAccountDA0();

    //Dinh nghia ham khoi tao database
    public static MyDatabase getDatabase(Context context){
        //khoi tao database noi chua ton tai
        if(database == null){
            synchronized (MyDatabase.class){
                database = Room.databaseBuilder(context, MyDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

}
