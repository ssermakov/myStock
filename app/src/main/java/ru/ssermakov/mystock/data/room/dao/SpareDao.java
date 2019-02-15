package ru.ssermakov.mystock.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssermakov.mystock.data.room.entity.Spare;

@Dao
public interface SpareDao {

    @Query("SELECT * FROM spares")
    List<Spare> getAll();

    @Query("SELECT * FROM spares WHERE id = :id")
    Spare getById(long id);

    @Insert
    Long insert(Spare patient);

    @Update
    void update(Spare patient);

    @Delete
    void delete(Spare patient);

}
