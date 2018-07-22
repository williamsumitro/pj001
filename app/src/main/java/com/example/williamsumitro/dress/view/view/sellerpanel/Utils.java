package com.example.williamsumitro.dress.view.view.sellerpanel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

/**
 * Created by WilliamSumitro on 21/07/2018.
 */

public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.item_sellerpanel_tv);
        txt.setText(libraryObject.getTitle());


        final ImageView img = (ImageView) view.findViewById(R.id.item_sellerpanel_img);
        img.setImageResource(libraryObject.getRes());
    }

    public static class LibraryObject {

        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }
    }
}
