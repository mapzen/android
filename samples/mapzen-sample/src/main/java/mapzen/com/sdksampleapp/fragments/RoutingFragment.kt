package mapzen.com.sdksampleapp.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.mapzen.android.graphics.MapzenMap
import kotterknife.bindView
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.RoutingController
import mapzen.com.sdksampleapp.presenters.RoutingPresenter
import javax.inject.Inject

/**
 * Routing sample which demonstrates making route requests and displaying result on map.
 */
class RoutingFragment : BaseFragment(), RoutingController {

  val tabLayout: TabLayout by bindView(R.id.tabLayout)

  @Inject lateinit var presenter: RoutingPresenter

  override fun getLayoutId(): Int {
    return R.layout.fragment_routing
  }

  override fun onMapSetup() {
    presenter.setupMap()
  }

  override fun getMapzenMap(): MapzenMap? {
    return map
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainApplication.appComponent.inject(this)
    presenter.controller = this
    presenter.onCreate()
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.selectedMode?.let { tabLayout.getTabAt(it)?.select() }
    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {
      }

      override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let { presenter.onTabPositionSelected(it) }
      }
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.onDestroyView()
  }
}
