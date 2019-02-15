package ru.ssermakov.mystock.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import ru.ssermakov.mystock.R;

public class NcrStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncr_stock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
getMenuInflater().inflate(R.menu.ncr_stock_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
