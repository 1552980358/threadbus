package lib.github1552980358.threadbus

import android.os.Handler
import android.os.Looper
import lib.github1552980358.threadbus.interfaces.HandlerBusInterface
import lib.github1552980358.threadbus.interfaces.ThreadBusInterface
import lib.github1552980358.threadbus.util.Priority

/**
 * @File    : ThreadBus
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:57
 **/

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class ThreadBus private constructor() {
    
    companion object {
        /**
         * @author 1552980358
         **/
        val threadBus by lazy { ThreadBus() }
        
        const val CREATE = "CREATE"
    }
    
    /**
     * handlerMap: storing handlers
     **/
    private val handlerMap = mutableMapOf<String, Handler?>()
    /**
     * threadMap: storing threads
     **/
    private val threadMap = mutableMapOf<String, BusSubThread>()
    
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
     * addRunnableAction
     * @author 1552980358
     * @param handlerName name of the handler registered
     * @param action interface containing actions
     * @return ThreadBus
     **/
    fun runRunnableAction(handlerName: Char, action: HandlerBusInterface): ThreadBus =
        runRunnableAction(handlerName.toString(), action)
    
    /**
     * addRunnableAction
     * @author 1552980358
     * @param handlerName name of the handler registered
     * @param action interface containing actions
     * @return ThreadBus
     **/
    fun runRunnableAction(handlerName: String, action: HandlerBusInterface): ThreadBus {
        handlerMap[handlerName]?.post(action.getRunnable)
        return this
    }
    
    /**
     * registerHandler
     * @author 1552980358
     * @param handlerName name of the handler register
     * @param handler handler object
     * @return ThreadBus
     **/
    fun registerHandler(handlerName: String, handler: Handler?): ThreadBus {
        handlerMap[handlerName] = handler
        return this
    }
    
    /**
     * unregisterHandler
     * @author 1552980358
     * @param handlerName name of the handler unregister
     * @return ThreadBus
     **/
    fun unregisterHandler(handlerName: String): ThreadBus {
        handlerMap[handlerName] = null
        return this
    }
    
    /**
     * unregisterHandler
     * @author 1552980358
     * @param threadName name of the handler register
     * @return ThreadBus
     **/
    fun registerThread(threadName: String): ThreadBus {
        threadMap[threadName] = BusSubThread().startThread()
        return this
    }
    
    /**
     * unregisterHandler
     * @author 1552980358
     * @param threadName name of the thread unregister
     * @return ThreadBus
     **/
    fun unregisterThread(threadName: String): ThreadBus {
        threadMap.remove(threadName)?.interrupt()
        return this
    }
    
    /**
     * unregisterHandler
     * @author 1552980358
     * @param threadName name of the thread unregister
     * @param action task to be done
     * @param priority priority of task
     * @return ThreadBus
     **/
    fun runActionOnThread(threadName: String, action: ThreadBusInterface, priority: Priority): ThreadBus {
        if (priority == Priority.NEW_THREAD) {
            threadMap[threadName] = BusSubThread().startThread(threadName, action, threadMap)
            return this
        }
    
        threadMap[threadName]?.setBusInterface(action, priority)
        return this
    }
    
}