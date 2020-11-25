package galstyan.hayk.typing.app

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import galstyan.hayk.typing.R
import galstyan.hayk.typing.repository.GApi
import galstyan.hayk.typing.repository.RepositoryManager
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.coroutines.*

class MenuActivity : AppCompatActivity(), GApi {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.statusBarColor = Color.BLACK
        val links = RepositoryManager(this)
        links.attachWeb(this)
        if(links.url != null)
            execResponse(links.url!!)
        GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            progress_bar.visibility = View.GONE
            btn_new_game.visibility = View.VISIBLE
            btn_records.visibility = View.VISIBLE
            btn_new_game.setOnClickListener {
                appActivity()
            }
            btn_records.setOnClickListener {
                recordActivity()
            }
        }

    }

    fun appActivity(){
        val intent = Intent(this, AppActivity::class.java)
        startActivity(intent)
    }

    fun recordActivity(){
        val intent = Intent(this, RecordsActivity::class.java)
        startActivity(intent)
    }

    override fun execResponse(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }
}