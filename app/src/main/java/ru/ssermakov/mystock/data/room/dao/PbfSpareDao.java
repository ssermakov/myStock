package ru.ssermakov.mystock.data.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.data.room.entity.Spare;

@Dao
public interface PbfSpareDao {

    @Query("SELECT * FROM pbfSpares")
    Single<List<PbfSpare>> getAll();

    @Query("SELECT * FROM pbfSpares WHERE id = :id")
    Single<List<PbfSpare>> getById(long id);

    @Update
    void updatePbfSpare(PbfSpare pbfSpare);


    @Insert
    Long insertPbfSpare(PbfSpare pbfSpare);

    @Delete
    void deletePbfSpare(PbfSpare pbfSpare);
}
