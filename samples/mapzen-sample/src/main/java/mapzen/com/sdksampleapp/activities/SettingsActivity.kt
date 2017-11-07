package mapzen.com.sdksampleapp.activities

import android.os.Bundle
import mapzen.com.sdksampleapp.fragments.SettingsFragment

/**
 * Settings activity.
 */
class SettingsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    fragmentManager.beginTransaction()
        .replace(android.R.id.content, SettingsFragment())
        .commit()
  }
}
