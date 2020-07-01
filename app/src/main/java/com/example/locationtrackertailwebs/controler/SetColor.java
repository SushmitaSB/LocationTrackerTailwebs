package com.example.locationtrackertailwebs.controler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

public class SetColor {

    public SetColor(Context context) {
        this.context = context;
    }

    private Context context;

    public void setTextColorForImage(final TextView textView, final TextView textView1, Bitmap firstPhoto) {
        Palette.from(firstPhoto)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch == null && palette.getSwatches().size() > 0) {
                            swatch = palette.getSwatches().get(0);
                        }

                        int titleTextColor = Color.WHITE; //initialized titleTextColor

                        if (swatch != null) { //checking swatch is null or not
                            titleTextColor = swatch.getRgb();
                            titleTextColor = ColorUtils.setAlphaComponent(titleTextColor, 255);
                        }
                        //set the color on textView
                        textView.setTextColor(titleTextColor);
                        textView1.setTextColor(titleTextColor);

                    }
                });
    }
}
