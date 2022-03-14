package ru.ssermakov.mystock.data.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
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
