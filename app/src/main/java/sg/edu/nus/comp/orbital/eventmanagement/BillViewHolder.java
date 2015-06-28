package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Larry on 23/6/15.
 */
public class BillViewHolder extends RecyclerView.ViewHolder {
    public TextView billEventTitle;

    public BillViewHolder(View itemView) {
        super(itemView);
        // Locate and cache view references:
        billEventTitle = (TextView) itemView.findViewById(R.id.eventTitle);
    }
}