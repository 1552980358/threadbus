package lib.github1552980358.threadbus.interfaces

import java.io.Serializable

/**
 * @File    : HandlerTask
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:59
 **/

abstract class HandlerTask: BaseActionInterface(), Serializable {
    val getRunnable by lazy {
        Runnable {
            try {
                executeTask(resultMap)
            } catch (e: Exception) {
                handler?.post { onExceptionOccurs(e, resultMap) }
                return@Runnable
            }
            handler?.post { onTaskComplete(resultMap) }
        }
    }
}