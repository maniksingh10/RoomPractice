package com.manik.gymmembership.AppDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface GymDao {

    @Query("SELECT * FROM memberstable ORDER BY Name")
    List<Member> loadAllTasks();

    @Query("SELECT COUNT (id) FROM memberstable")
    int getTotalMembers();

    @Insert
    void insertTask(Member member);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Member taskEntry);

    @Delete
    void deleteTask(Member taskEntry);

    @Query("SELECT * FROM memberstable WHERE id = :mid")
    Member loadMemberById(int mid);
}
