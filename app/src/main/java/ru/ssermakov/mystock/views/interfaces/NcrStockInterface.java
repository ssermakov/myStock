package ru.ssermakov.mystock.views.interfaces;

import java.util.List;

import ru.ssermakov.mystock.data.room.entity.Spare;

public interface NcrStockInterface {

    void setUpAdapterAndView(List<Spare> listOfSpares);

    void getListOfSparesFromDb();
}
