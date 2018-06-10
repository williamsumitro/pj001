package com.example.williamsumitro.dress.view.presenter.helper;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by WilliamSumitro on 03/04/2018.
 */

public class InputFilterMinMax implements InputFilter {
    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }
    @Override
    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int dstart, int dend) {
        try {
            int input = Integer.parseInt(spanned.toString() + charSequence.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }
    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
