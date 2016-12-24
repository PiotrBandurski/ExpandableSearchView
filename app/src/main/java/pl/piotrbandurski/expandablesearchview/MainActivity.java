package pl.piotrbandurski.expandablesearchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.piotrbandurski.expandablesearchview.listeners.OnListItemSelectedListener;
import pl.piotrbandurski.expandablesearchview.listeners.OnQueryTextEnterListener;
import pl.piotrbandurski.expandablesearchview.views.ExpandableSearchView;

public class MainActivity extends AppCompatActivity implements OnQueryTextEnterListener, OnListItemSelectedListener {

    ExpandableSearchView mExpandableSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void setupExpandableSearchView(){
        mExpandableSearchView = (ExpandableSearchView) findViewById(R.id.expandable_searchview);
        mExpandableSearchView.setOnListItemSelectedListener(this);
        mExpandableSearchView.setOnQ
    }

    @Override
    public void onQueryTextTyped(String query) {

    }

    @Override
    public void onListItemSelected(int index) {

    }
}
