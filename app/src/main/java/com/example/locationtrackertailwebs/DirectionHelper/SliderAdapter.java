package com.example.locationtrackertailwebs.DirectionHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.SetColor;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private ImageView linearLayout;
    private Animation a;
    private TextView textViewHeading, textView;
    private ImageView indicator1, indicator2, indicator3;
    private SetColor setColor;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen,container,false);
        // finding all the ids of views
        initializedViews(view);
        //set text color according to background image by calling serColor class object.
        setTextViewColor(position);
        container.addView(view);
        return view;
    }

    private void setTextViewColor(int position) {
        setColor = new SetColor(context);
        switch (position){
            case 0:
                linearLayout.setImageResource(R.drawable.man_img);
                a = AnimationUtils.loadAnimation(context, R.anim.scale);
                a.reset();
//                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(a);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.man_img);
                setColor.setTextColorForImage(textViewHeading,textView, bitmap);
                textViewHeading.setText(R.string.be_part);
                textView.setText(R.string.our_world);
                indicator1.setImageResource(R.drawable.active_dot_layout);
                indicator1.clearAnimation();
                indicator1.startAnimation(a);
                indicator2.setImageResource(R.drawable.non_active_dot_xml);
                indicator3.setImageResource(R.drawable.non_active_dot_xml);
                break;
            case 1:
                linearLayout.setImageResource(R.drawable.women_img);
                a = AnimationUtils.loadAnimation(context, R.anim.scale);
                a.reset();
//                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(a);
                Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.women_img);
                setColor.setTextColorForImage(textViewHeading,textView, bitmap1);
                textViewHeading.setText(R.string.perfect_your);
                textView.setText(R.string.everything);
                indicator1.setImageResource(R.drawable.non_active_dot_xml);
                indicator2.setImageResource(R.drawable.active_dot_layout);
                indicator2.clearAnimation();
                indicator2.startAnimation(a);
                indicator3.setImageResource(R.drawable.non_active_dot_xml);
                break;
            case 2:
                linearLayout.setImageResource(R.drawable.earth_img);
                a = AnimationUtils.loadAnimation(context, R.anim.scale);
                a.reset();
//                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(a);
                Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.earth_img);
                setColor.setTextColorForImage(textViewHeading,textView, bitmap2);
                textViewHeading.setText(R.string.postgraduate);
                textView.setText(R.string.join);
                indicator1.setImageResource(R.drawable.non_active_dot_xml);
                indicator2.setImageResource(R.drawable.non_active_dot_xml);
                indicator3.clearAnimation();
                indicator3.startAnimation(a);
                indicator3.setImageResource(R.drawable.active_dot_layout);
                break;
        }
    }

    private void initializedViews(View view) {
        indicator1 = view.findViewById(R.id.ind1);
        indicator2 = view.findViewById(R.id.ind2);
        indicator3 = view.findViewById(R.id.ind3);
        linearLayout = view.findViewById(R.id.backgroundLayout);
        textViewHeading = view.findViewById(R.id.tvHeading);
        textView = view.findViewById(R.id.tvText);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
