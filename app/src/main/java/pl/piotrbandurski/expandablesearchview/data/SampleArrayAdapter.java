package pl.piotrbandurski.expandablesearchview.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.piotrbandurski.expandablesearchview.R;
import pl.piotrbandurski.expandablesearchview.SquaredImageView;

/**
 * Created by piotr on 24.12.2016.
 */

public class SampleArrayAdapter extends ArrayAdapter<SampleDataObject> {

    private Context mContext;
    public SampleArrayAdapter(Context context, int resource, List<SampleDataObject> objects) {
        super(context, -1, objects);
        mContext = context;
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
        SquaredImageView itemImageView;
        TextView itemTextView;
        SampleDataObject mSampleDataObject;

        public ItemHolder(View view, SampleDataObject sampleDataObject) {
            mSampleDataObject = sampleDataObject;
            itemImageView = (SquaredImageView) view.findViewById(R.id.sample_item_iv);
            itemTextView = (TextView) view.findViewById(R.id.sample_item_tv);
        }

        private void bindDataToViews(){
            Picasso.with(mContext).load(mSampleDataObject.getImage()).into(itemImageView); //use picasso for better smoothness
            itemTextView.setText(mSampleDataObject.getText());
        }

    }

}
