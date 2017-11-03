package mapzen.com.sdksampleapp

import com.mapzen.android.graphics.MapzenMap
import mapzen.com.sdksampleapp.controllers.RoutingController
import org.mockito.Mockito.mock

class TestRoutingController : RoutingController {

  private val map = mock(MapzenMap::class.java)

  override fun getMapzenMap(): MapzenMap? {
    return map
  }
}
