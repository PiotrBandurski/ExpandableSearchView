package pl.piotrbandurski.expandablesearchview;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import pl.piotrbandurski.expandablesearchview.data.SampleArrayAdapter;
import pl.piotrbandurski.expandablesearchview.data.SampleDataObject;
import pl.piotrbandurski.expandablesearchview.data.SampleDataProvider;
import pl.piotrbandurski.expandablesearchview.listeners.OnListItemSelectedListener;
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
        mExpandableSearchView.setmOnQueryTextEnterListener(this);
        mAdapter = new SampleArrayAdapter(this,-1, SampleDataProvider.getSampleData());
        mExpandableSearchView.setListViewAdapter(mAdapter);
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
