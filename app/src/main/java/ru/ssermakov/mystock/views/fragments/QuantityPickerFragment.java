package ru.ssermakov.mystock.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Objects;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrStockController;
import ru.ssermakov.mystock.data.room.entity.Spare;

public class QuantityPickerFragment extends DialogFragment {
    private Spare spare;
    private NcrStockController ncrStockController;

    private AlertDialog dialog;
    private String identidier;
    public static final String ON_USE = "onUse";
    public static final String ON_ADD = "onAdd";

    public void setSpare(Spare spare) {
        this.spare = spare;
    }

    public Spare getSpare() {
        return spare;
    }

    public void setNcrStockController(NcrStockController ncrStockController) {
        this.ncrStockController = ncrStockController;
    }

    public void setIdentifier(String identifier) {
        this.identidier = identifier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.picker_dialog, null);
        NumberPicker picker = v.findViewById(R.id.numberPicker);
        Button buttonSet = v.findViewById(R.id.buttonSet);
        Button buttonCancel = v.findViewById(R.id.buttonCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(v);
        if (identidier.equals(ON_USE))builder.setTitle("use spare");
        if (identidier.equals(ON_ADD))builder.setTitle("add spare");
        dialog = builder.create();

        buttonSet.setOnClickListener(v1 -> {
            Integer currentAmount = Integer.valueOf(spare.getQuantity());
            if (identidier.equals(ON_USE)) update(picker, currentAmount);
            if (identidier.equals(ON_ADD)) add(picker, currentAmount);
            dialog.cancel();
        });

        buttonCancel.setOnClickListener(v12 -> dialog.cancel());

        picker.setMaxValue(100);
        picker.setMinValue(0);
        picker.setValue(1);

        return dialog;


    }

    private void add(NumberPicker picker, Integer currentAmount) {
        int newAmount = currentAmount + picker.getValue();
        spare.setQuantity(Integer.toString(newAmount));
        ncrStockController.updateSpare(spare);
    }

    private void update(NumberPicker picker, Integer currentAmount) {
        int newAmount = currentAmount - picker.getValue();
        spare.setQuantity(Integer.toString(newAmount));
        ncrStockController.updateSpare(spare);
    }

}