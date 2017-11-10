package mapzen.com.sdksampleapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import kotterknife.bindView
import mapzen.com.sdksampleapp.R

/**
 * Checks that required permissions are granted before launching [MainActivity] or exiting app.
 */
class PermissionActivity : AppCompatActivity() {

  companion object {
    private val PERMISSIONS_REQUEST_CODE = 1
    private val NUMBER_OF_PERMISSIONS = 2
  }

  val progressBar: ProgressBar by bindView(R.id.progressBar)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_permissions)
    progressBar.isIndeterminate = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkRuntimePermissions()
    }
  }

  private fun permissionNotGranted(): Boolean {
    return ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
  }

  private fun requestPermission() {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_CODE)
  }

  private fun checkRuntimePermissions() {
    if (permissionNotGranted()) {
      requestPermission()
      showSpinner()
    } else {
      startMainActivity()
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
      grantResults: IntArray) {
    hideSpinner()
    when (requestCode) {
      PERMISSIONS_REQUEST_CODE ->
        if (permissionsGranted(grantResults)) {
          startMainActivity()
        } else {
          showToast()
        }
      else ->
        showToast()
    }
  }

  fun permissionsGranted(grantResults: IntArray): Boolean {
    return grantResults.size == NUMBER_OF_PERMISSIONS &&
        grantResults[0] == PackageManager.PERMISSION_GRANTED
  }

  fun startMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
  }

  fun showToast() {
    Toast.makeText(this, getString(R.string.need_permissions),
        Toast.LENGTH_SHORT).show()
  }

  fun showSpinner() {
    progressBar.visibility = View.VISIBLE
  }

  fun hideSpinner() {
    progressBar.visibility = View.GONE
  }
}
