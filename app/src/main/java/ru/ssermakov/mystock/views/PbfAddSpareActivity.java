package ru.ssermakov.mystock.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrAddSpareController;
import ru.ssermakov.mystock.controllers.PbfAddSpareController;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;

public class PbfAddSpareActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namePbf, serialNumberPbf, statePbf, quantityPbf, customerPbf, connectionTypePbf, jiraWorkOrderPbf, commentPbf;
    private Button addPbfSpareButton;
    private PbfAddSpareController pbfAddSpareController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbf_add_spare);

        pbfAddSpareController = new PbfAddSpareController(this);

        namePbf = findViewById(R.id.namePbfEditDlgEditText);
        serialNumberPbf = findViewById(R.id.serialNumberPbfEditDlgEditText);
        statePbf = findViewById(R.id.statePbfEditDlgEditText);
        quantityPbf = findViewById(R.id.quantityPbfEditDlgEditText);
        customerPbf = findViewById(R.id.customerPbfEditDlgEditText);
        connectionTypePbf = findViewById(R.id.connectionTypePbfEditDlgEditText);
        jiraWorkOrderPbf = findViewById(R.id.jiraWorkOrderPbfEditDlgEditText);
        commentPbf = findViewById(R.id.commentPbfEditDlgEditText);

        addPbfSpareButton = findViewById(R.id.addPbfSpareButton);
//        openExcelButton = findViewById(R.id.openNcrExcelButton);

        addPbfSpareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.addPbfSpareButton) {

            String namePbf = this.namePbf.getText().toString();
            String serialNumberPbf = this.serialNumberPbf.getText().toString();
            String statePbf = this.statePbf.getText().toString();
            String quantityPbf = this.quantityPbf.getText().toString();
            String customerPbf = this.customerPbf.getText().toString();
            String connectionTypePbf = this.connectionTypePbf.getText().toString();
            String jiraWorkOrderPbf = this.jiraWorkOrderPbf.getText().toString();
            String commentPbf = this.commentPbf.getText().toString();

            pbfAddSpareController.addPbfSpareToDb(new PbfSpare(
                    namePbf,
                    serialNumberPbf,
                    statePbf,
                    customerPbf,
                    connectionTypePbf,
                    jiraWorkOrderPbf,
                    quantityPbf,
                    commentPbf
            ));
        }
    }
}