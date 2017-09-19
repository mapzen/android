package mapzen.com.sdksampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
