package pl.piotrbandurski.expandablesearchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.piotrbandurski.expandablesearchview.listeners.OnQueryTextEnterListener;
import pl.piotrbandurski.expandablesearchview.views.ExpandableSearchView;

public class MainActivity extends AppCompatActivity implements OnQueryTextEnterListener {

    ExpandableSearchView mExpandableSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onQueryTextTyped(String query) {

    }

}
