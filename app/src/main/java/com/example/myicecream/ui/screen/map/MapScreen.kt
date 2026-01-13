package com.example.myicecream.ui.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import android.Manifest
import androidx.core.content.ContextCompat
import com.example.myicecream.utils.location.rememberMultiplePermissions
import org.osmdroid.views.overlay.Polyline

@Composable
fun MapScreen(viewModel: MapViewModel) {

    val context = LocalContext.current
    val userCoordinates by viewModel.userCoordinates.collectAsState()
    val uiMapState by viewModel.uiMapState.collectAsState()

    val permissionHandler = rememberMultiplePermissions( permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )) { }

    val selectedShop by viewModel.selectedShop.collectAsState()

    LaunchedEffect(Unit) {
        if (permissionHandler.statuses.values.all { it.isGranted }) {
            viewModel.userLocation()
        } else {
            permissionHandler.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                Configuration.getInstance().apply {
                    load(
                        context,
                        context.getSharedPreferences("osmdroid", 0)
                    )
                    userAgentValue = context.packageName
                }

                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    controller.setZoom(15.0)

                    // Centro iniziale (prima gelateria)
                    val startPoint = GeoPoint(
                        viewModel.iceCreamShops.first().latitude,
                        viewModel.iceCreamShops.first().longitude
                    )
                    controller.setCenter(startPoint)

                    viewModel.iceCreamShops.forEach { shop ->
                        val marker = Marker(this)
                        marker.position = GeoPoint(shop.latitude, shop.longitude)
                        marker.title = shop.name
                        overlays.add(marker)
                        marker.setOnMarkerClickListener{
                                clickedMarker, mapView ->
                            viewModel.onShopSelected(shop)
                            true
                        }
                    }
                }
            },
            update = { mapView ->

                userCoordinates?.let { coord ->
                    val userMarker = Marker(mapView)
                    userMarker.position = GeoPoint(coord.latitude, coord.longitude)
                    userMarker.title = "La tua posizione"
                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    userMarker.icon = ContextCompat.getDrawable(context,
                        android.R.drawable.presence_online
                    )
                    mapView.overlays.add(userMarker)
                    mapView.controller.setCenter(userMarker.position)
                }
            }
        )

        if (uiMapState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        uiMapState.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }
    }
}
