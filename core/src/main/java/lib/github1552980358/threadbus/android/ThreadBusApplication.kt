package lib.github1552980358.threadbus.android

import android.app.Application
import lib.github1552980358.threadbus.ThreadBus

/**
 * @File    : ThreadBusApplication
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:02
 **/

class ThreadBusApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // should be initialized in onCreate() of application
        ThreadBus.threadBus
    }
}