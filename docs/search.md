# Search

## Getting Started

To start using Mapzen Search in your apps, you need a Mapzen developer API key. After you sign up for an API key you will need to include it in your application through the Java API or in a mapzen.xml properties file in your app resources folder.

**MySearchActivity.java**

```java
public class MySearchActivity extends Activity {
  @Override protected void onCreate(Bundle icicle) {
    MapzenSearch mapzenSearch = new MapzenSearch(this, “YOUR_MAPZEN_API_KEY”);
    ...
  }
```

**mapzen.xml**

```xml
<resources>
  <string name="search_key">[YOUR_SEARCH_KEY]</string>
</resources>
```

## Search
This is how you do a search query.

## Autocomplete
This is how you do autocomplete.

## Reverse
Yup, we do that too.

## PeliasSearchView
The Mapzen Android SDK has its own custom extension of `SearchView` that is integrated with autocomplete and search. Just drop an instance of PeliasSearchView into your layout and provide it with an AutoCompleteListView to display the results.

```java
// Configure search view
PeliasSearchView peliasSearchView = new PeliasSearchView(this);
ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
getSupportActionBar().setCustomView(peliasSearchView, layoutParams);
getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

// Configure autocomplete listview
AutoCompleteListView  listView = (AutoCompleteListView)
    findViewById(R.id.list_view);
AutoCompleteAdapter adapter = new AutoCompleteAdapter(this,
    android.R.layout.simple_list_item_1);
listView.setAdapter(autocompleteAdapter);

// Configure search service
MapzenSearch mapzenSearch = new MapzenSearch(this, “YOUR_MAPZEN_API_KEY”);

// Tying it all together
peliasSearchView.setAutoCompleteListView(listView);
peliasSearchView.setPelias(mapzenSearch);
peliasSearchView.setCallback(new Callback<Result>() {
  @Override public void success(Result result, Response response) {
    // Draw some pins on the map...
  }

  @Override public void failure(RetrofitError error) {
    // Uh oh
  }
});

```
