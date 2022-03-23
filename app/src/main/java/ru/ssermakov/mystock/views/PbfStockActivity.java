package ru.ssermakov.mystock.views;

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

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.PbfStockController;
import ru.ssermakov.mystock.data.room.entity.PbfSpare;
import ru.ssermakov.mystock.views.interfaces.PbfStockInterface;

public class PbfStockActivity extends AppCompatActivity implements PbfStockInterface {


    private RecyclerView recycler;
    private LayoutInflater layoutInflater;
    private PbfStockController pbfStockController;
    private List<PbfSpare> listOfPbfSpares;
    private CustomPbfAdapter pbfAdapter;
    private List<PbfSpare> listOfpbfSparesFiltered;
    private int previousExpandedPosition = -1;
    private int expandedPosition = -1;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbf_stock);

        recycler = findViewById(R.id.pbfStockRecycler);
        layoutInflater = getLayoutInflater();
        pbfStockController = new PbfStockController(this);

        getListOfSparesFromDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!pbfStockController.getListOfSelectedItems().isEmpty()) {
            getMenuInflater().inflate(R.menu.pbf_stock_delete_menu, menu);
            pbfAdapter.notifyDataSetChanged();
        } else {
            getMenuInflater().inflate(R.menu.pbf_stock_menu, menu);
        }

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.pbf_stock_find_menu).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.pbf_stock_add_menu) {
            pbfStockController.onAddPbfSpareClick(this);
        }
        if (item.getItemId() == R.id.pbf_stock_delete_menu_id) {

            Collections.sort(pbfStockController.getListOfSelectedItems(), Collections.<Integer>reverseOrder());
            for (int i = 0; i < pbfStockController.getListOfSelectedItems().size(); i++) {
                int k = pbfStockController.getListOfSelectedItems().get(i);
                PbfSpare pbfSpare = listOfPbfSpares.get(k);
                pbfStockController.deleteCaseFromDb(pbfSpare);
                listOfPbfSpares.remove(k);
            }
            pbfAdapter.notifyDataSetChanged();
            invalidateOptionsMenu();
            pbfStockController.getListOfSelectedItems().clear();
        }
        return super.onOptionsItemSelected(item);
    }


    public void getListOfSparesFromDb() {
        pbfStockController.getListOfSpares();
    }

    @Override
    public void setUpAdapterAndView(List<PbfSpare> listOfPbfSpares) {
        this.listOfPbfSpares = listOfPbfSpares;
        this.listOfpbfSparesFiltered = listOfPbfSpares;
        recycler.setLayoutManager(new LinearLayoutManager(this));
        pbfAdapter = new CustomPbfAdapter();
        recycler.setAdapter(pbfAdapter);
    }

    @Override
    public void getListOfPbfSparesFromDb() {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getListOfSparesFromDb();
    }


    private class CustomPbfAdapter extends RecyclerView.Adapter<CustomPbfAdapter.CustomPbfViewHolder>
            implements Filterable {

        String identifier;

        @Override
        public Filter getFilter() {
            return null;
        }

        @NonNull
        @Override
        public CustomPbfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View v = layoutInflater.inflate(R.layout.pbf_stock_recycler_selected, parent, false);
                return new CustomPbfViewHolder(v);
            }
            View v = layoutInflater.inflate(R.layout.pbf_stock_recycler, parent, false);
            return new CustomPbfViewHolder(v);

        }

        @Override
        public void onBindViewHolder(@NonNull CustomPbfViewHolder holder, int position) {
            PbfSpare pbfSpare = listOfPbfSpares.get(position);

            holder.name.setText(pbfSpare.getName().toUpperCase());
            holder.serialNumber.setText(pbfSpare.getSerialNumber().toUpperCase());
            holder.state.setText(pbfSpare.getState().toUpperCase());
            holder.quantity.setText(pbfSpare.getQuantity().toUpperCase());
            holder.customer.setText(pbfSpare.getCustomer().toUpperCase());
            holder.connectionType.setText(pbfSpare.getConnectionType().toUpperCase());
            holder.jiraWorkOrder.setText(pbfSpare.getJiraWorkOrder().toUpperCase());
            holder.comment.setText(pbfSpare.getComment().toUpperCase());

            final boolean isExpanded = position == expandedPosition;
            if (isExpanded) {
                holder.expandedPbfView.setVisibility(View.VISIBLE);
                holder.useButtonId.setVisibility(View.VISIBLE);
                holder.editButtonId.setVisibility(View.VISIBLE);
                holder.addButtonId.setVisibility(View.VISIBLE);
            } else {
                holder.expandedPbfView.setVisibility(View.GONE);
                holder.useButtonId.setVisibility(View.GONE);
                holder.editButtonId.setVisibility(View.GONE);
                holder.addButtonId.setVisibility(View.GONE);

            }

            holder.itemView.setActivated(isExpanded);

            if (isExpanded)
                previousExpandedPosition = position;

            holder.itemView.setOnClickListener(v -> {
                if (pbfStockController.getListOfSelectedItems().isEmpty()) {
                    expandItem(position, isExpanded);
                } else {
                    if (pbfStockController.getListOfSelectedItems().contains(holder.getAdapterPosition())) {
                        pbfStockController.removeItemFromSelected(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        invalidateOptionsMenu();
                    } else {
                        pbfStockController.selectItem(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });

            holder.itemView.setOnLongClickListener(v -> {
                pbfStockController.selectItem(holder.getAdapterPosition());
                notifyDataSetChanged();
                invalidateOptionsMenu();
                return true;
            });


        }

        private void expandItem(int position, boolean isExpanded) {
            if (isExpanded) {
                expandedPosition = -1;
            } else expandedPosition = position;

            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(position);
        }

        @Override
        public int getItemViewType(int position) {
            for (int i = 0; i < pbfStockController.getListOfSelectedItems().size(); i++) {
                if (position == pbfStockController.getListOfSelectedItems().get(i)) {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return listOfPbfSpares.size();
        }

        private class CustomPbfViewHolder extends RecyclerView.ViewHolder {

            private View expandedPbfView;
            private Button useButtonId, editButtonId, addButtonId;

            private TextView name;
            private TextView serialNumber;
            private TextView state;
            private TextView quantity;
            private TextView customer;
            private TextView connectionType;
            private TextView jiraWorkOrder;
            private TextView comment;

            public CustomPbfViewHolder(@NonNull View itemView) {
                super(itemView);

                this.name = itemView.findViewById(R.id.pbfNameTextView);
                this.serialNumber = itemView.findViewById(R.id.pbfSerialNumberTextView);
                this.state = itemView.findViewById(R.id.pbfStateTextView);
                this.quantity = itemView.findViewById(R.id.pbfQuantityTextView);
                this.customer = itemView.findViewById(R.id.pbfCustomerTextView);
                this.connectionType = itemView.findViewById(R.id.pbfConnectionTypeTextView);
                this.jiraWorkOrder = itemView.findViewById(R.id.pbfJiraWorkOrderTextView);
                this.comment = itemView.findViewById(R.id.pbfCommentTextView);

                this.expandedPbfView = itemView.findViewById(R.id.expandedPbfView);
                this.useButtonId = itemView.findViewById(R.id.useButtonId);
                this.editButtonId = itemView.findViewById(R.id.editButtonId);
                this.addButtonId = itemView.findViewById(R.id.addButtonId);


            }
        }
    }
}
