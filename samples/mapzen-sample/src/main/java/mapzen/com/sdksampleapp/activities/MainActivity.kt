package mapzen.com.sdksampleapp.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.models.SampleMap

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

  internal var current: Sample? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupBottomNavigation()
    navbarItemSelected(R.id.navigation_map)
  }

  private fun setupBottomNavigation() {
    val nav = findViewById<BottomNavigationView>(R.id.navigation)
    nav.setOnNavigationItemSelectedListener { item ->
      navbarItemSelected(item.itemId)
      true
    }
  }

  private fun configureScrollView(samples: Array<Sample>?) {
    val layout = findViewById<LinearLayout>(R.id.scrollContent)
    layout.removeAllViews()

    if (samples == null) {
      return
    }
    for (sample in samples) {
      val inflater = LayoutInflater.from(this)
      val textView = inflater.inflate(R.layout.text_row, null) as TextView
      textView.text = sample.title
      textView.setOnClickListener(this)
      textView.tag = sample
      val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT,
          WRAP_CONTENT)
      if (sample === samples[samples.size - 1]) {
        val rightMargin = resources.getDimensionPixelSize(R.dimen.padding_large)
        layoutParams.setMargins(0, 0, rightMargin, 0)
      }
      layout.addView(textView, layoutParams)
    }
  }

  override fun onClick(view: View) {
    val sample = view.tag as Sample
    if (current == null) {
      return
    }
    var curr = current as Sample
    curr.takedown(null, null, null)
    current = sample
    curr.setup(null, null, null)
  }

  private fun navbarItemSelected(itemId: Int) {
    val samples = SampleMap.NAV_ID_TO_SECTIONS.get(itemId)
    configureScrollView(samples)
    if (current != null) {
      (current as Sample).takedown(null, null, null) //TODO
      current = null
    }
    if (samples != null && !samples.isEmpty()) {
      current = samples[0]
    }
    if (current != null) {
      current!!.setup(null, null, null) //TODO
    }
  }
}
