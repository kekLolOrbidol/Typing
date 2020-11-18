package galstyan.hayk.typing.ui

import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment


fun Fragment.getColor(@ColorRes colorRes: Int): Int {
	return ResourcesCompat.getColor(resources, colorRes, null)
}