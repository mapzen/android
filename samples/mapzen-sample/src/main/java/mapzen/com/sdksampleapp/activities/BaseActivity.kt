package mapzen.com.sdksampleapp.activities

import android.support.v7.app.AppCompatActivity
import mapzen.com.sdksampleapp.MainApplication

/**
 * Base activity class for all activities in the sample app.
 */
open class BaseActivity : AppCompatActivity() {

  val mainApplication : MainApplication by lazy {
    application as MainApplication
  }
}
