package sg.edu.nus.comp.orbital.eventmanagement;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.content.res.TypedArray;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;

// Custom view for displaying of payments due in a bill
public class UserCostView  extends RelativeLayout {
    // Required Backend Objects (For Particular Debt)
    User debtor = null;
    Double totalCost = null;
    Double paidAmount = null;

    // Backend Output
    Debt debt = null;

    // View Components
    private ImageView profilePic;
    private TextView userName;
    private TextView cost;
    private EditText paid;

    // Custom View Attributes
    boolean mShowText = false;
    int mTextPos = 0;

    // Constructor
    public UserCostView(Context context, AttributeSet attrs) {

        // Call Parent Constructor
        super(context, attrs);

        // Attributes Handling
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.UserCostView,
                0, 0);

        // Set Attributes (Examples)
        try {
            mShowText = attributesArray.getBoolean(R.styleable.UserCostView_displayText, false);
            mTextPos = attributesArray.getInteger(R.styleable.UserCostView_labelPosition, 0);
        } finally {
            attributesArray.recycle();
        }

        // Initialize Components
        LayoutInflater.from(context).inflate(R.layout.user_costs, this, true);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        userName = (TextView) findViewById(R.id.userName);
        cost = (TextView) findViewById(R.id.cost);
        paid = (EditText) findViewById(R.id.paid);
    }

    // Set display attributes
    public void showCost(UserCostPair userCost) {
        this.debtor = (userCost != null ? userCost.getUser() : null);
        this.totalCost = (userCost != null ? userCost.getCost() : null);
        String name = debtor.getUserName();
        String cost = Double.toString(totalCost);
        String paid = Double.toString(paidAmount);

        if (name != null) {
            userName.setText(name);
        } else {
            userName.setText(R.string.unidentified);
        }

        if (cost != null) {
            this.cost.setText(cost);
            this.cost.setVisibility(name == null ? View.GONE : View.VISIBLE);
        } else {
            this.cost.setVisibility(View.GONE);
        }

        if (paidAmount != null) {
            this.paid.setText(paid);
            this.paid.setVisibility(View.VISIBLE);
        } else {
            this.paid.setVisibility(View.VISIBLE);
        }
    }
}

