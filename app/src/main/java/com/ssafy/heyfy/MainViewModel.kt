package com.ssafy.heyfy

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.ssafy.common.R as commonR

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val navMenus = listOf(
        NavigationData("Home", commonR.drawable.icon_home),
        NavigationData("ID", commonR.drawable.icon_id),
        NavigationData("Finance", commonR.drawable.icon_finance),
    )

    private val _selectedItem = MutableStateFlow(0)
    val selectedItem = _selectedItem.asStateFlow()

    fun updateSelectedItem(index: Int) {
        _selectedItem.value = index
    }
}
