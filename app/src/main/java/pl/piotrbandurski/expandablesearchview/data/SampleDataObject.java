package pl.piotrbandurski.expandablesearchview.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by piotr on 24.12.2016.
 */

public class SampleDataObject {

    private String text = "";
    private @DrawableRes int image;

    public SampleDataObject(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

}
