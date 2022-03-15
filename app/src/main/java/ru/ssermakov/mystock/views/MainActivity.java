package ru.ssermakov.mystock.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.MainController;
import ru.ssermakov.mystock.views.interfaces.MainActivityInterface;

public class MainActivity extends AppCompatActivity implements
        MainActivityInterface,
        View.OnClickListener {

    private TextView ncrTextView, pbfTextView;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = new MainController(this);

        ncrTextView = findViewById(R.id.ncrTextView);
        ncrTextView.setOnClickListener(this);

        pbfTextView = findViewById(R.id.pbfTextView);
        pbfTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.ncrTextView){
            startNcrStockActivity();
        }
        if (viewId == R.id.pbfTextView){
            startPbfStockActivity();
        }
    }

    @Override
    public void startNcrStockActivity() {
        mainController.onNcrTextViewClick(this);
    }

    @Override
    public void startPbfStockActivity() {
        mainController.onPbfTextViewClick(this);
    }
}
