package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Larry on 23/6/15.
 */
public class CreateBillViewHolder extends RecyclerView.ViewHolder {
    public ImageView profilePic;
    public TextView userName;
    public TextView itemName;
    public TextView itemCost;
    public TextView itemQuantity;

    public CreateBillViewHolder(View itemView) {
        super(itemView);
        // Locate and cache view references:
        profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
        userName = (TextView) itemView.findViewById(R.id.userName);
        itemName = (TextView) itemView.findViewById(R.id.contact_name);
        itemCost = (TextView) itemView.findViewById(R.id.cost);
        itemQuantity = (TextView) itemView.findViewById(R.id.quantity);
    }
}