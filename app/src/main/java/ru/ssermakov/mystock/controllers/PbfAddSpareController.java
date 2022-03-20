package ru.ssermakov.mystock.controllers;

import android.util.Log;
import android.widget.Toast;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.app.App;
import ru.ssermakov.mystock.data.room.SparesDataBase;
import ru.ssermakov.mystock.data.room.dao.PbfSpareDao;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.views.PbfAddSpareActivity;

public class PbfAddSpareController {
    private final PbfAddSpareActivity pbfAddSpareView;
    private PbfSpareDao pbfSpareDao;


    public PbfAddSpareController(PbfAddSpareActivity pbfAddSpareActivity) {
        this.pbfAddSpareView = pbfAddSpareActivity;
        SparesDataBase db = App.getInstance().getDb();
        this.pbfSpareDao = db.pbfSparesDao();
    }

    public void addPbfSpareToDb(final PbfSpare pbfSpare) {
        Completable completable = Completable.fromAction(() -> pbfSpareDao.insertPbfSpare(pbfSpare));
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
                        Toast.makeText(pbfAddSpareView,
                                pbfAddSpareView.getResources().getText(R.string.addSpareToast),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("lol", "error");
                    }
                });
    }

}
