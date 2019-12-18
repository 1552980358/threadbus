package example.github1552980358.threadbus

import android.app.Application
import lib.github1552980358.threadbus.util.threadBus

/**
 * @File    : Application
 * @Author  : 1552980358
 * @Date    : 2019/12/18
 * @TIME    : 8:44
 **/


class Application: Application() /* ThreadBusApplication() is suggested */ {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize, simpler calling method provided for Kotlin only
        // Overriding
        threadBus() // = ThreadBus.threadBus
    }
}