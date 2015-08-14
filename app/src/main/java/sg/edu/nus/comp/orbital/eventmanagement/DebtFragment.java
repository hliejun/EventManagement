package sg.edu.nus.comp.orbital.eventmanagement;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class DebtFragment extends Fragment {

// TODO: Change implementation for group

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager linearLayoutManager;
    protected ArrayList<Debt> debtDataList = null;
    protected SearchView searchView;

    // Default Constructor
    public DebtFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_debt, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.debt_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);

        debtDataList = ((DebtsActivity)getActivity()).getDebtList();

        // TODO: READ FROM MEMORY AND FILTER ASSETS
        adapter = new DebtsAdapter(debtDataList);
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
                    ((DebtsAdapter) recyclerView.getAdapter()).setFilterDebt(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.d("DEBUGGING", "onQueryTextChange is called");
                    ((DebtsAdapter) recyclerView.getAdapter()).setFilterDebt(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    ((DebtsAdapter) recyclerView.getAdapter()).setFilterDebt(null);
                    recyclerView.scrollToPosition(0);
                    return true;
                }
            });

            searchView.setQueryHint("Search Debts...");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
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

//    public ArrayList<User> getSelection() {
//        ArrayList<User> selection = new ArrayList<User>();
//        SparseBooleanArray indexArray = ((AddUsersAdapter)adapter).getGroupFlag();
//        int count = 0;
//        for (Group group : groupDataList) {
//            if (indexArray.get(count, false)) {
//                for (User user : group.getUsers()) {
//                    selection.add(user);
//                }
//            }
//            count++;
//        }
//        return selection;
//    }
}
