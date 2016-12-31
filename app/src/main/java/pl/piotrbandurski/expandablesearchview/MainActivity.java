package pl.piotrbandurski.expandablesearchview;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import pl.piotrbandurski.expandablesearchview.data.SampleArrayAdapter;
import pl.piotrbandurski.expandablesearchview.data.SampleDataObject;
import pl.piotrbandurski.expandablesearchview.data.SampleDataProvider;
import pl.piotrbandurski.expandablesearchview.listeners.OnListItemSelectedListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnListStateChangeListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnQueryTextEnterListener;
import pl.piotrbandurski.expandablesearchview.views.ExpandableSearchView;

public class MainActivity extends AppCompatActivity implements OnQueryTextEnterListener, OnListItemSelectedListener {

    ExpandableSearchView mExpandableSearchView;
    List<SampleDataObject> sampleDataObjects;
    SampleArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupExpandableSearchView();
    }


    private void setupExpandableSearchView(){
        mExpandableSearchView = (ExpandableSearchView) findViewById(R.id.expandable_searchview);
        mExpandableSearchView.setOnListItemSelectedListener(this);
        mExpandableSearchView.setOnQueryTextEnterListener(this);
        mAdapter = new SampleArrayAdapter(this,-1, SampleDataProvider.getSampleData());
        mExpandableSearchView.setListViewAdapter(mAdapter);
        mExpandableSearchView.setOnListStateChangeListener(new OnListStateChangeListener() {
            @Override
            public void onStateChange(ListState state) {
                if (state == ListState.CLOSED){
                    hideKeyboardWithDelay(); //With delay becouse on low-end devices sliding SearchView and hiding keyboard may be laggy
                }
            }
        });
    }

    private void hideKeyboardWithDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyboard();
            }
        }, 400);
    }

    private void hideKeyboard() {
        View focused_item = getCurrentFocus();
        if (focused_item != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focused_item.getWindowToken(), 0);
        }
    }

    @Override
    public void onQueryTextTyped(String query) {
        mAdapter = new SampleArrayAdapter(this,-1,SampleDataProvider.searchDataByQuery(query));
        mExpandableSearchView.setListViewAdapter(mAdapter);
    }

    @Override
    public void onListItemSelected(int index) {
        if (mAdapter == null){
            return;
        }
        SampleDataObject item = mAdapter.getItem(index);
        mExpandableSearchView.setSearchIcon(ContextCompat.getDrawable(this, item.getImage()));
        mExpandableSearchView.setSearchText(item.getText());
    }
}
