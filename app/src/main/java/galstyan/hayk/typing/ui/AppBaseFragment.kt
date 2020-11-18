package galstyan.hayk.typing.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class AppBaseFragment(
	protected val viewModelFactory: ViewModelProvider.Factory
) : Fragment()



