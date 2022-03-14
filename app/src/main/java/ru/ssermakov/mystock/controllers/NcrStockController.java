package ru.ssermakov.mystock.controllers;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
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
import ru.ssermakov.mystock.views.NcrStockActivity;
import ru.ssermakov.mystock.views.fragments.EditPickerFragment;
import ru.ssermakov.mystock.views.fragments.QuantityPickerFragment;

public class NcrStockController {

    public NcrStockActivity ncrStockView;

    private SpareDao spareDao;

    public NcrStockController(NcrStockActivity ncrStockView) {
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
                        ncrStockView.getListOfSparesFromDb();
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

    public void onUseClickButton(NcrStockActivity.CustomAdapter.CustomViewHolder holder, Spare spare, String identifier, NcrStockActivity ncrStockActivity) {
        createPickerFragment(spare, identifier, ncrStockActivity);
    }

    public void onAddClickButton(NcrStockActivity.CustomAdapter.CustomViewHolder holder, Spare spare, String identifier, NcrStockActivity ncrStockActivity) {
        createPickerFragment(spare, identifier, ncrStockActivity);
    }

    public void onEditClickButton(Spare spare, NcrStockActivity ncrStockActivity) {
        EditPickerFragment fragment = new EditPickerFragment();
        fragment.setSpare(spare);
        fragment.setNcrStockController(this);
        fragment.show(ncrStockActivity.getFragmentManager(), "pick quantity");
    }

    private void createPickerFragment(Spare spare, String identifier, NcrStockActivity ncrStockActivity) {
        QuantityPickerFragment fragment = new QuantityPickerFragment();
        fragment.setSpare(spare);
        fragment.setNcrStockController(this);
        fragment.setIdentifier(identifier);
        fragment.show(ncrStockActivity.getFragmentManager(), "pick quantity");
    }


    public void copyPartNumberToClipBoard(String string, NcrStockActivity ncrStockActivity) {
        ClipboardManager clipboardManager = (ClipboardManager) ncrStockActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("partnumber", string);
        clipboardManager.setPrimaryClip(data);

        Toast.makeText(ncrStockActivity.getApplicationContext(),"Text Copied : " + string, Toast.LENGTH_SHORT).show();
    }

    public String makeStringForWriteOff(int value, String partNumber) {
        return partNumber + "(" + Integer.toString(value) + ")" + "(b)";
    }
}
