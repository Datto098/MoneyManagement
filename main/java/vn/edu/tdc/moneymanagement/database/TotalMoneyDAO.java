package vn.edu.tdc.moneymanagement.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

import vn.edu.tdc.moneymanagement.model.FixedAccount;
import vn.edu.tdc.moneymanagement.model.TotalMoney;

@Dao
@TypeConverters(FixedAccount.Converters.class)
public interface TotalMoneyDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long saveTotalMoney(TotalMoney totalMoney);

    @Query("SELECT * FROM " + TotalMoney.TABLE_NAME)
    List<TotalMoney> getAllTotalMoney();

    @Update
    int updateTotalMoney(TotalMoney totalMoney);

    @Delete
    int deleteTotalMoney(TotalMoney totalMoney);

    @Query("SELECT SUM(money) FROM " + TotalMoney.TABLE_NAME + " WHERE SUBSTR(date, 1, 7) = SUBSTR(strftime('%Y-%m', CURRENT_DATE), 1, 7)")
    long getTotalMoneyThisMonth();

    @Query("SELECT strftime('%Y-%m', CURRENT_DATE) AS currentMonthYear")
    String getCurrentMonthYear();

}
