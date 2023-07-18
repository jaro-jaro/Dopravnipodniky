package cz.jaro.dopravnipodniky.other

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        res = resources
        prefs = getSharedPreferences("podnik", Context.MODE_PRIVATE)
    }

    companion object {
        lateinit var res: Resources
        lateinit var prefs: SharedPreferences
    }
}
