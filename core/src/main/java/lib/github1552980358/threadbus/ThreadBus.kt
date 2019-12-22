package lib.github1552980358.threadbus

import android.os.Handler
import android.os.Looper
import lib.github1552980358.threadbus.interfaces.HandlerBusInterface
import lib.github1552980358.threadbus.interfaces.ThreadBusInterface
import lib.github1552980358.threadbus.util.Priority
import lib.github1552980358.threadbus.util.ThreadMessage
import java.io.Serializable

/**
 * @File    : ThreadBus
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:57
 **/

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class ThreadBus private constructor(): Serializable {
    
    companion object {
        
        /**
         * @author 1552980358
         **/
        val threadBus by lazy { ThreadBus() }
        
        const val CREATE = "CREATE"
    
        /**
         * Set up looper for called thread, should be set before
         * @author 1552980358
         * @since v0.5
         * @see registerHandler(String)
         * to ThreadBus
         **/
        fun setUpLooper() {
            Looper.prepare()
            Looper.loop()
        }
        
    }
    
    /**
     * handlerMap: storing handlers
     * @author 1552980358
     **/
    private val handlerMap = mutableMapOf<String, Handler?>()
    /**
     * busSubThreadMap: storing BusSubThreads
     * @author 1552980358
     **/
    private val busSubThreadMap = mutableMapOf<String, BusSubThread?>()
    /**
     * busSubThreadMap: storing threads
     * @author 1552980358
     **/
    private val threadMap = mutableMapOf<String, Thread?>()
    private val threadMessageListenerList = mutableListOf<ThreadBusInterface>()
    private val listenerRemoveList = mutableListOf<Int>()
    
    /**
     * initialize
     * @author 1552980358
     **/
    init {
        // UI Thread
        try {
            handlerMap[CREATE] = Handler(Looper.getMainLooper())
        } catch (e: Exception) {
            throw IllegalStateInitializeException()
        }
    }
    
    /**
     * runRunnableAction()
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param action: interface containing actions
     * @return ThreadBus
     * @deprecated v0.6
     * @see runRunnableAction
     * `Char` can be changed into `String` by user
     * @hide
     **/
    @Deprecated("`Char` can be changed into `String` by user", ReplaceWith("runRunnableAction"), DeprecationLevel.HIDDEN)
    @Synchronized
    fun runRunnableAction(handlerName: Char, action: HandlerBusInterface): ThreadBus =
        runRunnableAction(handlerName.toString(), action)
    
    /**
     * runRunnableAction()
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param action: interface containing actions
     * @return ThreadBus
     **/
    @Synchronized
    fun runRunnableAction(handlerName: String, action: HandlerBusInterface, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]?.postDelayed(action.getRunnable, delayMillis)
        return this
    }
    
    /**
     * registerHandler()
     * @author 1552980358
     * @description register a handler
     * @param handlerName: name of the handler register
     * @param handler: handler object
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
    @Synchronized
    fun registerHandler(handlerName: String, handler: Handler?): ThreadBus {
        handlerMap[handlerName] = handler
        return this
    }
    
    /**
     * registerHandler()
     * @author 1552980358
     * @since v0.5
     * @description register a handler
     * @param handlerName: name of the handler register
     * @param looper: used to create Handler() object
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
    @Synchronized
    fun registerHandler(handlerName: String, looper: Looper): ThreadBus {
        handlerMap[handlerName] = Handler(looper)
        return this
    }
    
    /**
     * registerHandler(): register a handler for current thread
     * @author 1552980358
     * @since v0.5
     * @param handlerName: name of the handler register
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
    @Synchronized
    fun registerHandler(handlerName: String): ThreadBus {
        handlerMap[handlerName] = Handler(Looper.myLooper()!!)
        return this
    }
    
    /**
     * unregisterHandler()
     * @author 1552980358
     * @param handlerName: name of the handler unregister
     * @return ThreadBus
     **/
    @Synchronized
    fun unregisterHandler(handlerName: String): ThreadBus {
        handlerMap[handlerName] = null
        return this
    }
    
    /**
     * unregisterHandler()
     * @author 1552980358
     * @since v0.5
     * @description do not hope for callback of contents
     *
     * @param handlerName: name of the handler
     * @param runnable: runnable to be posted
     * @param delayMillis: millis to be delayed
     * @return ThreadBus
     **/
    @Synchronized
    fun postHandler(handlerName: String, runnable: Runnable, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]!!.postDelayed(runnable, delayMillis)
        return this
    }
    
    /**
     * getLooper()
     * @since v0.5
     * @author 1552980358
     * @param handlerName: name of the handler
     * @return Looper?
     **/
    fun getLooper(handlerName: String): Looper? {
        return handlerMap[handlerName]?.looper
    }
    
    /**
     * registerBusSubThread()
     * @author 1552980358
     * @param threadName: name of the handler register
     * @return ThreadBus
     **/
    @Synchronized
    fun registerBusSubThread(threadName: String): ThreadBus {
        busSubThreadMap[threadName] = BusSubThread().startThread()
        return this
    }
    
    /**
     * unregisterBusSubThread()
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @return ThreadBus
     **/
    @Synchronized
    fun unregisterBusSubThread(threadName: String): ThreadBus {
        busSubThreadMap.remove(threadName)?.interrupt()
        return this
    }
    
    /**
     * registerThread()
     * @author 1552980358
     * @since v0.5
     * @param threadName: name of the thread register
     * @param thread: thread to be added
     * @return ThreadBus
     **/
    @Synchronized
    fun registerThread(threadName: String, thread: Thread): ThreadBus {
        threadMap[threadName] = thread
        return this
    }
    
    /**
     * unregisterThread()
     * @author 1552980358
     * @since v0.5
     * @param threadName: name of the thread unregister
     * @return ThreadBus
     **/
    @Synchronized
    fun unregisterThread(threadName: String): ThreadBus {
        threadMap.remove(threadName)
        return this
    }
    
    /**
     * Followings are open method to the public.
     * However, following calling of internal
     * methods are opened for internal only.
     **/
    
    /* ================ THREAD ===================== */
    
    /**
     * runActionOnBusSubThread()
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @param action: task to be done
     * @param priority: priority of task
     * @return ThreadBus
     **/
    @Synchronized
    fun runActionOnBusSubThread(threadName: String, action: ThreadBusInterface, priority: Priority): ThreadBus {
        try {
            if (priority == Priority.NEW_THREAD) {
                busSubThreadMap[threadName] = BusSubThread().startThread(action, busSubThreadMap).setThreadName(threadName)
                return this
            }
            busSubThreadMap[threadName]?.setBusInterface(action, priority)?.setThreadName(threadName)
            return this
        } catch (e: Exception) {
            return this
        }
    }
    
    /**
     * stopCurrentExecution()
     * @author 1552980358
     * @since v0.7
     * @param threadName
     * @param timeGap
     * @return ThreadBus
     **/
    @Synchronized
    fun updateTimeGap(threadName: String, timeGap: Int): ThreadBus {
        busSubThreadMap[threadName]?.updateTimeGap(timeGap)
        return this
    }
    @Synchronized
    fun updateTimeGap(threadName: String, timeGap: Long): ThreadBus {
        busSubThreadMap[threadName]?.updateTimeGap(timeGap)
        return this
    }
    
    /**
     * stopCurrentExecution()
     * @author 1552980358
     * @since v0.7
     * @param threadName
     * @return ThreadBus
     **/
    @Synchronized
    fun stopCurrentExecution(threadName: String): ThreadBus {
        busSubThreadMap[threadName]?.stopCurrentExecution()
        return this
    }
    
    /**
     * sendMessage()
     * @author 1552980358
     * @since v0.8
     * @param threadName
     * @param message
     * @return ThreadBus
     **/
    @Synchronized
    fun sendMessage(threadName: String, message: ThreadMessage): ThreadBus {
        busSubThreadMap[threadName]?.receiveMessage(message)
        return this
    }
    
}