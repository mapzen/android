package com.mapzen.android.sdk.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main entry point for app. Displays list of SDK features and allows launching individual
 * activities to showcase specific SDK features.
 *
 * If app is running on api 23 or above, handles requesting runtime permissions and displays
 * UI if permissions aren't granted.
 */
public class MainActivity extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_CODE = 1;
  private static final int NUMBER_OF_PERMISSIONS = 2;

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      ViewHolder viewHolder = (ViewHolder) v.getTag();
      startActivity(new Intent(MainActivity.this, viewHolder.activityClass));
    }
  };

  ProgressBar progressBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    progressBar.setIndeterminate(true);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    configureListView();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkRuntimePermissions();
    }
  }

  private class ViewHolder {
    TextView title;
    TextView description;
    Class activityClass;

    ViewHolder(View view) {
      title = (TextView) view.findViewById(R.id.title);
      description = (TextView) view.findViewById(R.id.description);
    }
  }

  private void configureListView() {
    ListView listView = (ListView) findViewById(R.id.list_view);
    listView.setAdapter(new BaseAdapter() {
      @Override public int getCount() {
        return SampleDetailsList.DEMOS.length;
      }

      @Override public Object getItem(int position) {
        return null;
      }

      @Override public long getItemId(int position) {
        return 0;
      }

      @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
          view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, parent, false);
          holder = new ViewHolder(view);
          view.setTag(holder);
        } else {
          view = convertView;
          holder = (ViewHolder) view.getTag();
        }
        SampleDetails demo = SampleDetailsList.DEMOS[position];
        holder.title.setText(demo.getTitleId());
        holder.description.setText(demo.getDetailId());
        holder.activityClass = demo.getActivityClass();
        view.setOnClickListener(onClickListener);
        return view;
      }
    });
  }

  private boolean permissionNotGranted() {
    return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(this, new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    }, PERMISSIONS_REQUEST_CODE);
  }

  private void showSpinner() {
    progressBar.setVisibility(View.VISIBLE);
  }

  private void hideSpinner() {
    progressBar.setVisibility(View.GONE);
  }

  private void checkRuntimePermissions() {
    if (permissionNotGranted()) {
      requestPermission();
      showSpinner();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    hideSpinner();
    switch (requestCode) {
      case PERMISSIONS_REQUEST_CODE:
        if (grantResults.length != NUMBER_OF_PERMISSIONS
            || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(MainActivity.this, getString(R.string.need_permissions),
              Toast.LENGTH_SHORT).show();
        }
        break;
      default:
        Toast.makeText(MainActivity.this, getString(R.string.need_permissions), Toast.LENGTH_SHORT)
            .show();
        break;
    }
  }
}
