package mapzen.com.sdksampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupScrollContent();
  }

  private void setupScrollContent() {
    LinearLayout layout = findViewById(R.id.scrollContent);
    for (int i = 0; i < 30; i++) {
      LayoutInflater inflater = LayoutInflater.from(this);
      TextView textView = (TextView) inflater.inflate(R.layout.text_row, null);
      textView.setText("Text: " + i);
      textView.setOnClickListener(this);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT,
          WRAP_CONTENT);
      if (i < 29) { //TODO !isLast()
        int rightMargin = getResources().getDimensionPixelSize(R.dimen.padding_large);
        layoutParams.setMargins(0, 0, rightMargin, 0);
      }
      layout.addView(textView, i, layoutParams);
    }
  }

  @Override public void onClick(View view) {
    //TODO
  }
}
