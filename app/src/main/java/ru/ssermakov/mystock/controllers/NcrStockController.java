package ru.ssermakov.mystock.controllers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
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
import ru.ssermakov.mystock.views.NcrStockActivity;
import ru.ssermakov.mystock.views.fragments.QuantityPickerFragment;
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
        getAll();
    }

    private void getAll() {
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

    public void updateSpare(Spare spare) {
        Completable.fromRunnable(() -> spareDao.update(spare))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("update", "update");
                    }

                    @Override
                    public void onError(Throwable e) {

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

    public void onUseClickButton(NcrStockActivity.CustomAdapter.CustomViewHolder holder, Spare spare, NcrStockActivity ncrStockActivity) {
        QuantityPickerFragment fragment = new QuantityPickerFragment();
        fragment.setSpare(spare);
        fragment.setNcrStockController(this);
        fragment.show(ncrStockActivity.getFragmentManager(), "pick quantity");
    }

}
