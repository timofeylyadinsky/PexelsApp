package lt.timofey.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import lt.timofey.domain.usecases.getCuratedPhotosUseCase
import lt.timofey.domain.usecases.getFeaturedCollectionsUseCase
import lt.timofey.domain.usecases.getSearchPhotosUseCase
import lt.timofey.ui.state.HomeScreenUIState
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: getFeaturedCollectionsUseCase,
    private val getCuratedPhotosUseCase: getCuratedPhotosUseCase,
    private val searchPhotosUseCase: getSearchPhotosUseCase
) {

    val uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState())

    init {

    }


}