package cz.jaro.dopravnipodniky.other

import android.content.Context
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.activities.MainActivity
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse

object Tutorial {

    fun Context.zobrazitTutorial(x: Int): Boolean {

        if (x != vse.tutorial) return false

        return when (vse.tutorial) {
            1 -> {
                vse.tutorial = 2
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t1)
                    setPositiveButton(R.string.pojdme_na_to) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)

                    if (BuildConfig.DEBUG) setNegativeButton(getString(R.string.preskocit_tutorial)) { dialog, _ ->

                        vse.tutorial = 0

                        dialog.cancel()

                        val i = Intent(this@zobrazitTutorial, MainActivity::class.java)

                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(i)
                    }

                    show()
                }
                true
            }
            2 -> {
                vse.tutorial = 3
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t2)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            3 -> {
                vse.tutorial = 4
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t3)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            4 -> {
                vse.tutorial = 5
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t4)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            5 -> {
                vse.tutorial = 6
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t5)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            6 -> {
                vse.tutorial = 7
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t6)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            7 -> {
                vse.tutorial = 8
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.tutorial)
                    setMessage(R.string.t7)
                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    show()
                }
                true
            }
            else -> false

        }

}

}
