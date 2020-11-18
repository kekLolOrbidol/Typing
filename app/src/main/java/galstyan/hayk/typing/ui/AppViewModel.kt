package galstyan.hayk.typing.ui

import androidx.lifecycle.ViewModel
import galstyan.hayk.typing.model.AppContainer


abstract class AppViewModel(protected val appContainer: AppContainer) : ViewModel()