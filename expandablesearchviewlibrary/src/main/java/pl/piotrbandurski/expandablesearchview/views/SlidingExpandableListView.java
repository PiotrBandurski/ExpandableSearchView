package pl.piotrbandurski.expandablesearchview.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;

/**
 * Created by piotr on 30.08.2016.
 */
class SlidingExpandableListView extends ListView {

    OnListStateChangeListener onListStateChangeListener;
    int maxListHeightInPx = 300; //TODO create seter

    public SlidingExpandableListView(Context context) {
        super(context);
    }

    public SlidingExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnListStateChangeListener(OnListStateChangeListener onListStateChangeListener) {
        this.onListStateChangeListener = onListStateChangeListener;
    }

    public void expandListToMaxHeight() {
        if (getLayoutParams().height == maxListHeightInPx) {
            return;
        }
        ValueAnimator va = ValueAnimator.ofInt(getLayoutParams().height, maxListHeightInPx);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                getLayoutParams().height = value;
                requestLayout();
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                changeActualState(OnListStateChangeListener.ListState.EXPANDING);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                changeActualState(OnListStateChangeListener.ListState.EXPANDED);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        va.start();
    }

    public void wrapList() {

    }

    public void collapseList() {
        if (getLayoutParams().height == 0) {
            return;
        }
        ValueAnimator va = ValueAnimator.ofInt(getLayoutParams().height, 0);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                getLayoutParams().height = value;
                requestLayout();
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                changeActualState(OnListStateChangeListener.ListState.CLOSING);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                changeActualState(OnListStateChangeListener.ListState.CLOSED);
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        va.start();
    }

    void changeActualState(OnListStateChangeListener.ListState state){
        if (onListStateChangeListener == null){
            return;
        }
        onListStateChangeListener.onStateChange(state);
    }

}