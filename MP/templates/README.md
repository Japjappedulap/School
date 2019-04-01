# How to use

## Simple list container with expandable view

* Import `android-flexbox` by adding to gradle dependencies
```
implementation 'com.google.android:flexbox:1.0.0 
```
> Ignore 1.1.0 version warning!
* Copy [`list_item_content.xml`](https://github.com/Japjappedulap/School/blob/master/MP/templates/ListContainer/app/src/main/res/layout/list_item_content.xml) and [`list_item_header`](https://github.com/Japjappedulap/School/blob/master/MP/templates/ListContainer/app/src/main/res/layout/list_item_header.xml) to your layout
* Copy java classes [`ExpandableListAdapter.java`](https://github.com/Japjappedulap/School/blob/master/MP/templates/ListContainer/app/src/main/java/s/pvie2207/list/ExpandableListAdapter.java) and [`Product.java`](https://github.com/Japjappedulap/School/blob/master/MP/templates/ListContainer/app/src/main/java/s/pvie2207/list/Product.java) to your java package
* Add the [`initData`](https://github.com/Japjappedulap/School/blob/36ee61e16fa61c3347e50c0787d7022fa78a18ad/MP/templates/ListContainer/app/src/main/java/s/pvie2207/list/MainActivity.java#L23) method to the main activity where you want to use the list
* Add the [`ExpandableListView`](https://developer.android.com/reference/android/widget/ExpandableListView) to your `ACITIVITY_NAME.xml` file, just like [this](https://github.com/Japjappedulap/School/blob/36ee61e16fa61c3347e50c0787d7022fa78a18ad/MP/templates/ListContainer/app/src/main/res/layout/activity_main.xml#L10) 

#### To customize

* Change the product class to suit your needs and then, modify the [`getGroupView()`](https://github.com/Japjappedulap/School/blob/36ee61e16fa61c3347e50c0787d7022fa78a18ad/MP/templates/ListContainer/app/src/main/java/s/pvie2207/list/ExpandableListAdapter.java#L60) and [`getChildView()`](https://github.com/Japjappedulap/School/blob/36ee61e16fa61c3347e50c0787d7022fa78a18ad/MP/templates/ListContainer/app/src/main/java/s/pvie2207/list/ExpandableListAdapter.java#L79) methods to render the object to the given layout
