package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

// Custom view for displaying of bill
public class CreateBillView extends RelativeLayout {
    // Required Backend Objects (For Particular Bill)
    Purchase myPurchase = null;

    // View Components
    private ImageView profilePic = null;
    private TextView userName = null;
    private TextView itemName = null;
    private TextView itemCost = null;
    private TextView itemQuantity = null;

    // Custom View Attributes
    boolean mShowText = false;
    int mTextPos = 0;

    // Constructor
    public CreateBillView(Context context, AttributeSet attrs) {

        // Call Parent Constructor
        super(context, attrs);

        // Attributes Handling
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CreateBillView,
                0, 0);

        // Set Attributes (Examples)
        try {
            mShowText = attributesArray.getBoolean(R.styleable
                            .CreateBillView_createBillDisplayText,
                    false);
            mTextPos = attributesArray.getInteger(R.styleable
                    .CreateBillView_createBillLabelPosition, 0);
        } finally {
            attributesArray.recycle();
        }

        // Initialize Components
        LayoutInflater.from(context).inflate(R.layout.purchase, this, true);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        userName = (TextView) findViewById(R.id.userName);
        itemName = (TextView) findViewById(R.id.contact_name);
        itemCost = (TextView) findViewById(R.id.cost);
        itemQuantity = (TextView) findViewById(R.id.quantity);
    }

    // Set display attributes
    public void showPurchase(Purchase purchase) {
        String nameUser = null;
        if (purchase.getUser().size() > 1) {
            nameUser = "Group Purchase";
        } else {
            for (User user: purchase.getUser()) {
                nameUser = user.getUserName();
            }
        }
        String nameItem = purchase.getItem().getItemName();
        String cost = Double.toString(purchase.getItem().getItemCost());
        String itemQty = Integer.toString(purchase.getQuantity());

        if (nameUser != null) {
            userName.setText(nameUser);
        } else {
            userName.setText(R.string.unidentified);
        }

        if (nameItem != null) {
            itemName.setText(nameItem);
        } else {
            itemName.setText("UNIDENTIFIED ITEM");
        }

        if (cost != null) {
            this.itemCost.setText(cost);
            this.itemCost.setVisibility(cost == null ? View.GONE : View.VISIBLE);
        } else {
            this.itemCost.setVisibility(View.GONE);
        }

        if (itemQty != null) {
            this.itemQuantity.setText(itemQty);
            this.itemQuantity.setVisibility(itemQty == null ? View.GONE : View.VISIBLE);
        } else {
            this.itemQuantity.setVisibility(View.GONE);
        }
    }

    public void setUserName(String text) {
        userName.setText(text);
    }

    public void setItemName(String text) {
        itemName.setText(text);
    }

    public void setItemCost(String text) {
        itemCost.setText(text);
    }

    public void setItemQuantity(String text) {
        itemQuantity.setText(text);
    }



}

