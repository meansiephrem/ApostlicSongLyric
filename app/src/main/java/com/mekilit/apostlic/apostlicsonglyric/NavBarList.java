package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Menasi on 7/15/2016.
 */
public class NavBarList extends ArrayAdapter<Integer> {





    public NavBarList(Context context ,Integer[] arry) {
        super(context, R.layout.nav_bar_layout, arry);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View returnView = inflater.inflate(R.layout.nav_bar_layout, parent, false);
        TextView textView = (TextView) returnView.findViewById(R.id.NavItemText);
        ImageView imageView = (ImageView) returnView.findViewById(R.id.NavItemImage);
        imageView.setImageResource(android.R.drawable.btn_star_big_on);
        switch (position)
        {
            case 0:
                textView.setText("ሁሉም አልበሞች");
                break;
            case 1:
                textView.setText("ስለ አፑ");
                break;

            case 2:
                textView.setText("ሰሪዎቹ");
                break;

            case 3:
                textView.setText("ውጣ");
                break;

            case 4:

                break;

            default:
                break;
        }







        return returnView;
    }
}
