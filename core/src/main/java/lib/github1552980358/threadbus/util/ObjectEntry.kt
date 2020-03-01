@file:Suppress("unused")

package lib.github1552980358.threadbus.util

import android.os.Handler
import android.os.Looper
import lib.github1552980358.threadbus.ThreadBus
import lib.github1552980358.threadbus.interfaces.HandlerTask
import lib.github1552980358.threadbus.interfaces.ThreadTask

/**
 * @File    : ObjectEntry
 * @Author  : 1552980358
 * @Date    : 2019/12/17
 * @TIME    : 18:13
 * @since     : v0.5
 **/

/* For java */
@Deprecated("Seems Useless, use original method calling", ReplaceWith(""), DeprecationLevel.ERROR)
class ObjectEntry {
    companion object {
        /**
         * Call ThreadBus in Any object
         * @author 1552980358
         *
         * @since v0.5
         **/
        fun threadBus() = ThreadBus.threadBus
        
        /**
         * Setup a looper for called class
         * @author 1552980358
         * @since v0.5
         **/
        fun setUpLooper() = ThreadBus.setUpLooper()
    }
}

/* For Kotlin */
/**
 * @fun [threadBus]
 * Call ThreadBus in Any object
 * @author 1552980358
 * @since v0.5
 **/
fun threadBus(): ThreadBus = ThreadBus.threadBus

/**
 * @fun [setUpLooper]
 * Setup a looper for called class
 * @author 1552980358
 * @since v0.5
 **/
fun setUpLooper() = ThreadBus.setUpLooper()

/**
 * @fun [executeTask]
 * @author 1552980358
 * @since v0.17
 * @date 2020/2/20
 * @time 17:40
 * @description
 * @return ThreadBus
 **/
fun executeTask(handlerName: String, task: HandlerTask, delayMillis: Long = 0) =
    threadBus().executeTask(handlerName, task, delayMillis)

/**
 * @fun [registerHandler]
 * @author 1552980358
 * @since v
 * @date 2020/2/20
 * @time 17:45
 * @description
 * @return ThreadBus
 **/
fun registerHandler(handlerName: String, handler: Handler?) =
    threadBus().registerHandler(handlerName, handler)

/**
 * @fun [registerHandler]
 * @author 1552980358
 * @since v0.17
 * @date 2020/2/20
 * @time 17:47
 * @description
 * @return ThreadBus
 **/
fun registerHandler(handlerName: String, looper: Looper) =
    threadBus().registerHandler(handlerName, looper)


/**
 * @fun [registerHandler]
 * @author 1552980358
 * @since v
 * @date 2020/2/20
 * @time 17:48
 * @description
 * @return ThreadBus
 **/
fun registerHandler(handlerName: String) =
    threadBus().registerHandler(handlerName)


/**
 * @fun [registerHandler]
 * @author 1552980358
 * @since v
 * @date 2020/3/1
 * @time 11:12
 * @description
 * @return ThreadBus
 **/
fun unregisterHandler(handlerName: String) =
    threadBus().unregisterHandler(handlerName)

/**
 * @fun [postHandler]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:19
 * @description
 * @return ThreadBus
 **/
@JvmOverloads
fun postHandler(handlerName: String, runnable: Runnable, delayMillis: Long = 0) =
    threadBus().postHandler(handlerName, runnable, delayMillis)

/**
 * @fun [getLooper]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:20
 * @description
 * @return ThreadBus
 **/
fun getLooper(handlerName: String) = 
    threadBus().getLooper(handlerName)

/**
 * @fun [registerThread]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:21
 * @description
 * @return ThreadBus
 **/
fun registerThread(threadName: String) = 
    threadBus().registerThread(threadName)

/**
 * @fun [unregisterPriorityDivisionThread]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:22
 * @description
 * @return ThreadBus
 **/
fun unregisterPriorityDivisionThread(threadName: String) = 
    threadBus().unregisterPriorityDivisionThread(threadName)

/**
 * @fun [registerThread]
 * @author 1552980358
 * @since v
 * @date 2020/3/1
 * @time 14:23
 * @description
 * @return ThreadBus
 **/
fun registerThread(threadName: String, thread: Thread) = 
    threadBus().registerThread(threadName, thread)

/**
 * @fun [unregisterThread]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:23
 * @description
 * @return ThreadBus
 **/
fun unregisterThread(threadName: String) =
    threadBus().unregisterThread(threadName)

/* ================ THREAD ===================== */

/**
 * @fun [executeTask]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:25
 * @description
 * @return ThreadBus
 **/
fun executeTask(threadName: String, task: ThreadTask, priority: Priority) =
    threadBus().executeTask(threadName, task, priority)

/**
 * @fun [updateTimeGap]
 * @author 1552980358
 * @since v.17
 * @date 2020/3/1
 * @time 14:28
 * @description
 * @return ThreadBus
 **/
fun updateTimeGap(threadName: String, timeGap: Int) = 
    threadBus().updateTimeGap(threadName, timeGap)
fun updateTimeGap(threadName: String, timeGap: Long) =
    threadBus().updateTimeGap(threadName, timeGap)

/**
 * @fun [stopCurrentExecution]
 * @author 1552980358
 * @since v0.17
 * @date 2020/3/1
 * @time 14:29
 * @description
 * @return ThreadBus
 **/
fun stopCurrentExecution(threadName: String) =
    threadBus().stopCurrentExecution(threadName)

/**
 * @fun [sendMessage]
 * @author 1552980358
 * @since v
 * @date 2020/3/1
 * @time 14:30
 * @description
 * @return ThreadBus
 **/
fun sendMessage(threadName: String, message: ThreadMessage) =
    threadBus().sendMessage(threadName, message)