package mapzen.com.sdksampleapp.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.presenters.MainPresenter
import mapzen.com.sdksampleapp.presenters.MainPresenterImpl

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
class MainActivity : AppCompatActivity(), MainController {

  internal val presenter: MainPresenter = MainPresenterImpl() //TODO inject

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    presenter.controller = this
    presenter.onCreate()
  }

  override fun onDestroy() {
    presenter.onDestroy()
    super.onDestroy()
  }

  override fun setupNavigationItemSelectedListener() {
    val nav = findViewById<BottomNavigationView>(R.id.navigation) //TODO inject
    nav.setOnNavigationItemSelectedListener { item ->
      presenter.onNavBarItemSelected(item.itemId)
      true
    }
  }

  override fun cleanupNavigationItemSelectedListener() {
    val nav = findViewById<BottomNavigationView>(R.id.navigation) //TODO inject
    nav.setOnNavigationItemSelectedListener(null)
  }

  override fun clearScrollViewSamples() {
    findViewById<LinearLayout>(R.id.scrollContent).removeAllViews() //TODO inject
  }

  override fun setScrollViewSamples(samples: Array<Sample>?) {
    if (samples == null) { return }
    val scrollContent = findViewById<LinearLayout>(R.id.scrollContent) //TODO inject
    for (sample in samples) {
      val inflater = LayoutInflater.from(this)
      val textView = inflater.inflate(R.layout.text_row, null) as TextView
      textView.text = presenter?.getTitleText(sample)
      textView.setOnClickListener { view ->
        val sample = view.tag as Sample
        presenter?.onSampleSelected(sample)
      }
      textView.tag = presenter?.getTag(sample)
      val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
      if (sample === samples[samples.size - 1]) {
        val rightMargin = resources.getDimensionPixelSize(R.dimen.padding_large)
        layoutParams.setMargins(0, 0, rightMargin, 0)
      }
      scrollContent.addView(textView, layoutParams)
    }
  }


  override fun cleanupScrollItemClickListeners() {
    val scrollContent = findViewById<LinearLayout>(R.id.scrollContent) //TODO inject
    (0..scrollContent.childCount)
        .map { scrollContent.getChildAt(it) as TextView }
        .forEach { it.setOnClickListener(null) }
  }
}
