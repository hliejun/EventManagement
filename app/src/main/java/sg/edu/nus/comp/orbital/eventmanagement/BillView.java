package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

// Custom view for displaying of bill
public class BillView extends RelativeLayout {
    // Required Backend Objects (For Particular Bill)
    Bill myBill = null;

    // View Components
    private TextView eventTitle;

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
        eventTitle = (TextView) findViewById(R.id.eventTitle);
    }

    // Set display attributes
    public void showBill(Bill bill) {
        this.myBill = bill;
        String myBillEventTitle = myBill.getEventTitle();

        if (myBillEventTitle != null) {
            eventTitle.setText(myBillEventTitle);
        } else {
            eventTitle.setText("UNIDENTIFIED BILL");
        }
    }

    public String getBillEventTitle() {
        return eventTitle.getText().toString();
    }

    public void setBillEventTitle(String title) {
        eventTitle.setText(title);
    }


}

