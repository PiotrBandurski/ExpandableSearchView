package pl.piotrbandurski.expandablesearchview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Piotr on 19.02.2016.
 */
public class SquaredImageView extends ImageView {

        public SquaredImageView(final Context context) {
            super(context);
        }

        public SquaredImageView(final Context context, final AttributeSet attrs) {
            super(context, attrs);
        }

        public SquaredImageView(final Context context, final AttributeSet attrs,
                                final int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onMeasure(int width, int height) {
            super.onMeasure(width, height);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            if (measuredWidth > measuredHeight) {
                setMeasuredDimension(measuredHeight, measuredHeight);
            } else {
                setMeasuredDimension(measuredWidth, measuredWidth);
            }
        }

    }
