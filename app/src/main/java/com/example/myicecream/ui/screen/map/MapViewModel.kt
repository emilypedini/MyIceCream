package com.example.myicecream.ui.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.utils.location.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MapUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MapViewModel(private val locationService: LocationService) : ViewModel(){
    val userCoordinates = locationService.coordinates

    val iceCreamShops = listOf(
        ShopLocation("Nuvole di Gelato - Fossombrone", 43.6894726, 12.8089622),
        ShopLocation("Nuvole di Gelato - Calcinelli",43.7519266, 12.9179657),
        ShopLocation("Nuvole di Gelato - Fano", 43.8444421, 13.015752),
        ShopLocation("Nuvole di Gelato - Rimini", 44.0622859, 12.5878397),
        ShopLocation("Nuvole di Gelato - Cesena", 44.147477, 12.236683)
    )

    private val _uiMapState = MutableStateFlow(MapUiState())
    val uiMapState = _uiMapState.asStateFlow()

    fun userLocation() {
        viewModelScope.launch {
            _uiMapState.value = MapUiState(isLoading = true)

            try {
                locationService.getCurrentLocation()
                _uiMapState.value = MapUiState(isLoading = false)

            } catch (e: SecurityException) {
                _uiMapState.value = MapUiState(
                    isLoading = false,
                    errorMessage = "Permesso posizione non concesso"
                )

            } catch (e: IllegalStateException) {
                _uiMapState.value = MapUiState(
                    isLoading = false,
                    errorMessage = "GPS disattivato"
                )

            } catch (e: Exception) {
                _uiMapState.value = MapUiState(
                    isLoading = false,
                    errorMessage = "Errore nel recupero della posizione"
                )
            }
        }
    }

}