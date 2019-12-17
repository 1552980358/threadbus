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
     * @author 1552980358
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
     * runRunnableAction()
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param action: interface containing actions
     * @return ThreadBus
     **/
    fun runRunnableAction(handlerName: Char, action: HandlerBusInterface): ThreadBus =
        runRunnableAction(handlerName.toString(), action)
    
    /**
     * runRunnableAction()
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param action: interface containing actions
     * @return ThreadBus
     **/
    fun runRunnableAction(handlerName: String, action: HandlerBusInterface): ThreadBus {
        handlerMap[handlerName]?.post(action.getRunnable)
        return this
    }
    
    /**
     * registerHandler()
     * @author 1552980358
     * @param handlerName: name of the handler register
     * @param handler: handler object
     * @return ThreadBus
     **/
    fun registerHandler(handlerName: String, handler: Handler?): ThreadBus {
        handlerMap[handlerName] = handler
        return this
    }
    
    /**
     * registerHandler()
     * @author 1552980358
     * @param handlerName: name of the handler register
     * @param looper: used to create Handler() object
     * @return ThreadBus
     *
     * @added v0.5
     **/
    fun registerHandler(handlerName: String, looper: Looper): ThreadBus {
        handlerMap[handlerName] = Handler(looper)
        return this
    }
    
    /**
     * unregisterHandler()
     * @author 1552980358
     * @param handlerName: name of the handler unregister
     * @return ThreadBus
     **/
    fun unregisterHandler(handlerName: String): ThreadBus {
        handlerMap[handlerName] = null
        return this
    }
    
    /**
     * unregisterHandler()
     * @author 1552980358
     * @param handlerName: name of the handler
     * @param runnable: runnable to be posted
     * @param delayMillis: millis to be delayed
     * @return ThreadBus
     *
     * @added v0.5
     **/
    fun postHandler(handlerName: String, runnable: Runnable, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]!!.postDelayed(runnable, delayMillis)
        return this
    }
    
    /**
     * getLooper()
     * @author 1552980358
     * @param handlerName: name of the handler
     * @return Looper?
     *
     * @added v0.5
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
    fun registerBusSubThread(threadName: String): ThreadBus {
        threadMap[threadName] = BusSubThread().startThread()
        return this
    }
    
    /**
     * unregisterBusSubThread()
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @return ThreadBus
     **/
    fun unregisterBusSubThread(threadName: String): ThreadBus {
        threadMap.remove(threadName)?.interrupt()
        return this
    }
    
    /**
     * runActionOnBusSubThread()
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @param action: task to be done
     * @param priority: priority of task
     * @return ThreadBus
     **/
    fun runActionOnBusSubThread(threadName: String, action: ThreadBusInterface, priority: Priority): ThreadBus {
        if (priority == Priority.NEW_THREAD) {
            threadMap[threadName] = BusSubThread().startThread(threadName, action, threadMap)
            return this
        }
    
        threadMap[threadName]?.setBusInterface(action, priority)
        return this
    }
    
}