package lib.github1552980358.threadbus.interfaces

import java.io.Serializable

/**
 * @File    : HandlerBusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:59
 **/

abstract class HandlerBusInterface: BusInterface(), Serializable {
    val getRunnable by lazy {
        Runnable {
            try {
                doAction()
            } catch (e: Exception) {
                handler?.post { onException(e) }
                return@Runnable
            }
            handler?.post { onActionDone() }
        }
    }
}