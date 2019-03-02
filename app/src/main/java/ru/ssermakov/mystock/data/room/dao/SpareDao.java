package ru.ssermakov.mystock.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.ssermakov.mystock.data.room.entity.Spare;

@Dao
public interface SpareDao {

    @Query("SELECT * FROM spares")
    Single<List<Spare>> getAll();

    @Query("SELECT * FROM spares LIMIT 1")
    Spare getSpare();

    @Query("SELECT * FROM spares WHERE id = :id")
    Spare getById(long id);

    @Insert
    Long insertSpare(Spare spare);

    @Update
    void update(Spare spare);

    @Delete
    void delete(Spare spare);

}
