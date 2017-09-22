package mapzen.com.sdksampleapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import mapzen.com.sdksampleapp.R;
import mapzen.com.sdksampleapp.models.Sample;
import mapzen.com.sdksampleapp.models.SampleMap;

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
public class MainActivity extends AppCompatActivity implements
    View.OnClickListener {

  Sample current = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupBottomNavigation();
    navbarItemSelected(R.id.navigation_map);
  }

  private void setupBottomNavigation() {
    BottomNavigationView nav = findViewById(R.id.navigation);
    nav.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navbarItemSelected(item.getItemId());
        return true;
      }
    });
  }

  private void configureScrollView(Sample[] samples) {
    LinearLayout layout = findViewById(R.id.scrollContent);
    layout.removeAllViews();
    if (samples == null) {
      return;
    }
    for (Sample sample : samples) {
      LayoutInflater inflater = LayoutInflater.from(this);
      TextView textView = (TextView) inflater.inflate(R.layout.text_row, null);
      textView.setText(sample.title);
      textView.setOnClickListener(this);
      textView.setTag(sample);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT,
          WRAP_CONTENT);
      if (sample == samples[samples.length - 1]) {
        int rightMargin = getResources().getDimensionPixelSize(R.dimen.padding_large);
        layoutParams.setMargins(0, 0, rightMargin, 0);
      }
      layout.addView(textView, layoutParams);
    }
  }

  @Override public void onClick(View view) {
    Sample sample = (Sample) view.getTag();
    current.takedown(null, null, null);
    current = sample;
    current.setup(null, null, null);
  }

  private void navbarItemSelected(int itemId) {
    Sample[] samples = SampleMap.NAV_ID_TO_SECTIONS.get(itemId);
    configureScrollView(samples);
    if (current != null) {
      current.takedown(null, null, null); //TODO
      current = null;
    }
    if (samples != null && samples.length > 0) {
      current = samples[0];
    }
    if (current != null) {
      current.setup(null, null, null); //TODO
    }
  }
}
