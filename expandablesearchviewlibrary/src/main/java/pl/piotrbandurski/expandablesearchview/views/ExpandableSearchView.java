package pl.piotrbandurski.expandablesearchview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;
import pl.piotrbandurski.expandablesearchviewlibrary.R;

/**
 * Created by piotr on 30.08.2016.
 */
public class ExpandableSearchView extends BaseView {

    SlidingExpandableListView mSlidingExpandableListView;
    EditText mSearchEditText;
    OnListStateChangeListener.ListState mActualState = OnListStateChangeListener.ListState.CLOSED;
    private int maxListHeightInPx;

    public ExpandableSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attachView(R.layout.search_view_layout);
        setupListeners();
    }

    //I'm not using any binding libraries like ButterKnife
    //in this project to save number of methods
    @Override
    void bindViews() {
        mSlidingExpandableListView = (SlidingExpandableListView) findViewById(R.id.expandable_listview);
        mSearchEditText = (EditText) findViewById(R.id.search_editText);
    }

    private void setupListeners() {
        // little workaround if someone will want to have this view inside scrollview
        mSlidingExpandableListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    private void collectAttrs() {

    }

    public void setmSearchText(String text){
        mSearchEditText.setText(text);
    }

    public void setOnListStateChangeListener(OnListStateChangeListener onListStateChangeListener) {
        mSlidingExpandableListView.setOnListStateChangeListener(onListStateChangeListener);
    }

    public <T> void setListViewAdapter(ArrayAdapter<T> adapter) {
        mSlidingExpandableListView.setAdapter(adapter);
    }

    public void expandListView() {
        mSlidingExpandableListView.expandListToMaxHeight();
    }

    public void collapseListView() {
        mSlidingExpandableListView.collapseList();
    }
}
