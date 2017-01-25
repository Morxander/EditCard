package morxander.editcard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.regex.Pattern;

import static android.R.attr.start;

/**
 * Created by Mac on 11/19/16 PM 2:24
 */

public class EditCard extends EditText {

    String type = "";
    CardNumberTransformationMethod cardNumberTransformationMethod;

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
        
        cardNumberTransformationMethod = CardNumberTransformationMethod.getInstance();
        setTransformationMethod(cardNumberTransformationMethod);
        
        // Adding the TextWatcher
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (textWatcher != null) textWatcher.beforeTextChanged(s, start,count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int position, int before, int action) {
                changeIcon(s.toString());

                boolean isChaged    = false;
                String  text         = getText().toString();
                String  onlyNum      = getText().toString().replaceAll("-", "");

                StringBuilder stringBuilder = new StringBuilder(onlyNum);

                if (type.equals("UNKNOWN") || type.equals("MasterCard") || type.equals("Visa") || type.equals("Discover") || type.equals("JCB")|| type.equals("Union_Pay")) {
                    if (text.length() > 4) {
                        if ('-' != text.charAt(4)) {
                            isChaged = true;
                        }
                        stringBuilder.insert(4, "-");
                        if (text.length() > 9) {
                            if ('-' != text.charAt(9)) {
                                isChaged = true;
                            }
                            stringBuilder.insert(9, "-");
                            if (text.length() > 14) {
                                if ('-' != text.charAt(14)) {
                                    isChaged = true;
                                }
                                stringBuilder.insert(14, "-");
                            }
                        }
                    }
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
                    cardNumberTransformationMethod.setType(CardNumberTransformationMethod.NORMAL);
                } else if (type.equals("American_Express") || type.equals("Diners_Club")) {
                    if (type.equals("Diners_Club")) {
                        setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                        cardNumberTransformationMethod.setType(CardNumberTransformationMethod.DINERS_CLUB);
                    }else {
                        setFilters(new InputFilter[]{new InputFilter.LengthFilter(17)});
                        cardNumberTransformationMethod.setType(CardNumberTransformationMethod.AMERICAN_EXPRESS);
                    }
                    if (text.length() > 4) {
                        if ('-' != text.charAt(4)) {
                            isChaged = true;
                        }
                        stringBuilder.insert(4, "-");
                        if (text.length() > 11) {
                            if ('-' != text.charAt(11)) {
                                isChaged = true;
                            }
                            stringBuilder.insert(11, "-");
                        }
                    }
                }
                if (isChaged) {
                    setText(stringBuilder.toString());
                }else{
                    setSelection(length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textWatcher != null) textWatcher.afterTextChanged(editable);
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
        String s = getText().toString();
        changeIcon(s);
    }

    // 2017.01.25
    private void changeIcon(String s) {
        s = s.replaceAll("-", "").trim();
        if (s.startsWith("4") || s.matches(CardPattern.VISA)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vi, 0);
            type = "Visa";
        } else if (s.matches(CardPattern.MASTERCARD_SHORTER)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mc, 0);
            type = "MasterCard";
        } else if (s.matches(CardPattern.AMERICAN_EXPRESS)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.am, 0);
            type = "American_Express";
        } else if (s.matches(CardPattern.DISCOVER_SHORT) || s.matches(CardPattern.DISCOVER)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ds, 0);
            type = "Discover";
        } else if (s.matches(CardPattern.JCB_SHORT)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jcb, 0);
            type = "JCB";
        } else if (s.matches(CardPattern.DINERS_CLUB_SHORT)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dc, 0);
            type = "Diners_Club";
        } else if (s.matches(CardPattern.UNION_PAY_SHORT)) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.un, 0);
            type = "Union_Pay";
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
        // TODO: 2017. 1. 25. Need to add union-pay valid check
        return false;
    }
    // 2017.01.25
    public String getCardType(){
        return type;
    }

    // 2017.01.25    
    TextWatcher textWatcher;
    
    // 2017.01.25
    public void addCardTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    // 2017.01.25
    public void removeCardNumberTransformationMethod(TransformationMethod method) {
       setTransformationMethod(null);
    }

    // 2017.01.25
    public void setCardNumberTransformationMethod(TransformationMethod method) {
        if (cardNumberTransformationMethod == null) {
            cardNumberTransformationMethod = CardNumberTransformationMethod.getInstance();
        }
        setTransformationMethod(cardNumberTransformationMethod);
    }
}
