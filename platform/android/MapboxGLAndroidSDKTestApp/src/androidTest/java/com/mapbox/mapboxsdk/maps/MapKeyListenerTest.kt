package com.mapbox.mapboxsdk.maps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.KeyEvent
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.constants.MapboxConstants
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.GesturesUiTestUtils.move
import com.mapbox.mapboxsdk.maps.GesturesUiTestUtils.quickScale
import com.mapbox.mapboxsdk.testapp.R
import com.mapbox.mapboxsdk.testapp.activity.BaseTest
import com.mapbox.mapboxsdk.testapp.activity.maplayout.SimpleMapActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MapKeyListenerTest : BaseTest() {
  override fun getActivityClass() = SimpleMapActivity::class.java

  private var maxWidth: Int = 0
  private var maxHeight: Int = 0

  @Before
  fun setup() {
    maxWidth = mapView.width
    maxHeight = mapView.height
  }

  @Test
  fun pitchAdjustWithShiftAndUp() {
    validateTestSetup()
    var initialCameraPosition: CameraPosition? = null
    rule.runOnUiThread {
      // zoom in so we can move vertically
      mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(10.0))
      initialCameraPosition = mapboxMap.cameraPosition
      mapboxMap.uiSettings.isTiltGesturesEnabled = true
    }
    onView(withId(R.id.mapView)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_SHIFT_LEFT),
      ViewActions.pressKey(KeyEvent.KEYCODE_DPAD_UP))
    rule.runOnUiThread {
      Assert.assertNotEquals(initialCameraPosition!!.tilt, mapboxMap.cameraPosition.tilt)
    }
  }

  @Test
  fun targetAdjustWithUp() {
    validateTestSetup()
    var initialCameraPosition: CameraPosition? = null
    rule.runOnUiThread {
      // zoom in so we can move vertically
      mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(4.0))
      initialCameraPosition = mapboxMap.cameraPosition
      mapboxMap.uiSettings.isQuickZoomGesturesEnabled = false
    }

    // Tap the up arrow on the keyboard directional pad
    onView(withId(R.id.mapView)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_DPAD_UP))

    rule.runOnUiThread {
      Assert.assertNotEquals(initialCameraPosition!!.target.latitude, mapboxMap.cameraPosition.target.latitude)
      Assert.assertNotEquals(initialCameraPosition!!.target.longitude, mapboxMap.cameraPosition.target.longitude)
    }
  }

  @Test
  fun targetAdjustWithDown() {
    validateTestSetup()
    var initialCameraPosition: CameraPosition? = null
    rule.runOnUiThread {
      // zoom in so we can move vertically
      mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(4.0))
      initialCameraPosition = mapboxMap.cameraPosition
      mapboxMap.uiSettings.isQuickZoomGesturesEnabled = false
    }

    // Tap the down arrow on the keyboard directional pad
    onView(withId(R.id.mapView)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_DPAD_DOWN))

    rule.runOnUiThread {
      Assert.assertNotEquals(initialCameraPosition!!.target.latitude, mapboxMap.cameraPosition.target.latitude)
      Assert.assertNotEquals(initialCameraPosition!!.target.longitude, mapboxMap.cameraPosition.target.longitude)
    }
  }
}