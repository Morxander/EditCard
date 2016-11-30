package morxander.editcard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by morxander on 11/19/16.
 */

public class EditCard extends EditText {

    String type = "UNKNOWN";

    public EditCard(Context context) {
        super(context);
        addMagic();
    }

    public EditCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        addMagic();
    }

    public EditCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addMagic();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addMagic();
    }

    private void addMagic() {
        // Changing the icon when it's empty
        changeIcon();
        // Adding the TextWatcher
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int position, int before, int action) {
                if (action == 1) {
                    if (type.equals("UNKNOWN") || type.equals("Visa") || type.equals("Discover") || type.equals("JCB")) {
                        if (position == 3 || position == 8 || position == 13) {
                            if (!s.toString().endsWith("-")) {
                                append("-");
                            }
                        }
                    } else if (type.equals("American_Express") || type.equals("Diners_Club")) {
                        if (position == 3 || position == 10) {
                            if (!s.toString().endsWith("-")) {
                                append("-");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                changeIcon();
            }
        });
        // The input filters
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[0-9\\-]*").matcher(String.valueOf(source)).matches()) {
                        return "";
                    }
                }
                return null;
            }
        };
        // Setting the filters
        setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(19)});
    }

    private void changeIcon() {
        String s = getText().toString().replace("-", "").trim();
        if (s.startsWith("4") || s.matches(CardPattern.VISA)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vi, 0);
            type = "Visa";
        } else if (s.matches(CardPattern.MASTERCARD_SHORTER) || s.matches(CardPattern.MASTERCARD_SHORT) || s.matches(CardPattern.MASTERCARD)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mc, 0);
            type = "MasterCard";
        } else if (s.matches(CardPattern.AMERICAN_EXPRESS)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.am, 0);
            type = "American_Express";
        } else if (s.matches(CardPattern.DISCOVER_SHORT) || s.matches(CardPattern.DISCOVER)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ds, 0);
            type = "Discover";
        } else if (s.matches(CardPattern.JCB_SHORT) || s.matches(CardPattern.JCB)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jcb, 0);
            type = "JCB";
        } else if (s.matches(CardPattern.DINERS_CLUB_SHORT) || s.matches(CardPattern.DINERS_CLUB)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dc, 0);
            type = "Diners_Club";
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card, 0);
            type = "UNKNOWN";
        }
    }

    public String getCardNumber() {
        return getText().toString().replace("-", "").trim();
    }

    public boolean isValid() {
        if (getCardNumber().matches(CardPattern.VISA_VALID)) return true;
        if (getCardNumber().matches(CardPattern.MASTERCARD_VALID)) return true;
        if (getCardNumber().matches(CardPattern.AMERICAN_EXPRESS_VALID)) return true;
        if (getCardNumber().matches(CardPattern.DISCOVER_VALID)) return true;
        if (getCardNumber().matches(CardPattern.DINERS_CLUB_VALID)) return true;
        if (getCardNumber().matches(CardPattern.JCB_VALID)) return true;
        return false;
    }

    public String getCardType(){
        return type;
    }
}
