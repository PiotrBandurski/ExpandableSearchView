package pl.piotrbandurski.expandablesearchview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    ImageView mIconImageView;
    RelativeLayout mContainer;
    OnListStateChangeListener.ListState mActualState = OnListStateChangeListener.ListState.CLOSED;
    OnListItemSelectedListener mOnListItemSelectedListener;
    OnQueryTextEnterListener mOnQueryTextEnterListener;
    private boolean isListOpened = false;
    private int maxListHeightInPx;

    public ExpandableSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attachView(R.layout.search_view_layout);
        collectAttrs();
        setupListeners();
    }

    //I'm not using any binding libraries like ButterKnife
    //in this project to save number of methods
    @Override
    void bindViews() {
        mSlidingExpandableListView = (SlidingExpandableListView) findViewById(R.id.expandable_listview);
        mSearchEditText = (EditText) findViewById(R.id.search_editText);
        mIconImageView = (ImageView) findViewById(R.id.airport_search_item_layout_iv);
        mContainer = (RelativeLayout) findViewById(R.id.suggestedAirportContainer);
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
                mSlidingExpandableListView.collapseList();
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

        mContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchFieldClick();
            }
        });
        mSearchEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchFieldClick();
            }
        });
        mSearchEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    expandListView();
                    isListOpened = true;
                }else {
                    collapseListView();
                    isListOpened = false;
                }
            }
        });
    }



    void handleSearchFieldClick(){
        if (isListOpened){
            collapseListView();
        }else {
            expandListView();
        }
    }

    private void onListItemClick(int position) {
        if (mOnListItemSelectedListener == null) {
            return;
        }
        mOnListItemSelectedListener.onListItemSelected(position);
    }

    private void onSearchEditTextEntered(String text) {
        if (mOnQueryTextEnterListener == null) {
            return;
        }
        mOnQueryTextEnterListener.onQueryTextTyped(text);
    }

    private void collectAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.ExpandableSearchView);

        int slidingDuration = typedArray.getInteger(R.styleable.ExpandableSearchView_slidingDuration, SlidingExpandableListView.DEFAULT_SLIDING_DURATION);
        setSlidingDuration(slidingDuration);

        int maxListHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandableSearchView_maxListHeight, SlidingExpandableListView.DEFAULT_MAX_LIST_HEIGHT);
        setMaxListHeightInPx(maxListHeight);

        Drawable searchIcon = typedArray.getDrawable(R.styleable.ExpandableSearchView_searchIcon);
        if (searchIcon != null){
            setSearchIcon(searchIcon);
        }

        String searchHint  = typedArray.getString(R.styleable.ExpandableSearchView_searchHint);
        setSearchHint(searchHint);

        int singleItemHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandableSearchView_singleItemHeight, Integer.MIN_VALUE);
        mSlidingExpandableListView.setSingleItemHeight(singleItemHeight);

        typedArray.recycle();
    }

    public void setOnListItemSelectedListener(OnListItemSelectedListener mOnListItemSelectedListener) {
        this.mOnListItemSelectedListener = mOnListItemSelectedListener;
    }

    public void setOnQueryTextEnterListener(OnQueryTextEnterListener mOnQueryTextEnterListener) {
        this.mOnQueryTextEnterListener = mOnQueryTextEnterListener;
    }

    public void setSearchText(String text) {
        mSearchEditText.setText(text);
    }

    public void setSearchHint(String hint){
        mSearchEditText.setHint(hint);
    }

    public void setSearchIcon(Drawable icon) {
        mIconImageView.setImageDrawable(icon);
    }

    public void setSlidingDuration(int slidingDuration) {
        mSlidingExpandableListView.setSlidingDuration(slidingDuration);
    }

    public void setSingleItemHeight(int singleItemHeight) {
        mSlidingExpandableListView.setSingleItemHeight(singleItemHeight);
    }

    public void setMaxListHeightInPx(int heightInPx){
        mSlidingExpandableListView.setMaxListHeightInPx(heightInPx);
    }

    public void setOnListStateChangeListener(OnListStateChangeListener onListStateChangeListener) {
        mSlidingExpandableListView.setOnListStateChangeListener(onListStateChangeListener);
    }

    public <T> void setListViewAdapter(ArrayAdapter<T> adapter) {
        mSlidingExpandableListView.setAdapter(adapter);
        mSlidingExpandableListView.wrapList();
    }

    public void expandListView() {
        isListOpened = true;
        mSlidingExpandableListView.wrapList();
    }

    public void collapseListView() {
        isListOpened = false;
        mSlidingExpandableListView.collapseList();
    }
}
