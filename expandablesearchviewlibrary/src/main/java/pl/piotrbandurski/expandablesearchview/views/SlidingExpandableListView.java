package pl.piotrbandurski.expandablesearchview.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import pl.piotrbandurski.expandablesearchview.exceptions.PropertyNotSetException;
import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;

/**
 * Created by piotr on 30.08.2016.
 */
class SlidingExpandableListView extends ListView {

    public static final int DEFAULT_SLIDING_DURATION = 500;
    public static final int DEFAULT_MAX_LIST_HEIGHT = 1000;

    OnListStateChangeListener onListStateChangeListener;
    int maxListHeightInPx = DEFAULT_MAX_LIST_HEIGHT;
    int slidingDuration = DEFAULT_SLIDING_DURATION;
    int singleItemHeight = Integer.MIN_VALUE;

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

    public void setSingleItemHeight(int singleItemHeight) {
        this.singleItemHeight = singleItemHeight;
    }

    public void setSlidingDuration(int slidingDuration) {
        this.slidingDuration = slidingDuration;
    }

    public void setMaxListHeightInPx(int maxListHeightInPx) {
        this.maxListHeightInPx = maxListHeightInPx;
    }

    public void expandListToMaxHeight() {
        if (getLayoutParams().height == maxListHeightInPx) {
            return;
        }
        expandListToHeight(maxListHeightInPx);
    }

    private void expandListToHeight(int height){
        if (getLayoutParams().height == height) {
            return;
        }
        ValueAnimator va = ValueAnimator.ofInt(getLayoutParams().height, height);
        va.setDuration(slidingDuration);
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
        Adapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0){
            collapseListDueToEmptyAdapter();
            return;
        }
        int allItemsHeightInPx = getHeightOfAllItemsInPx();
        if (allItemsHeightInPx > maxListHeightInPx){
            expandListToMaxHeight();
        }else {
            expandListToHeight(allItemsHeightInPx);
        }
    }


    private int getHeightOfAllItemsInPx(){
        Adapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0){
            return 0;
        }
        if(singleItemHeight == Integer.MIN_VALUE){
            throw new PropertyNotSetException("set single item height in xml using: app:singleItemHeight=\"40dp\" or setSingleItemHeight() method ");
        }
        return adapter.getCount() * singleItemHeight;
    }


    private void collapseListDueToEmptyAdapter(){
        if (getLayoutParams().height == 0) {
            return;
        }
        ValueAnimator va = ValueAnimator.ofInt(getLayoutParams().height, 0);
        va.setDuration(slidingDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                getLayoutParams().height = value;
                requestLayout();
            }
        });
        va.start();
    }

    public void collapseList() {
        if (getLayoutParams().height == 0) {
            return;
        }
        ValueAnimator va = ValueAnimator.ofInt(getLayoutParams().height, 0);
        va.setDuration(slidingDuration);
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
