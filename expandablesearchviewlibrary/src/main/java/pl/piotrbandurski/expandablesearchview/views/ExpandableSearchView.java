package pl.piotrbandurski.expandablesearchview.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import pl.piotrbandurski.expandablesearchview.listeners.OnListItemSelectedListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnQueryTextEnterListener;
import pl.piotrbandurski.expandablesearchviewlibrary.R;

/**
 * Created by piotr on 30.08.2016.
 */
public class ExpandableSearchView extends BaseView {

    SlidingExpandableListView mSlidingExpandableListView;
    EditText mSearchEditText;
    OnListStateChangeListener.ListState mActualState = OnListStateChangeListener.ListState.CLOSED;
    OnListItemSelectedListener mOnListItemSelectedListener;
    OnQueryTextEnterListener mOnQueryTextEnterListener;
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
        mSlidingExpandableListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { // little workaround if someone will want to have this view inside scrollview
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mSlidingExpandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                onListItemClick(position);
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onSearchEditTextEntered(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void onListItemClick(int position){
        if (mOnListItemSelectedListener == null){
            return;
        }
        mOnListItemSelectedListener.onListItemSelected(position);
    }

    private void onSearchEditTextEntered(String text){
        if (mOnQueryTextEnterListener == null){
            return;
        }
        mOnQueryTextEnterListener.onQueryTextTyped(text);
    }

    private void collectAttrs() {

    }

    public void setOnListItemSelectedListener(OnListItemSelectedListener mOnListItemSelectedListener) {
        this.mOnListItemSelectedListener = mOnListItemSelectedListener;
    }

    public void setmOnQueryTextEnterListener(OnQueryTextEnterListener mOnQueryTextEnterListener) {
        this.mOnQueryTextEnterListener = mOnQueryTextEnterListener;
    }

    public void setSearchText(String text){
        mSearchEditText.setText(text);
    }

    public void setSearchIcon(Drawable icon){

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
