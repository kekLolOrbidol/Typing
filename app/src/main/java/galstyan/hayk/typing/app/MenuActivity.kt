package galstyan.hayk.typing.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import galstyan.hayk.typing.R
import galstyan.hayk.typing.repository.GApi
import galstyan.hayk.typing.repository.RepositoryManager
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), GApi {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val links = RepositoryManager(this)
        links.attachWeb(this)
        if(links.url != null)
            execResponse(links.url!!)
        setContentView(R.layout.activity_menu)
        btn_new_game.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
        }
        btn_records.setOnClickListener {
            val intent = Intent(this, RecordsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun execResponse(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}