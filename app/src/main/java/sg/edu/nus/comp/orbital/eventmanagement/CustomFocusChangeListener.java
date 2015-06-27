package sg.edu.nus.comp.orbital.eventmanagement;

import android.view.View.OnFocusChangeListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

/**
 * Created by Larry on 27/6/15.
 */
public class CustomFocusChangeListener implements OnFocusChangeListener {

    public void onFocusChange(View v, boolean hasFocus) {

        if(v.getId() == R.id.paid && !hasFocus) {
            InputMethodManager imm =  (InputMethodManager) ContextManager.context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}