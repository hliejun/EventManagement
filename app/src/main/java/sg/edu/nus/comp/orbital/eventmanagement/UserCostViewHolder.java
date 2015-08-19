package sg.edu.nus.comp.orbital.eventmanagement;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

/**
 * Created by Larry on 23/6/15.
 */
public class UserCostViewHolder extends RecyclerView.ViewHolder {
    public ImageView profilePic;
    public TextView userName;
    public TextView cost;
    public EditText paid;
    public String paidAmt = null;

    public UserCostViewHolder(View itemView) {
        super(itemView);
        // Locate and cache view references:
        profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
        userName = (TextView) itemView.findViewById(R.id.userName);
        cost = (TextView) itemView.findViewById(R.id.cost);
        paid = (EditText) itemView.findViewById(R.id.paid);
        Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/GoodDog.ttf");
        userName.setTypeface(type);
        cost.setTypeface(type);
        paid.setTypeface(type);


    }

    public String getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(String paidAmt) {
        this.paidAmt = paidAmt;
    }
}