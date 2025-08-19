package com.ssafy.mento_club

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.DestinationParamConstants
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MentoClubViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val type = savedStateHandle.get<String>(DestinationParamConstants.MENTO_CLUB_TYPE) ?: ""

    fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }

    fun goToSuccess() {
        viewModelScope.launch {
            delay(500)
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
            )
        }
    }
}
