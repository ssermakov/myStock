package ru.ssermakov.mystock.controllers;

import android.content.Context;
import android.content.Intent;

import ru.ssermakov.mystock.views.NcrStockActivity;
import ru.ssermakov.mystock.views.interfaces.MainActivityInterface;

public class MainController {

    private MainActivityInterface mainActivityView;

    public MainController(MainActivityInterface mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    public void onNcrTextViewClick(Context context) {
        Intent i = new Intent(context, NcrStockActivity.class);
        context.startActivity(i);
    }
}
