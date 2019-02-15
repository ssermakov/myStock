package ru.ssermakov.mystock.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import ru.ssermakov.mystock.data.room.dao.SpareDao;
import ru.ssermakov.mystock.data.room.entity.Spare;

@Database(entities = {Spare.class}, version = 1)
public abstract class SparesDataBase extends RoomDatabase {

    public abstract SpareDao spareDao();

}