package com.example.taskforcft

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import com.example.taskforcft.compose_class.MainActivityCompose
import com.example.taskforcft.ui.theme.TaskForCFTTheme
import java.util.prefs.Preferences


class MainActivity : ComponentActivity() {
    val composeMethod = MainActivityCompose()

    private lateinit var dataStore: DataStore<Preferences>

    companion object {
        private const val PREFS_NAME = "myPrefs"
        private const val KEY_MY_VALUE = "myValue"
        private const val PREFS_LOG = "myLoginPref"
        private const val KEY_LOG_IN = "Login"


        fun saveMyValue(context: Context, value: String) {
            val prefs =
                context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(KEY_MY_VALUE, value)
            editor.apply()
        }

        fun setLogin(context: Context, value: Boolean) {
            val prefs =
                context.applicationContext.getSharedPreferences(PREFS_LOG, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(KEY_LOG_IN, value)
            editor.apply()
        }

        fun getMyValue(context: Context): String? {
            val prefs =
                context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getString(KEY_MY_VALUE, null)
        }
        fun isLogin(context: Context): Boolean {
            val prefs =
                context.applicationContext.getSharedPreferences(PREFS_LOG, Context.MODE_PRIVATE)
            return prefs.getBoolean(KEY_LOG_IN, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskForCFTTheme {
                if (!isLogin(this@MainActivity))
                    composeMethod.Greeting(this@MainActivity)
                else{
                    val intent = Intent(this@MainActivity, MainScreen::class.java)
                    this@MainActivity.startActivity(intent)
                }
            }


        }

    }

}


