package neel.com.mocksocialapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import neel.com.mocksocialapp.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_tbl")
    LiveData<List<User>> loadAllUser();

    @Insert
    void insertUser(User... users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(User user);

    @Query("DELETE FROM user_tbl")
    void deleteUserList();


}
