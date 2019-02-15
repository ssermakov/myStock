package ru.ssermakov.mystock.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.ssermakov.mystock.data.room.SparesDataBase;

public class App extends Application {

    public static App instance;
    private SparesDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, SparesDataBase.class, "spares")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public SparesDataBase getDb() {
        return db;
    }
}
