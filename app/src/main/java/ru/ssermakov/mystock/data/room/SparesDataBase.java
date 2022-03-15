package ru.ssermakov.mystock.data.room;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;


import ru.ssermakov.mystock.data.room.dao.PbfSpareDao;
import ru.ssermakov.mystock.data.room.dao.SpareDao;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.data.room.entity.Spare;

@Database(entities = {Spare.class, PbfSpare.class},
        version = 2)
public abstract class SparesDataBase extends RoomDatabase {

    public abstract SpareDao spareDao();
    public abstract PbfSpareDao pbfSparesDao();

}