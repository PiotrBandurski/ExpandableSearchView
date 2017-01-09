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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pl.piotrbandurski.expandablesearchview.listeners.OnListItemSelectedListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnQueryTextEnterListener;
import pl.piotrbandurski.expandablesearchview.tools.ScreenUtils;
import pl.piotrbandurski.expandablesearchviewlibrary.R;

/**
 * Created by piotr on 30.08.2016.
 */
public class ExpandableSearchView extends BaseView {

    SlidingListView mSlidingListView;
    EditText mSearchEditText;
    ImageView mIconImageView;
    LinearLayout mContainerLinearLayout;
    RelativeLayout mSearchContainer;
    OnListStateChangeListener.ListState mActualState = OnListStateChangeListener.ListState.CLOSED;
    OnListItemSelectedListener mOnListItemSelectedListener;
    OnQueryTextEnterListener mOnQueryTextEnterListener;
    private boolean isListOpened = false;

    public ExpandableSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attachView(R.layout.search_view_layout);
        collectAttrs();
        setupListeners();
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

    public void setSearchHint(String hint) {
        mSearchEditText.setHint(hint);
    }

    public void setSearchIcon(Drawable icon) {
        mIconImageView.setImageDrawable(icon);
    }

    public void setSlidingDuration(int slidingDuration) {
        mSlidingListView.setSlidingDuration(slidingDuration);
    }

    public void setSingleItemHeight(int singleItemHeight) {
        mSlidingListView.setSingleItemHeight(singleItemHeight);
    }

    public void setMaxListHeightInPx(int heightInPx) {
        mSlidingListView.setMaxListHeightInPx(heightInPx);
    }

    public void setOnListStateChangeListener(OnListStateChangeListener onListStateChangeListener) {
        mSlidingListView.setOnListStateChangeListener(onListStateChangeListener);
    }

    public <T> void setListViewAdapter(ArrayAdapter<T> adapter) {
        mSlidingListView.setAdapter(adapter);
        mSlidingListView.wrapList();
    }

    public void expandListView() {
        isListOpened = true;
        mSlidingListView.wrapList();
    }

    public void collapseListView() {
        isListOpened = false;
        mSlidingListView.collapseList();
    }

    //I'm not using any binding libraries like ButterKnife
    //in this project to save number of methods
    @Override
    void bindViews() {
        mSearchEditText = (EditText) findViewById(R.id.search_editText);
        mIconImageView = (ImageView) findViewById(R.id.airport_search_item_layout_iv);
        mSearchContainer = (RelativeLayout) findViewById(R.id.suggestedAirportContainer);
        mContainerLinearLayout = (LinearLayout) findViewById(R.id.search_airport_linearLayout);
        insertSlidingListviewToContainer();
    }

    private void insertSlidingListviewToContainer() { //i'm doing this instead of adding it in xml for hide SlidingListView for final developer
        mSlidingListView = provideSlidingListView();
        mSlidingListView.setupView();
        mContainerLinearLayout.addView(mSlidingListView);
    }

    private void setupListeners() {
        mSlidingListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) { // little workaround if someone will want to have this view inside scrollview
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mSlidingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                onListItemClick(position);
                collapseListView();
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

        mSearchContainer.setOnClickListener(new OnClickListener() {
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
                if (hasFocus) {
                    expandListView();
                    isListOpened = true;
                } else {
                    collapseListView();
                    isListOpened = false;
                }
            }
        });
    }

    void handleSearchFieldClick() {
        if (isListOpened) {
            collapseListView();
        } else {
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
        TypedArray typedArray = getContext().obtainStyledAttributes(mAttributeSet, R.styleable.ExpandableSearchView);

        int slidingDuration = typedArray.getInteger(R.styleable.ExpandableSearchView_slidingDuration, SlidingListView.DEFAULT_SLIDING_DURATION);
        setSlidingDuration(slidingDuration);

        int maxListHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandableSearchView_maxListHeight, SlidingListView.DEFAULT_MAX_LIST_HEIGHT);
        setMaxListHeightInPx(maxListHeight);

        Drawable searchIcon = typedArray.getDrawable(R.styleable.ExpandableSearchView_searchIcon);
        if (searchIcon != null) {
            setSearchIcon(searchIcon);
        }

        String searchHint = typedArray.getString(R.styleable.ExpandableSearchView_searchHint);
        setSearchHint(searchHint);

        int singleItemHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandableSearchView_singleItemHeight, Integer.MIN_VALUE);
        mSlidingListView.setSingleItemHeight(singleItemHeight);

        Drawable background = typedArray.getDrawable(R.styleable.ExpandableSearchView_searchBackground);
        if (background != null) {
            mSearchContainer.setBackgroundDrawable(background);
        }
        typedArray.recycle();
    }

    private SlidingListView provideSlidingListView() {
        return new SlidingListView(getContext(), mAttributeSet) {
        };
    }
}
