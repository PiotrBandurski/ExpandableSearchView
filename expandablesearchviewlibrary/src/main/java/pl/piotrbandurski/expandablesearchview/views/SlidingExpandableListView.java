package pl.piotrbandurski.expandablesearchview.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;

/**
 * Created by piotr on 30.08.2016.
 */
class SlidingExpandableListView extends ListView {

    public static final int DEFAULT_SLIDING_DURATION = 500;
    public static final int DEFAULT_MAX_LIST_HEIGHT = 1000;

    OnListStateChangeListener onListStateChangeListener;
    int maxListHeightInPx = DEFAULT_MAX_LIST_HEIGHT;
    public int slidingDuration = DEFAULT_SLIDING_DURATION;

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
            collapseList();
            return;
        }
        int allItemsHeightInPx = getMeasuredHeightOfAllItemsInPx();
        if (allItemsHeightInPx > maxListHeightInPx){
            expandListToMaxHeight();
        }else {
            expandListToHeight(allItemsHeightInPx);
        }
    }

    //I'm checking height of all elements instead of checking only one and multiple by items count
    // becouse someone may wants to have different list views
    private int getMeasuredHeightOfAllItemsInPx(){
        int totalHeight = 0;
        Adapter adapter = getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            View mView = adapter.getView(i, null, this);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        return totalHeight;
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
