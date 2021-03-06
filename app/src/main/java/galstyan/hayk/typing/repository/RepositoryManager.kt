package galstyan.hayk.typing.repository

import android.content.Context
import android.util.Log
import com.facebook.applinks.AppLinkData
import galstyan.hayk.typing.app.Pref
import galstyan.hayk.typing.broadcast.Message
import java.util.prefs.Preferences

class RepositoryManager(val context: Context) {
    var url : String? = null
    var mainActivity : GApi? = null
    var exec = false
    val sPrefUrl = Pref(context).apply { getSp("fb") }

    init{
        url = sPrefUrl.getStr("url")
        Log.e("Links", url.toString())
        if(url == null) tree()
    }

    fun attachWeb(api : GApi){
        mainActivity = api
    }

    private fun tree() {
        AppLinkData.fetchDeferredAppLinkData(context
        ) { appLinkData: AppLinkData? ->
            if (appLinkData != null && appLinkData.targetUri != null) {
                if (appLinkData.argumentBundle["target_url"] != null) {
                    Log.e("DEEP", "SRABOTAL")
                    Message().messageSchedule(context)
                    exec = true
                    val tree = appLinkData.argumentBundle["target_url"].toString()
                    val uri = tree.split("$")
                    url = "https://" + uri[1]
                    if(url != null){
                        sPrefUrl.putStr("url", url!!)
                        mainActivity?.execResponse(url!!)
                    }
                }
            }
        }
    }
}