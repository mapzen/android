package com.mapzen.places.api.sample;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.PendingResult;
import com.mapzen.android.lost.api.ResultCallback;
import com.mapzen.places.api.AutocompletePrediction;
import com.mapzen.places.api.AutocompletePredictionBuffer;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Places;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Demonstrates use of the {@link com.mapzen.places.api.GeoDataApi}.
 */
public class GeoDataApiActivity extends AppCompatActivity {

  LostApiClient client;

  ListView listView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geodata);
    listView = (ListView) findViewById(R.id.list_view);

    connectClient();
  }

  private void connectClient() {
    client = new LostApiClient.Builder(this).addConnectionCallbacks(
        new LostApiClient.ConnectionCallbacks() {

          @Override public void onConnected() {
            queryAutocompleteApi();
          }

          @Override public void onConnectionSuspended() {

          }
        }).build();
    client.connect();
  }

  private void queryAutocompleteApi() {
    String query = "pizza";
    LatLng pointA = new LatLng(40.020451, -105.274679);
    LatLng pointB = new LatLng(40.012004, -105.289957);
    LatLngBounds bounds = new LatLngBounds.Builder().include(pointA).include(pointB).build();
    PendingResult<AutocompletePredictionBuffer> result =
        Places.GeoDataApi.getAutocompletePredictions(client, query, bounds, null);
    result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
      @Override public void onResult(@NonNull AutocompletePredictionBuffer result) {
        displayAutocompleteItems(result);
      }
    });
  }

  private void displayAutocompleteItems(final AutocompletePredictionBuffer buffer) {
    listView.setAdapter(new BaseAdapter() {
      @Override public int getCount() {
        return buffer.getCount();
      }

      @Override public Object getItem(int i) {
        return buffer.get(i);
      }

      @Override public long getItemId(int i) {
        return i;
      }

      @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
          view = LayoutInflater.from(GeoDataApiActivity.this)
              .inflate(R.layout.list_item, parent, false);
          holder = new ViewHolder(view);
          view.setTag(holder);
        } else {
          view = convertView;
          holder = (ViewHolder) view.getTag();
        }
        AutocompletePrediction prediction = buffer.get(position);
        holder.title.setText(prediction.getPlaceId());
        holder.description.setText(prediction.getPrimaryText(null));
        return view;
      }
    });
  }

  private class ViewHolder {
    TextView title;
    TextView description;

    ViewHolder(View view) {
      title = (TextView) view.findViewById(R.id.title);
      description = (TextView) view.findViewById(R.id.description);
    }
  }
}
