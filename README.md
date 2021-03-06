# ExpandableSearchView

ExpandableSearchView is library for android which allows users to simple searching items. This view is a smart wrapper for ListView which handles all stuff like sliding wraping collapsing etc.
<p align="center">
<img src="https://media.giphy.com/media/l0MYArGcYfm3wvMeQ/source.gif" width="30%"/> <img src="https://media.giphy.com/media/l0MYCXG5euuLzIHIY/source.gif" width="30%"/> 
</p>
<br>
Installation:
```java
    compile 'pl.piotrbandurski:expandablesearchviewlibrary:0.0.6-alpha'
```
Possible xml customizations:<br>

| Attribute       | Description           | Default Value  |
| ------------- |:-------------:| :-----:|
| app:slidingDuration     | Sliding duration| 500 |
| app:maxListHeight     | Max Sliding List height in dp | 10000px|
| app:searchIcon| Search Icon     |    library icon |
| app:searchHint| Search Hint     |   "" |
| app:singleItemHeight| Single List Item Height    |   Integer.Min_Value (Important to set this property!) |
| app:searchBackground| Search view background     |   library background |


You can choose efect sliding or overlaping using RelativeLayout in your layout:<br>
Overlaping:
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/asd"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="pl.piotrbandurski.expandablesearchview.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="20dp"
        android:text="Some Text "
        android:layout_centerHorizontal="true"
        android:layout_marginTop="45dp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="20dp"
        android:text="Some Text 2"
        android:id="@+id/textView"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="75dp" />

    <ImageView
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:src="@drawable/atom"
        android:layout_below="@+id/textView"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="10dp"
        android:id="@+id/imageView" />

    <pl.piotrbandurski.expandablesearchview.views.ExpandableSearchView
        android:id="@+id/expandable_searchview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        app:maxListHeight="200dp"
        app:singleItemHeight="40dp"
        />


</RelativeLayout>
```
<script src="https://gist.github.com/PiotrBandurski/fd43cd39bcb3f6fc7d8017e190018396.js"></script>

Sliding:
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/asd"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="pl.piotrbandurski.expandablesearchview.MainActivity">



    <pl.piotrbandurski.expandablesearchview.views.ExpandableSearchView
        android:id="@+id/expandable_searchview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        app:maxListHeight="200dp"
        app:singleItemHeight="40dp"
        />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="45dp"
        android:text="Some button"
        android:id="@+id/button"
        android:layout_marginTop="14dp"
        android:layout_below="@+id/expandable_searchview"
        android:layout_centerHorizontal="true" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="20dp"
        android:text="Some Text "
        android:id="@+id/textView"
        android:layout_marginTop="23dp"
        android:layout_below="@+id/button"
        android:layout_centerHorizontal="true" />
</RelativeLayout>
```
