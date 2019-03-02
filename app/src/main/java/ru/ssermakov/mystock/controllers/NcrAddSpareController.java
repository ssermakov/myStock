package ru.ssermakov.mystock.controllers;

import android.util.Log;
import android.widget.Toast;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.app.App;
import ru.ssermakov.mystock.data.room.SparesDataBase;
import ru.ssermakov.mystock.data.room.dao.SpareDao;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.NcrAddSpareActivity;

public class NcrAddSpareController {
    private final NcrAddSpareActivity ncrAddSpareView;
    private SpareDao spareDao;

    public NcrAddSpareController(NcrAddSpareActivity ncrAddSpareActivity) {
        this.ncrAddSpareView = ncrAddSpareActivity;
        SparesDataBase db = App.getInstance().getDb();
        this.spareDao = db.spareDao();
    }

    public void addSpareToDb(final Spare spare) {
        Completable completable = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spareDao.insertSpare(spare);
            }
        });
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("lol", "wtf");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("lol", "success");
                        Toast.makeText(ncrAddSpareView,
                                ncrAddSpareView.getResources().getText(R.string.addSpareToast),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("lol", "error");
                    }
                });
    }
}
