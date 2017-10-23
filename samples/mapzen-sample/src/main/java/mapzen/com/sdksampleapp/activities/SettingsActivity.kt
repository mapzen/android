package mapzen.com.sdksampleapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import mapzen.com.sdksampleapp.fragments.SettingsFragment

/**
 * Settings activity.
 */
class SettingsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    fragmentManager.beginTransaction()
        .replace(android.R.id.content, SettingsFragment())
        .commit()
  }
}
