package ru.ssermakov.mystock.controllers;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

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

    private static final String TAG = "EXCEL";

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

    public ArrayList<Spare> getSpares(HSSFWorkbook workBook) throws InterruptedException, ExecutionException {
        CreateListOfSparesTask task = new CreateListOfSparesTask();
        task.execute(workBook);
        return task.get();
    }

    private class CreateListOfSparesTask extends AsyncTask<HSSFWorkbook, Void, ArrayList<Spare>> {

        @Override
        protected ArrayList<Spare> doInBackground(HSSFWorkbook... hssfWorkbooks) {
            HSSFSheet mySheet = hssfWorkbooks[0].getSheetAt(0);
            Iterator rowIterator = mySheet.rowIterator();
            ArrayList<Spare> spares = new ArrayList<>();
            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();
                int cellCounter = 0;
                Spare spare = new Spare();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();
                    if (cell.getCellType() == CellType.NUMERIC) {
                        cell.setCellType(CellType.STRING);
                    }
                    Log.d(TAG, "Cell value: " + cell.toString());
                    if (cellCounter == 0) {
                        String partNumber = validatePartNumber(cell.toString());
                        spare.setPartNumber(partNumber);
                    }
                    if (cellCounter == 1) {
                        String name = cell.toString();
                        spare.setName(name);
                    }
                    if (cellCounter == 2) {
                        String state = cell.toString();
                        spare.setState(state);
                    }
                    if (cellCounter == 3) {
                        String quantity = cell.toString();
                        spare.setQuantity(quantity);
                    }
                    if (cellCounter == 4) {
                        String returnCode = cell.toString();
                        spare.setReturnCode(returnCode);
                    }
                    if (!cellIterator.hasNext()) {
                        spare.setLocation("garage");
                        spares.add(spare);
                        spareDao.insertSpare(spare);
                    }
                    cellCounter++;
                }
            }
            return spares;
        }

        @Override
        protected void onPostExecute(ArrayList<Spare> spares) {
            super.onPostExecute(spares);
            Toast.makeText(ncrAddSpareView, "List od spares created successfully", Toast.LENGTH_SHORT).show();
        }


    }

    private String validatePartNumber(String partNumber) {
        if (partNumber.length() < 10) {
            return "00" + partNumber;
        }
        return partNumber;
    }
}
