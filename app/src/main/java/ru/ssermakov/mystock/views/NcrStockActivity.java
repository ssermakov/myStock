package ru.ssermakov.mystock.views;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private SearchView searchView;
    private List<Spare> listOfSparesFiltered;
    private int previousExpandedPosition = -1;
    private int expandedPosition = -1;

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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.ncr_stock_find_menu).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ncr_stock_add_menu) {
            ncrStockController.onAddSpareClick(this);
        }
        if (item.getItemId() == R.id.ncr_stock_find_menu) {
//            ncrStockController.onFindSpareClick(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpAdapterAndView(List<Spare> listOfSpares) {
        this.listOfSpares = listOfSpares;
        this.listOfSparesFiltered = listOfSpares;
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

    public CustomAdapter getAdapter() {
        return adapter;
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
            implements Filterable {


        private String identifier;

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(layoutInflater.inflate(R.layout.ncr_stock_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Spare spare = listOfSparesFiltered.get(position);

            holder.state.setText(spare.getState().toUpperCase());
            holder.partNumber.setText(spare.getPartNumber().toUpperCase());
            holder.name.setText(spare.getName().toUpperCase());
            holder.quantity.setText(spare.getQuantity().toUpperCase());
            holder.location.setText(spare.getLocation().toUpperCase());
            holder.rework.setText(spare.getReturnCode().toUpperCase());

            final boolean isExpanded = position == expandedPosition;
            if (isExpanded) {
                holder.details.setVisibility(View.VISIBLE);
                holder.useButton.setVisibility(View.VISIBLE);
                holder.editButton.setVisibility(View.VISIBLE);
                holder.addButton.setVisibility(View.VISIBLE);
            } else {
                holder.details.setVisibility(View.GONE);
                holder.editButton.setVisibility(View.GONE);
                holder.useButton.setVisibility(View.GONE);
                holder.addButton.setVisibility(View.GONE);

            }

            holder.itemView.setActivated(isExpanded);

            if (isExpanded)
                previousExpandedPosition = position;

            holder.itemView.setOnClickListener(v -> {
                expandItem(position, isExpanded);
                ncrStockController.copyPartNumberToClipBoard(holder.partNumber.getText().toString(), NcrStockActivity.this);
            });

            holder.useButton.setOnClickListener(v -> {
                identifier = "onUse";
                ncrStockController.onUseClickButton(holder, spare, identifier, NcrStockActivity.this);
            });
            holder.addButton.setOnClickListener(v -> {
                identifier = "onAdd";
                ncrStockController.onAddClickButton(holder, spare, identifier, NcrStockActivity.this);
            });
            holder.editButton.setOnClickListener(v -> ncrStockController.onEditClickButton(spare, NcrStockActivity.this));
        }

        @Override
        public int getItemCount() {
            return listOfSparesFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        listOfSparesFiltered = listOfSpares;
                    } else {
                        List<Spare> filteredList = new ArrayList<>();
                        for (Spare spare : listOfSpares) {
                            if (spare.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                    spare.getLocation().toLowerCase().contains(charString.toLowerCase()) ||
                                    spare.getPartNumber().toLowerCase().contains(charString.toLowerCase()) ||
                                    spare.getQuantity().toLowerCase().contains(charString.toLowerCase()) ||
                                    spare.getReturnCode().toLowerCase().contains(charString.toLowerCase()) ||
                                    spare.getState().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(spare);
                            }
                        }
                        listOfSparesFiltered = filteredList;
                    }
                    FilterResults results = new FilterResults();
                    results.values = listOfSparesFiltered;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    listOfSparesFiltered = (List<Spare>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        private void copyPartNumberToClipBoard(CustomViewHolder holder, NcrStockActivity ncrStockActivity) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("partnumber", holder.partNumber.getText().toString());
            clipboardManager.setPrimaryClip(data);

            Toast.makeText(getApplicationContext(), "Text Copied : " + holder.partNumber.getText().toString(), Toast.LENGTH_SHORT).show();
        }

        private void expandItem(int position, boolean isExpanded) {
            if (isExpanded) {
                expandedPosition = -1;
            } else expandedPosition = position;

            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(position);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            public View details;
            private TextView state;
            public TextView partNumber;
            private TextView name;
            private TextView quantity;
            private TextView location;
            private TextView rework;
            private Button useButton, editButton, addButton;

            public CustomViewHolder(View itemView) {
                super(itemView);

                this.state = itemView.findViewById(R.id.stateTextView);
                this.partNumber = itemView.findViewById(R.id.pnTextView);
                this.name = itemView.findViewById(R.id.nameTextView);
                this.quantity = itemView.findViewById(R.id.quantityTextView);
                this.location = itemView.findViewById(R.id.locationTextView);
                this.rework = itemView.findViewById(R.id.reworkCodetextView);
                this.details = itemView.findViewById(R.id.expandedView);
                this.useButton = itemView.findViewById(R.id.useButton);
                this.editButton = itemView.findViewById(R.id.editEditDlgButton);
                this.addButton = itemView.findViewById(R.id.addButton);


            }
        }
    }
}
