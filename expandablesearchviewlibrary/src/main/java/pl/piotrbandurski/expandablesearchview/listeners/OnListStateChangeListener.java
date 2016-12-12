package pl.piotrbandurski.expandablesearchview.listeners;

/**
 * Created by piotr on 30.08.2016.
 */
public interface OnListStateChangeListener {
    enum  ListState  {EXPANDED,EXPANDING,CLOSED,CLOSING}
    void onStateChange(ListState state);
}
