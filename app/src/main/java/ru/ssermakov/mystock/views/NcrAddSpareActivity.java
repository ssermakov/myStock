package ru.ssermakov.mystock.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrAddSpareController;
import ru.ssermakov.mystock.data.room.entity.Spare;

public class NcrAddSpareActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText state, pn, desc, quantity, reworkCode;
    Button addSpareButton;
    NcrAddSpareController ncrAddSpareController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncr_add_spare);

        ncrAddSpareController = new NcrAddSpareController(this);

        state = findViewById(R.id.stateNcrAddEditText);
        pn = findViewById(R.id.pnNcrAddSpareEditText);
        desc = findViewById(R.id.nameNcrAddSpareEditText);
        quantity = findViewById(R.id.quantityNcrAddSpareEditText);
        reworkCode = findViewById(R.id.reworkNcrAddSpareEditText);

        addSpareButton = findViewById(R.id.addNcrSpareButton);

        addSpareButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.addNcrSpareButton) {

            String state = this.state.getText().toString();
            String pn = this.pn.getText().toString();
            String desc = this.desc.getText().toString();
            String quantity = this.quantity.getText().toString();
            String rework = this.reworkCode.getText().toString();

            ncrAddSpareController.addSpareToDb(new Spare(
                    desc,
                    pn,
                    state,
                    rework,
                    quantity
            ));
        }
    }
}
