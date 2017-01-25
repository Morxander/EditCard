package morxander.editcard;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by Mac on 2017. 1. 25. PM 1:56
 * make password bullet
 */

public class CardNumberTransformationMethod extends PasswordTransformationMethod {
    final static    int    NORMAL               = 0;
    final static    int    AMERICAN_EXPRESS     = 1;
    final static    int    DINERS_CLUB          = 2;

    // Card type
    private         int    type                 = 0;

    private static CardNumberTransformationMethod ourInstance = new CardNumberTransformationMethod();

    public static CardNumberTransformationMethod getInstance() {
        return ourInstance;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new CardNumberCharSequence(source);
    }

    private class CardNumberCharSequence implements CharSequence {
        private     CharSequence    mSource;

        public CardNumberCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        public char charAt(int index) {
            switch(type) {
                case AMERICAN_EXPRESS:
                case DINERS_CLUB:
                    if (index < 11 && index > 4) {
                        return '*';
                    }
                    break;
                default:
                    if (index > 9 && index < 14) {
                        return '*';
                    }
                    break;
            }
            return mSource.charAt(index);
        }

        public int length() {
            return mSource.length();
        }

        public CharSequence subSequence(int start, int end) {
            CharSequence charSequence = mSource.subSequence(start, end);
            return charSequence; // Return default
        }
    }
}
