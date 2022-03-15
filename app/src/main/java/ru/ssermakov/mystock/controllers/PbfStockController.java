package ru.ssermakov.mystock.controllers;

import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.ssermakov.mystock.app.App;
import ru.ssermakov.mystock.data.room.SparesDataBase;
import ru.ssermakov.mystock.data.room.dao.PbfSpareDao;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.PbfStockActivity;

public class PbfStockController {

   public PbfStockActivity pbfStockView;
   private PbfSpareDao pbfSpareDao;

   public PbfStockController(PbfStockActivity pbfStockView) {
      this.pbfStockView = pbfStockView;
      SparesDataBase db = App.getInstance().getDb();
      this.pbfSpareDao = db.pbfSparesDao();
   }

   private void getListFromDataSource() {
      getAll();
   }

   private void getAll() {
      Single<List<PbfSpare>> single = pbfSpareDao.getAll();

      single.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new SingleObserver<List<PbfSpare>>() {
                 @Override
                 public void onSubscribe(Disposable d) {
                    Log.d("lol", "wtfGetAll");
                 }

                 @Override
                 public void onSuccess(List<PbfSpare> spares) {
                    Log.d("lol", "successGetAll");
                    pbfStockView.setUpAdapterAndView(spares);
                 }

                 @Override
                 public void onError(Throwable e) {
                    Log.d("lol", "errorGetAll");
                 }
              });
   }


    public void getListOfSpares() {
        getListFromDataSource();
    }
}
