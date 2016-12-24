package pl.piotrbandurski.expandablesearchview.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.piotrbandurski.expandablesearchview.R;

/**
 * Created by piotr on 24.12.2016.
 */

public class SampleArrayAdapter extends ArrayAdapter<SampleDataObject> {

    public SampleArrayAdapter(Context context, int resource, List<SampleDataObject> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_item, parent, false);
        SampleDataObject sampleDataObject = getItem(position);
        ItemHolder itemHolder = new ItemHolder(rowView, sampleDataObject);
        itemHolder.bindDataToViews();
        return rowView;
    }

    class ItemHolder {
        ImageView itemImageView;
        TextView itemTextView;
        SampleDataObject mSampleDataObject;

        public ItemHolder(View view, SampleDataObject sampleDataObject) {
            mSampleDataObject = sampleDataObject;
            itemImageView = (ImageView) view.findViewById(R.id.sample_item_iv);
            itemTextView = (TextView) view.findViewById(R.id.sample_item_tv);
        }

        private void bindDataToViews(){
            itemImageView.setImageResource(mSampleDataObject.getImage());
            itemTextView.setText(mSampleDataObject.getText());
        }

    }

}
