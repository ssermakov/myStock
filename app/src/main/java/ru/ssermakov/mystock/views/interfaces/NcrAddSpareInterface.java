package ru.ssermakov.mystock.views.interfaces;

import android.content.Context;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

import ru.ssermakov.mystock.data.room.entity.Spare;

public interface NcrAddSpareInterface {


    void createIntentForOpenExcel();

    List<Spare> createListOfSparesFromWorkBook (Context context, HSSFWorkbook workBook);

}
