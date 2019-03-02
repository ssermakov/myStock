package ru.ssermakov.mystock.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.ssermakov.mystock.app.App;
import ru.ssermakov.mystock.data.room.SparesDataBase;
import ru.ssermakov.mystock.data.room.dao.SpareDao;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.NcrAddSpareActivity;
import ru.ssermakov.mystock.views.interfaces.NcrStockInterface;

public class NcrStockController {

    private NcrStockInterface ncrStockView;

    private SpareDao spareDao;

    public NcrStockController(NcrStockInterface ncrStockView) {
        this.ncrStockView = ncrStockView;
        SparesDataBase db = App.getInstance().getDb();
        this.spareDao = db.spareDao();
    }


    private void getListFromDataSource() {
        rxJavaMethod();
    }

    private void rxJavaMethod() {
        Single<List<Spare>> single = spareDao.getAll();

        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Spare>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("lol", "wtfGetAll");
                    }

                    @Override
                    public void onSuccess(List<Spare> spares) {
                        Log.d("lol", "successGetAll");
                        ncrStockView.setUpAdapterAndView(spares);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("lol", "errorGetAll");
                    }
                });
    }


    //
    public void onAddSpareClick(Context context) {
        Intent i = new Intent(context, NcrAddSpareActivity.class);
        context.startActivity(i);
    }


    public void getListOfSpares() {
        getListFromDataSource();
    }
}
