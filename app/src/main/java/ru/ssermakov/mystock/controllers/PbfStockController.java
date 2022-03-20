package ru.ssermakov.mystock.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

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
import ru.ssermakov.mystock.data.room.dao.PbfSpareDao;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.NcrAddSpareActivity;
import ru.ssermakov.mystock.views.PbfAddSpareActivity;
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
//      PbfSpare pbfSpare = new PbfSpare(
//              2,
//              "pax s920",
//              "59865824",
//              "bad",
//              "Альфа Банк",
//              "sim",
//              "Alpha-5485",
//              "1",
//              "All done");
//
//
//       Completable completable = Completable.fromAction(new Action() {
//           @Override
//           public void run() throws Exception {
//               pbfSpareDao.insertPbfSpare(pbfSpare);
//           }
//       });


       Single<List<PbfSpare>> singleGetAll = pbfSpareDao.getAll();
//       Single<List<PbfSpare>> singleGetById = pbfSpareDao.getById(1);


//       completable.subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe(new CompletableObserver() {
//                   @Override
//                   public void onSubscribe(Disposable d) {
//                       Log.d("lol", "wtf");
//                   }
//
//                   @Override
//                   public void onComplete() {
//                       Log.d("lol", "success");
////                       Toast.makeText(ncrAddSpareView,
////                               ncrAddSpareView.getResources().getText(R.string.addSpareToast),
////                               Toast.LENGTH_LONG).show();
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//                       Log.d("lol", "error");
//                   }
//               });

       singleGetAll.subscribeOn(Schedulers.io())
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

//       singleGetById.subscribeOn(Schedulers.io())
//              .observeOn(AndroidSchedulers.mainThread())
//              .subscribe(new SingleObserver<List<PbfSpare>>() {
//                 @Override
//                 public void onSubscribe(Disposable d) {
//                    Log.d("lol", "wtfGetAll");
//                 }
//
//                 @Override
//                 public void onSuccess(List<PbfSpare> spares) {
//                    Log.d("lol", "successGetAll");
//                    pbfStockView.setUpAdapterAndView(spares);
//                 }
//
//                 @Override
//                 public void onError(Throwable e) {
//                    Log.d("lol", "errorGetAll");
//                 }
//              });
   }


    public void getListOfSpares() {
        getListFromDataSource();
    }

    public void onAddPbfSpareClick(Context context) {
        Intent i = new Intent(context, PbfAddSpareActivity.class);
        context.startActivity(i);
    }

}
