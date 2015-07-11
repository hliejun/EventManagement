package sg.edu.nus.comp.orbital.eventmanagement;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AddUserSingleFragment extends Fragment {
    // TODO: Change implementation for single users
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_PAGE = "User";
    protected int mPage;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected ArrayList<String> name_ref;
    protected ArrayList<String> phone_ref ;
    protected ArrayList<User> selected_contacts = new ArrayList<>();
//  protected OnFragmentInteractionListener mListener;

    public ArrayList<String> getContact(){
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

    public String matchPhoneByName(String name){
        int pos = -1;
        for(int c = 0; c<=name_ref.size()-1;c++){
            if(name.equals(name_ref.get(c))){
                pos = c;
                break;
            }
        }
        return phone_ref.get(pos);
    }

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

    public void doStuff(View view){
        TextView tv = (TextView) view.findViewById(R.id.item);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        if(tv.isSelected()){
            String person_name = tv.getText().toString();
            String person_phone = matchPhoneByName(person_name);
            User user = new User(person_name, person_phone);
            selected_contacts.add(user);
        }else if(!tv.isSelected() && selected_contacts.size()!=0){
            String person_name = tv.getText().toString();
            for(int c = 0; c<= selected_contacts.size()-1;c++){
                if(selected_contacts.get(c).getUserName().equals(person_name)){
                    selected_contacts.remove(c);
                }
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddUserContactListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUserSingleFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AddUserSingleFragment fragment = new AddUserSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddUserSingleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name_ref = this.getContact();
        phone_ref=this.getNumber();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new AddUsersAdapter(getContact());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_user_single, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.add_user_single_recycler_view);

        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

}
