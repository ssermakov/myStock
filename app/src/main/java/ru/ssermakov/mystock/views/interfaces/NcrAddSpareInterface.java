package ru.ssermakov.mystock.views.interfaces;

import android.content.Context;
import android.content.Intent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.mystock.data.room.entity.Spare;

public interface NcrAddSpareInterface {


    Intent createIntentForOpenExcel();

    List<Spare> createListOfSparesFromWorkBook (Context context, HSSFWorkbook workBook) throws ExecutionException, InterruptedException;

}
