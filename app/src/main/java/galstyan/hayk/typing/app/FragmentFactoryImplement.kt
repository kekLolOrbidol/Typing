package galstyan.hayk.typing.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider


class FragmentFactoryImplement(
	private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

	override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
		return try {
			val cls = loadFragmentClass(classLoader, className)
			cls.getConstructor(ViewModelProvider.Factory::class.java).newInstance(viewModelFactory)
		} catch (e: InstantiationException) {
			super.instantiate(classLoader, className)
		}
	}
}