package ru.ssermakov.mystock.views.interfaces;

import java.util.List;

import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.data.room.entity.Spare;

public interface PbfStockInterface {
 void setUpAdapterAndView(List<PbfSpare> listOfPbfSpares);

 void getListOfPbfSparesFromDb();
}
