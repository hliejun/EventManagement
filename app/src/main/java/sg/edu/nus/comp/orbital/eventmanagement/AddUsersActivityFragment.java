//package sg.edu.nus.comp.orbital.eventmanagement;
//
//import android.database.Cursor;
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;
//import android.widget.AdapterView;
//import android.util.Log;
//
//import android.provider.ContactsContract;
//import android.widget.ArrayAdapter;
//import android.content.Context;
//
//import java.util.ArrayList;
//
//
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class AddUsersActivityFragment extends Fragment {
//
//    public AddUsersActivityFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        ArrayList<String> nameList = new ArrayList<String>();
//        ArrayList<String> phoneList = new ArrayList<String>();
//
//
//        try {
//            while (phone.moveToNext()) {
//                String userName = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                nameList.add(userName);
//                phoneList.add(phoneNumber);
//            }
//          }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            phone.close();
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.contacts, R.id.contactList, nameList);
//
//        return inflater.inflate(R.layout.fragment_add_users, container, false);
//    }
//}
