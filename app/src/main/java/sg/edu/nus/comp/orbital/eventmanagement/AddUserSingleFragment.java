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

public class AddUserSingleFragment extends Fragment {

    // TODO: Change implementation for single

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager linearLayoutManager;
    protected User[] userDataList = null;
    protected SearchView searchView;
    protected TextView emptyView;
    User[] userArray = null;

    protected ArrayList<User> myUsers = new ArrayList<User>();
    final static private String USERS_FILENAME = "users.xml";

    // Default Constructor
    public AddUserSingleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_user_single, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.add_user_single_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setScrollContainer(true);
        setHasOptionsMenu(true);

//        //TODO: PREPARE DATASET (load from database) AND PASS INTO ADAPTER AS A LIST OR WHATNOT
//        User user1 = new User("Abby", "93260235");
//        User user2 = new User("Barry", "93234235");
//        User user3 = new User("Cassidy", "82633463");
//        User user4 = new User("Diddy", "93269805");
//        User user5 = new User("Elany", "82364235");
//        User user6 = new User("Fairy", "93237235");
//        User user7 = new User("Gary", "93755325");
//        User user8 = new User("Hairy", "91415235");
//        User user9 = new User("Iany", "93867535");
//        User user10 = new User("Jerry", "93415135");
//
//        userDataList = new User[10];
//        userDataList[0] = user1;
//        userDataList[1] = user2;
//        userDataList[2] = user3;
//        userDataList[3] = user4;
//        userDataList[4] = user5;
//        userDataList[5] = user6;
//        userDataList[6] = user7;
//        userDataList[7] = user8;
//        userDataList[8] = user9;
//        userDataList[9] = user10;

        readUsersFromFile();

        if (myUsers == null) {
            myUsers = new ArrayList<User>();
        }

        userArray = new User[myUsers.size()];

        myUsers.toArray(userArray);

        Arrays.sort(userArray);

        adapter = new AddUsersAdapter(userArray);
        recyclerView.setAdapter(adapter);

        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        Typeface type = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/GoodDog.ttf");

        if (userArray == null || userArray.length == 0) {
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
        //searchMenuItem.expandActionView();

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
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterUser(query);
                    recyclerView.scrollToPosition(0);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService
                            (Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    //searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.d("DEBUGGING", "onQueryTextChange is called");
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterUser(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

            });
            searchView.setQueryHint("Search Users...");
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
        SparseBooleanArray indexArray = ((AddUsersAdapter)adapter).getUserFlag();
        int count = 0;

        if (userArray == null) {
            userArray = new User[0];
        }
        for (User user : userArray) {
            if (indexArray.get(count, false)) {
                selection.add(user);
            }
            count++;
        }
        return selection;
    }

    public boolean writeUsersToFile(User user) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            if (myUsers == null) {
                myUsers = new ArrayList<User>();
            }
            myUsers.add(user);
            fos = getActivity().getApplicationContext().openFileOutput(USERS_FILENAME, Context
                    .MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(myUsers);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("USERS", "Can't save users" + e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("USERS", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readUsersFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getActivity().getApplicationContext().openFileInput(USERS_FILENAME);
            ois = new ObjectInputStream(fin);
            myUsers =(ArrayList<User>)ois.readObject();
            ois.close();
            Log.d("USERS", "Users read successfully");
            return true;
        }catch(Exception e){
            Log.d("USERS", "Cant read saved users"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("USERS", "Error in closing stream while reading users" + e
                            .getMessage());
                }
        }
    }
}
