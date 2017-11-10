package mapzen.com.sdksampleapp.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import kotterknife.bindView
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.presenters.MainPresenter
import javax.inject.Inject

/**
 * Entry point for the sample app. Displays bottom navigation bar with top scroll view for
 * interaction with different SDK use cases.
 */
class MainActivity : BaseActivity(), MainController {

  val navigationView: BottomNavigationView by bindView(R.id.navigation)
  val scrollContent: LinearLayout by bindView(R.id.scrollContent)
  val scrollView: HorizontalScrollView by bindView(R.id.scrollView)

  @Inject lateinit var presenter: MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mainApplication.appComponent.inject(this)
    presenter.controller = this
    presenter.onCreate()
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
  }

  override fun onDestroy() {
    presenter.onDestroy()
    super.onDestroy()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.activity_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    presenter.onOptionsItemSelected(item?.itemId)
    return true
  }

  override fun selectSampleView(sample: Sample) {
    (0..scrollContent.childCount)
      .filter { it -> scrollContent.getChildAt(it) is TextView }
      .map { scrollContent.getChildAt(it) }
      .forEach { it.isSelected = (it.tag == sample) }
  }

  override fun setupNavigationItemSelectedListener() {
    navigationView?.setOnNavigationItemSelectedListener { item ->
      presenter.onNavBarItemSelected(item.itemId)
      true
    }
  }

  override fun cleanupNavigationItemSelectedListener() {
    navigationView?.setOnNavigationItemSelectedListener(null)
  }

  override fun clearScrollViewSamples() {
    scrollContent?.removeAllViews()
  }

  override fun cleanupSampleFragment() {
    val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
    fragment?.let { supportFragmentManager.beginTransaction().remove(it).commit() }
  }

  override fun setupSampleFragment(sample: Sample) {
    val fragment = sample.fragmentClass.java.newInstance()
    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
  }

  override fun setScrollViewSamples(samples: Array<Sample>?) {
    if (samples == null) { return }
    for (sample in samples) {
      val inflater = LayoutInflater.from(this)
      val textView = inflater.inflate(R.layout.text_row, null) as TextView
      textView.text = presenter?.getTitleText(sample)
      textView.setOnClickListener { view ->
        scrollView.smoothScrollTo(view.x.toInt(), 0)
        val sample = view.tag as Sample
        presenter?.onSampleSelected(sample)
      }
      textView.tag = presenter?.getTag(sample)
      val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
      if (sample != samples[samples.size - 1]) {
        val rightMargin = resources.getDimensionPixelSize(R.dimen.padding)
        layoutParams.setMargins(0, 0, rightMargin, 0)
      }
      scrollContent?.addView(textView, layoutParams)
    }
  }

  override fun cleanupScrollItemClickListeners() {
    (0..scrollContent.childCount)
      .filter { it -> scrollContent.getChildAt(it) is TextView }
      .map { scrollContent.getChildAt(it) as TextView }
      .forEach { it.setOnClickListener(null) }
  }

  override fun openSettings() {
    val intent = Intent(this, SettingsActivity::class.java)
    startActivity(intent)
  }
}
