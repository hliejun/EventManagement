package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

// Custom view for displaying of bill
public class BillView extends RelativeLayout {
    // Required Backend Objects (For Particular Bill)
    Bill myBill = null;

    // View Components
    private TextView billTitle;

    // Custom View Attributes
    boolean mShowText = false;
    int mTextPos = 0;

    // Constructor
    public BillView(Context context, AttributeSet attrs) {

        // Call Parent Constructor
        super(context, attrs);

        // Attributes Handling
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BillView,
                0, 0);

        // Set Attributes (Examples)
        try {
            mShowText = attributesArray.getBoolean(R.styleable.BillView_billsDisplayText, false);
            mTextPos = attributesArray.getInteger(R.styleable.BillView_billsLabelPosition, 0);
        } finally {
            attributesArray.recycle();
        }

        // Initialize Components
        LayoutInflater.from(context).inflate(R.layout.bill, this, true);
        billTitle = (TextView) findViewById(R.id.billTitle);
    }

    // Set display attributes
    public void showBill(Bill bill) {
        this.myBill = bill;
        String myBillEventTitle = myBill.getBillTitle();

        if (myBillEventTitle != null) {
            billTitle.setText(myBillEventTitle);
        } else {
            billTitle.setText("UNIDENTIFIED BILL");
        }
    }

    public String getBillEventTitle() {
        return billTitle.getText().toString();
    }

    public void setBillEventTitle(String title) {
        billTitle.setText(title);
    }


}

