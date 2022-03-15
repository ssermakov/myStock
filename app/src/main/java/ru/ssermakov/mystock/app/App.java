package ru.ssermakov.mystock.app;

import android.app.Application;
import androidx.room.Room;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import ru.ssermakov.mystock.data.room.SparesDataBase;

public class App extends Application {

    public static App instance;
    private SparesDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, SparesDataBase.class, "spares")
//                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'PbfSpare' " +
                    "('id' INTEGER NOT NUll, " +
                    "'name' TEXT NOT NUll DEFAULT '', " +
                    "'serialNumber' TEXT NOT NUll DEFAULT '', " +
                    "'state' TEXT NOT NUll DEFAULT '', " +
                    "'customer' TEXT NOT NUll DEFAULT '', " +
                    "'quantity' TEXT NOT NUll DEFAULT '', " +
                    "'comment' TEXT NOT NUll DEFAULT '', " +
                    "PRIMARY KEY('id'))");
        }
    };

    public static App getInstance() {
        return instance;
    }

    public SparesDataBase getDb() {
        return db;
    }
}
