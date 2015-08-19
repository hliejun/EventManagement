package sg.edu.nus.comp.orbital.eventmanagement;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class AssetFragment extends Fragment {

// TODO: Change implementation for group

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager linearLayoutManager;
    protected ArrayList<Debt> assetDataList = null;
    protected SearchView searchView;
    protected TextView emptyView;

    // Default Constructor
    public AssetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_asset, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.asset_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setScrollContainer(true);
        setHasOptionsMenu(true);

        assetDataList = ((DebtsActivity)getActivity()).getAssetList();

        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        Typeface type = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/GoodDog.ttf");

        if (assetDataList == null || assetDataList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setTypeface(type);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        // TODO: READ FROM MEMORY AND FILTER DEBTS
        adapter = new AssetsAdapter(assetDataList);


        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            linearLayoutManager = savedInstanceState.getParcelable("STATE_KEY");
        }
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        searchView.setQuery("", true);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        inflater.inflate(R.menu.menu_debts, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menuItem);



        final LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);

        //Give the LinearLayout a transition animation.
        searchBar.setLayoutTransition(new LayoutTransition());
        ActionBar.LayoutParams p= new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        searchView.setLayoutParams(p);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        if (searchView != null) {
            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean queryTextFocused) {
                    //Log.d("DEBUGGING", "FOCUS CHANGE is called");
                    if (!queryTextFocused) {
                        MenuItemCompat.collapseActionView(menuItem);
                    }
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    ((AssetsAdapter) recyclerView.getAdapter()).setFilterDebt(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.d("DEBUGGING", "onQueryTextChange is called");
                    ((AssetsAdapter) recyclerView.getAdapter()).setFilterDebt(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    ((AssetsAdapter) recyclerView.getAdapter()).setFilterDebt(null);
                    recyclerView.scrollToPosition(0);
                    return true;
                }
            });

            searchView.setQueryHint("Search Assets...");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
        state.putParcelable("STATE_KEY", linearLayoutManager.onSaveInstanceState());
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
