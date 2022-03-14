package ru.ssermakov.mystock.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import ru.ssermakov.mystock.data.room.dao.SpareDao;
import ru.ssermakov.mystock.data.room.entity.Spare;

@Database(entities = {Spare.class}, version = 1)
public abstract class SparesDataBase extends RoomDatabase {

    public abstract SpareDao spareDao();

}