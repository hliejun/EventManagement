package sg.edu.nus.comp.orbital.eventmanagement;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Larry on 23/6/15.
 */
public class BillViewHolder extends RecyclerView.ViewHolder {
    public TextView billEventTitle;
    public ImageView billIcon;
    public BillViewHolder(View itemView) {
        super(itemView);
        // Locate and cache view references:
        billEventTitle = (TextView) itemView.findViewById(R.id.billTitle);
        billIcon = (ImageView) itemView.findViewById(R.id.billIcon);

        Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/GoodDog" +
                ".ttf");
        billEventTitle.setTypeface(type);
    }
}