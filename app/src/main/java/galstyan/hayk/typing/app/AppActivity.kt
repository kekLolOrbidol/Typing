package galstyan.hayk.typing.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import galstyan.hayk.typing.R
import galstyan.hayk.typing.ui.main.MainFragment


class AppActivity : AppCompatActivity() {

	val app by lazy { (application as App?)!! }

	override fun onCreate(savedInstanceState: Bundle?) {
		supportFragmentManager.fragmentFactory = app.fragmentFactory
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity)
		if (savedInstanceState != null)
			return

		val navigation = NavigationImplement(supportFragmentManager, R.id.fragment_container, null)
		navigation.replace(MainFragment(app.viewModelFactory))
	}
}
