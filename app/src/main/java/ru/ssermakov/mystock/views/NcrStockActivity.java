package ru.ssermakov.mystock.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrStockController;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.interfaces.NcrStockInterface;

public class NcrStockActivity extends AppCompatActivity implements NcrStockInterface {

    private List<Spare> listOfSpares;
    private LayoutInflater layoutInflater;
    private RecyclerView recycler;
    private CustomAdapter adapter;
    private NcrStockController ncrStockController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncr_stock);

        recycler = findViewById(R.id.ncrStockRecycler);
        layoutInflater = getLayoutInflater();
        ncrStockController = new NcrStockController(this);

        getListOfSparesFromDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ncr_stock_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ncr_stock_menu) {
            ncrStockController.onAddSpareClick(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpAdapterAndView(List<Spare> listOfSpares) {
        this.listOfSpares = listOfSpares;
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);

    }

    @Override
    public void getListOfSparesFromDb() {
        ncrStockController.getListOfSpares();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getListOfSparesFromDb();
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {


        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(layoutInflater.inflate(R.layout.ncr_stock_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Spare spare = listOfSpares.get(position);
            holder.state.setText(spare.getState());
            holder.partNumber.setText(spare.getPartNumber());
            holder.name.setText(spare.getName());
            holder.quantity.setText(spare.getQuantity());
        }

        @Override
        public int getItemCount() {
            return listOfSpares.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            private TextView state;
            private TextView partNumber;
            private TextView name;
            private TextView quantity;

            public CustomViewHolder(View itemView) {
                super(itemView);

                this.state = itemView.findViewById(R.id.stateTextView);
                this.partNumber = itemView.findViewById(R.id.pnTextView);
                this.name = itemView.findViewById(R.id.nameTextView);
                this.quantity = itemView.findViewById(R.id.quantityTextView);

            }
        }
    }
}
