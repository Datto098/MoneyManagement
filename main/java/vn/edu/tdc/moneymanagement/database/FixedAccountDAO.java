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

@Dao
@TypeConverters(FixedAccount.Converters.class)
public interface FixedAccountDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        public long saveFixedAccount(FixedAccount fixedAccount);

        @Query("SELECT * FROM " + FixedAccount.TABLE_NAME  + " ORDER BY " + FixedAccount.DATE + " DESC ")
        public List<FixedAccount> getAllFixedAccount();

        @Update
        public int updateFixedAccount(FixedAccount fixedAccount);

        @Delete
        public int deleteFixedAccount(FixedAccount fixedAccount);

        @Query("SELECT SUM(money) FROM fixed_accounts WHERE SUBSTR(date, 1, 7) = SUBSTR(strftime('%Y-%m', CURRENT_DATE), 1, 7)")
        public long getTotalMoneyThisMonth();

        @Query("SELECT strftime('%Y-%m', CURRENT_DATE) AS currentMonthYear")
        String getCurrentMonthYear();

}
