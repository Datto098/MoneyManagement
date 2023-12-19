package vn.edu.tdc.moneymanagement.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.moneymanagement.model.FixedAccount;

public class MyDatabaseAPIs {

    //Dinh nghia cac NotificationID;
    public static final int SAY_DONE = 1;
    public static final int GET_ALL_DONE = 2;

    public static final int DELETE_DONE = 3;
    public static final int UPDATE_DONE = 4;
    public static final int QUERY_DONE = 5;


    private FixedAccountDAO fixedAccountDAO;

    //B2: Dinh nghia mot bien interface
    private CompleteListener completeListener;

    //B3: Dinh nghia ham Setter cho interface;
    public void setCompleteListener(CompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    //Constructor
    public  MyDatabaseAPIs(Context context){
        fixedAccountDAO = MyDatabase.getDatabase(context).getFixedAccountDA0();
    }

    //Lop tam
    private static  class Temp{
        public long position;
        public String string;
        public boolean complete;
    }

    //Dinh nghia cac APIs tang tren

    //1. Ghi
    public long saveFixedAccount (FixedAccount fixedAccount){
        final  Temp temp = new Temp();

        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.complete = false;
                temp.position = fixedAccountDAO.saveFixedAccount(fixedAccount);
                temp.complete = true;
            }
        });

        while(!temp.complete){}

        //Bao cho phia Activity
        if(temp.position != -1){
            //B4: Thuc hien thong bao
            if(completeListener != null){
                //Cap nhat id cho doi tuong
                fixedAccount.setId((int)temp.position);
                completeListener.notifyToActivity(SAY_DONE);
            }
        }
        return temp.position;
    }

    //2. Lay tat ca
    public void getAllFixedAccount(ArrayList<FixedAccount> fixedAccounts){
        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<FixedAccount> fixedAccountList = fixedAccountDAO.getAllFixedAccount();
                if(fixedAccountList.size() != 0){
                    fixedAccounts.addAll(fixedAccountList);
                    if(completeListener != null){
                        completeListener.notifyToActivity(GET_ALL_DONE);
                    }
                }
            }
        });
    }

    //3. Xóa
    public long deleteFixedAccount (FixedAccount fixedAccount){
        final  Temp temp = new Temp();

        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.complete = false;
                temp.position = fixedAccountDAO.deleteFixedAccount(fixedAccount);
                temp.complete = true;
            }
        });

        while(!temp.complete){}

        if(temp.position != -1){
            if(completeListener != null){
                completeListener.notifyToActivity(DELETE_DONE);
            }
        }
        return temp.position;
    }

    //4. Update
    public long updateFixedAccount (FixedAccount fixedAccount){
        final  Temp temp = new Temp();

        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.complete = false;
                temp.position = fixedAccountDAO.updateFixedAccount(fixedAccount);
                temp.complete = true;
            }
        });

        while(!temp.complete){}

        if(temp.position != -1){
            if(completeListener != null){
                completeListener.notifyToActivity(UPDATE_DONE);
            }
        }
        return temp.position;
    }

    //Get total money of fixed account in this month

    public String getCurrentMonthYear (){
        final  Temp temp = new Temp();
        String day;
        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.complete = false;
                temp.string = fixedAccountDAO.getCurrentMonthYear();
                temp.complete = true;
            }
        });

        while(!temp.complete){}

        if(temp.string != null){
            if(completeListener != null){
                completeListener.notifyToActivity(QUERY_DONE);
            }
        }
        return temp.string;
    }

    public long getTotalMoneyThisMonth (){
        final  Temp temp = new Temp();
        String day;
        MyDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.complete = false;
                temp.position = fixedAccountDAO.getTotalMoneyThisMonth();
                temp.complete = true;
            }
        });

        while(!temp.complete){}

        if(temp.string != null){
            if(completeListener != null){
                completeListener.notifyToActivity(QUERY_DONE);
            }
        }
        return temp.position;
    }




    //Dinh nghia co che uy quen
    //B1: Dinh nghia interface;
    public interface CompleteListener{
        public void notifyToActivity(int notificationID);
    }
}
