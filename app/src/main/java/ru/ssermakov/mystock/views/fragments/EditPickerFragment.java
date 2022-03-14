package ru.ssermakov.mystock.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrStockController;
import ru.ssermakov.mystock.data.room.entity.Spare;

public class EditPickerFragment extends DialogFragment {
    private Spare spare;
    private NcrStockController ncrStockController;

    private AlertDialog dialog;

    public void setSpare(Spare spare) {
        this.spare = spare;
    }

    public Spare getSpare() {
        return spare;
    }

    public void setNcrStockController(NcrStockController ncrStockController) {
        this.ncrStockController = ncrStockController;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.edit_dialog, null);

        Button buttonEdit = v.findViewById(R.id.editEditDlgButton);
        Button buttonCancel = v.findViewById(R.id.cancelEditDlgButton);
        TextView stateTextView = v.findViewById(R.id.stateEditDlgEditText);
        TextView pnTextView = v.findViewById(R.id.pnEditDlgEditText);
        TextView nameTextView = v.findViewById(R.id.nameEditDlgEditText);
        TextView quantityTextView = v.findViewById(R.id.quantityEditDlgEditText);
        TextView reworkTextView = v.findViewById(R.id.reworkEditDlgEditText);
        TextView locationTextView = v.findViewById(R.id.locationEditDlgEditText);

        stateTextView.setText(spare.getState());
        pnTextView.setText(spare.getPartNumber());
        nameTextView.setText(spare.getName());
        quantityTextView.setText(spare.getQuantity());
        reworkTextView.setText(spare.getReturnCode());
        locationTextView.setText(spare.getLocation());

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(v)
                .setTitle("Edit spare");
        dialog = builder.create();

        buttonEdit.setOnClickListener(v1 -> {
            spare.setState(stateTextView.getText().toString().toUpperCase());
            spare.setPartNumber(pnTextView.getText().toString().toUpperCase());
            spare.setName(nameTextView.getText().toString().toUpperCase());
            spare.setQuantity(quantityTextView.getText().toString().toUpperCase());
            spare.setReturnCode(reworkTextView.getText().toString().toUpperCase());
            spare.setLocation(locationTextView.getText().toString().toUpperCase());
            edit();
            dialog.cancel();
        });

        buttonCancel.setOnClickListener(v12 -> dialog.cancel());


        return dialog;


    }

    private void edit() {
        ncrStockController.updateSpare(spare);
    }


}