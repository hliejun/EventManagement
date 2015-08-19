package sg.edu.nus.comp.orbital.eventmanagement;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class AddUserGroupFragment extends Fragment /*implements SearchView.OnQueryTextListener,
        android.widget.SearchView.OnCloseListener */ {

    // TODO: Change implementation for group

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager linearLayoutManager;
    protected Group[] groupDataList = null;
    protected SearchView searchView;
    protected TextView emptyView;
    protected Group[] groupArray = null;

    protected ArrayList<Group> myGroups = new ArrayList<Group>();
    final static private String GROUPS_FILENAME = "groups.xml";

    // Default Constructor
    public AddUserGroupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_user_group, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.add_user_group_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setScrollContainer(true);
        setHasOptionsMenu(true);

//        //TODO: PREPARE DATASET (Load from database) AND PASS INTO ADAPTER AS A LIST OR WHATNOT
//        Group group1 = new Group("Group 1");
//        Group group2 = new Group("Group 2");
//        Group group3 = new Group("Group 3");
//        Group group4 = new Group("Group 4");
//        Group group5 = new Group("Group 5");
//        Group group6 = new Group("Group 6");
//        Group group7 = new Group("Group 7");
//        Group group8 = new Group("Group 8");
//        Group group9 = new Group("Group 9");
//        Group group10 = new Group("Group 10");
//        Group group11 = new Group("Group 11");
//        Group group12 = new Group("Group 12");
//
//        groupDataList = new Group[12];
//        groupDataList[0] = group1;
//        groupDataList[1] = group2;
//        groupDataList[2] = group3;
//        groupDataList[3] = group4;
//        groupDataList[4] = group5;
//        groupDataList[5] = group6;
//        groupDataList[6] = group7;
//        groupDataList[7] = group8;
//        groupDataList[8] = group9;
//        groupDataList[9] = group10;
//        groupDataList[10] = group11;
//        groupDataList[11] = group12;

        readGroupsFromFile();

        if (myGroups == null) {
            myGroups = new ArrayList<Group>();
        }

        groupArray = new Group[myGroups.size()];

        myGroups.toArray(groupArray);

        Arrays.sort(groupArray);

        adapter = new AddUsersAdapter(groupArray);
        recyclerView.setAdapter(adapter);

        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        Typeface type = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/GoodDog.ttf");

        if (groupArray == null || groupArray.length == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setTypeface(type);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
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
        inflater.inflate(R.menu.menu_add_users, menu);

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
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterGroup(query);
                    recyclerView.scrollToPosition(0);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService
                            (Context
                                    .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.d("DEBUGGING", "onQueryTextChange is called");
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterGroup(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterGroup(null);
                    recyclerView.scrollToPosition(0);
                    return true;
                }
            });

            searchView.setQueryHint("Search Groups...");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_confirm) {
            ((AddUsersActivity)getActivity()).confirmUsers();
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

    public ArrayList<User> getSelection() {
        ArrayList<User> selection = new ArrayList<User>();
        SparseBooleanArray indexArray = ((AddUsersAdapter)adapter).getGroupFlag();
        int count = 0;
        if (groupArray == null) {
            groupArray = new Group[0];
        }
        for (Group group : groupArray) {
            if (indexArray.get(count, false)) {
                for (User user : group.getUsers()) {
                    selection.add(user);
                }
            }
            count++;
        }
        return selection;
    }

    public boolean writeGroupsToFile(Group group) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            if (myGroups == null) {
                myGroups = new ArrayList<Group>();
            }
            myGroups.add(group);
            fos = getActivity().getApplicationContext().openFileOutput(GROUPS_FILENAME, Context
                    .MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(myGroups);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("GROUPS", "Can't save groups" + e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("GROUPS", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readGroupsFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getActivity().getApplicationContext().openFileInput(GROUPS_FILENAME);
            ois = new ObjectInputStream(fin);
            myGroups =(ArrayList<Group>)ois.readObject();
            ois.close();
            Log.d("GROUPS", "Groups read successfully");
            return true;
        }catch(Exception e){
            Log.d("GROUPS", "Cant read saved groups"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("GROUPS", "Error in closing stream while reading groups" + e
                            .getMessage());
                }
        }
    }
}
