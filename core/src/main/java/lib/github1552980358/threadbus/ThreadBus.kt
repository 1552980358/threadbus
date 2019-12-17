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
    
        /**
         * Set up looper for called thread, should be set before
         * @author 1552980358
         * @add v0.5
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
     * registerHandler(): register a handler
     * @author 1552980358
     * @param handlerName: name of the handler register
     * @param handler: handler object
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
    fun registerHandler(handlerName: String, handler: Handler?): ThreadBus {
        handlerMap[handlerName] = handler
        return this
    }
    
    /**
     * registerHandler(): register a handler
     * @author 1552980358
     * @add v0.5
     * @param handlerName: name of the handler register
     * @param looper: used to create Handler() object
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
    fun registerHandler(handlerName: String, looper: Looper): ThreadBus {
        handlerMap[handlerName] = Handler(looper)
        return this
    }
    
    /**
     * registerHandler(): register a handler for current thread
     * @author 1552980358
     * @add v0.5
     * @param handlerName: name of the handler register
     * @return ThreadBus
     *
     * Should call
     * @see setUpLooper()
     * before registering
     **/
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
    fun unregisterHandler(handlerName: String): ThreadBus {
        handlerMap[handlerName] = null
        return this
    }
    
    /**
     * unregisterHandler()
     * @add v0.5
     * @author 1552980358
     * @param handlerName: name of the handler
     * @param runnable: runnable to be posted
     * @param delayMillis: millis to be delayed
     * @return ThreadBus
     **/
    fun postHandler(handlerName: String, runnable: Runnable, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]!!.postDelayed(runnable, delayMillis)
        return this
    }
    
    /**
     * getLooper()
     * @add v0.5
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
    fun unregisterBusSubThread(threadName: String): ThreadBus {
        busSubThreadMap.remove(threadName)?.interrupt()
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
        try {
            if (priority == Priority.NEW_THREAD) {
                busSubThreadMap[threadName] = BusSubThread().startThread(threadName, action, busSubThreadMap)
                return this
            }
    
            busSubThreadMap[threadName]?.setBusInterface(action, priority)
            return this
        } catch (e: Exception) {
            return this
        }
    }
    
    /**
     * registerThread()
     * @author 1552980358
     * @add v0.5
     * @param threadName: name of the thread register
     * @param thread: thread to be added
     * @return ThreadBus
     **/
    fun registerThread(threadName: String, thread: Thread): ThreadBus {
        threadMap[threadName] = thread
        return this
    }
    
    /**
     * unregisterThread()
     * @author 1552980358
     * @add v0.5
     * @param threadName: name of the thread unregister
     * @return ThreadBus
     **/
    fun unregisterThread(threadName: String): ThreadBus {
        threadMap.remove(threadName)
        return this
    }
}