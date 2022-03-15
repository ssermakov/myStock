package ru.ssermakov.mystock.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.PbfStockController;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.views.interfaces.PbfStockInterface;

public class PbfStockActivity extends AppCompatActivity implements PbfStockInterface {


    private RecyclerView recycler;
    private LayoutInflater layoutInflater;
    private PbfStockController pbfStockController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbf_stock);

        recycler = findViewById(R.id.pbfStockRecycler);
        layoutInflater = getLayoutInflater();
        pbfStockController = new PbfStockController(this);

        getListOfSparesFromDb();
    }

    public void getListOfSparesFromDb() {
        pbfStockController.getListOfSpares();
    }

    @Override
    public void setUpAdapterAndView(List<PbfSpare> listOfPbfSpares) {

    }

    @Override
    public void getListOfPbfSparesFromDb() {

    }
}
