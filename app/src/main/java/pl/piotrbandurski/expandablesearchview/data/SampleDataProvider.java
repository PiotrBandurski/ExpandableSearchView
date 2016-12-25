package pl.piotrbandurski.expandablesearchview.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.piotrbandurski.expandablesearchview.R;

/**
 * Created by piotr on 24.12.2016.
 */

public class SampleDataProvider {

    private static List<SampleDataObject> sampleDataObjects;

    public static List<SampleDataObject> getSampleData() {
        if (sampleDataObjects != null){
            return sampleDataObjects;
        }
        sampleDataObjects = new ArrayList<>();
        for (int i = 0; i < (new Random().nextInt(20)+10); i++){
            sampleDataObjects.add(new SampleDataObject(getRandomText(),getRandomDrawable()));
        }
        return sampleDataObjects;
    }

    public static List<SampleDataObject> searchDataByQuery(String query){ //Simple stupid function which mocks searching...
        List<SampleDataObject> listToReturn = new ArrayList<>();
        for (SampleDataObject sampleDataObject : getSampleData()){
            if (sampleDataObject.getText().toLowerCase().contains(query.toLowerCase())){
                listToReturn.add(sampleDataObject);
            }
        }
        return listToReturn;
    }

    private static  @DrawableRes int getRandomDrawable() {
        int[] drawables = {R.drawable.archives, R.drawable.atom, R.drawable.browser, R.drawable.cards, R.drawable.cloudy, R.drawable.cogwheel, R.drawable.compass, R.drawable.computer, R.drawable.cone, R.drawable.crown, R.drawable.diamond, R.drawable.dice};
        int randomIndex = new Random().nextInt(drawables.length -1);
        return drawables[randomIndex];
    }

    private static String getRandomText(){
        String[] strings = {"Warsaw","Warsaw1","Warsaw12","Warsaw123","Warsaw1234","Warsaw12345","Warsaw123456"};
        int randomIndex = new Random().nextInt(strings.length -1);
        return strings[randomIndex];
    }
}
