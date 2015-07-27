package sg.edu.nus.comp.orbital.eventmanagement;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AddUserContactListFragment extends Fragment {

    // TODO: Change implementation for contact list

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager linearLayoutManager;
    protected ArrayList<String> name_ref;
    protected ArrayList<String> phone_ref ;
    protected ContactPair[] contactDataList = null;
    protected SearchView searchView;
    //protected SparseBooleanArray contactFlag;

    // Obtain a list of contact names from contact list
    public ArrayList<String> getName(){
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        ArrayList<String> contacts = new ArrayList<String>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    // Obtain a list of contact number from contact list
    public ArrayList<String> getNumber(){
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
        ArrayList<String> numbers = new ArrayList<String>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numbers.add(number);
            } while (cursor.moveToNext());
        }
        return numbers;
    }

    // Default Constructor
    public AddUserContactListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_user_contact_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.add_user_contact_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);

        // Read and create ContactPair from contact list
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int size = cursor.getCount();
        if (size <= 0) {
            // ... Handle empty contact list ...
        }
        else {
            contactDataList = new ContactPair[size];
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactDataList[cursor.getPosition()] = new ContactPair(name, number);
            } while (cursor.moveToNext());
        }

        adapter = new AddUsersAdapter(contactDataList);
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
        name_ref = this.getName();
        phone_ref=this.getNumber();
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
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterContact(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.d("DEBUGGING", "onQueryTextChange is called");
                    ((AddUsersAdapter) recyclerView.getAdapter()).setFilterContact(query);
                    recyclerView.scrollToPosition(0);
                    return true;
                }

            });

            searchView.setQueryHint("Search Phone Contacts...");
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

    public ArrayList<User> getSelection() {
        ArrayList<User> selection = new ArrayList<User>();
        SparseBooleanArray indexArray = ((AddUsersAdapter)adapter).getContactFlag();
        int count = 0;
        for (ContactPair contact : contactDataList) {
            if (indexArray.get(count, false)) {
                selection.add(new User(contact.getName(), contact.getNumber()));
            }
            count++;
        }
        return selection;
    }

}
