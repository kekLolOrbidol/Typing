package galstyan.hayk.typing.ui.navigation

import androidx.fragment.app.Fragment


interface Navigation {

    fun replace(fragment: Fragment)

    fun push(fragment: Fragment)

    fun pop()

    fun peek(): Fragment?
}