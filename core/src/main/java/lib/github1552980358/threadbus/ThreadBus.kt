package lib.github1552980358.threadbus

import android.os.Handler
import android.os.Looper
import lib.github1552980358.threadbus.interfaces.HandlerTask
import lib.github1552980358.threadbus.interfaces.ThreadTask
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
        @JvmStatic
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
     * busSubThreadMap: storing PriorityDivisionThread
     * @author 1552980358
     **/
    private val pdThreadMap = mutableMapOf<String, PriorityDivisionThread?>()
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
     * @fun [executeTask]
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param task: interface containing actions
     * @return ThreadBus
     * @deprecated v0.6
     * @see executeTask
     * `Char` can be changed into `String` by user
     * @hide
     **/
    @Deprecated("`Char` can be changed into `String` by user", ReplaceWith("runRunnableAction"), DeprecationLevel.HIDDEN)
    @Synchronized
    fun executeTask(handlerName: Char, task: HandlerTask): ThreadBus =
        executeTask(handlerName.toString(), task)
    
    /**
     * @fun [executeTask]
     * @author 1552980358
     * @param handlerName: name of the handler registered
     * @param action: interface containing actions
     * @return ThreadBus
     **/
    @JvmOverloads
    @Synchronized
    fun executeTask(handlerName: String, action: HandlerTask, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]?.postDelayed(action.getRunnable, delayMillis)
        return this
    }
    
    /**
     * @fun [registerHandler]
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
     * @fun [registerHandler]
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
        return registerHandler(handlerName, Handler(looper))
    }
    
    /**
     * @fun [registerHandler]: register a handler for current thread
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
        return registerHandler(handlerName, Looper.myLooper()!!)
    }
    
    /**
     * @fun [unregisterHandler]
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
     * @fun [unregisterHandler]
     * @author 1552980358
     * @since v0.5
     * @description do not hope for callback of contents
     *
     * @param handlerName: name of the handler
     * @param runnable: runnable to be posted
     * @param delayMillis: millis to be delayed
     * @return ThreadBus
     **/
    @JvmOverloads
    @Synchronized
    fun postHandler(handlerName: String, runnable: Runnable, delayMillis: Long = 0): ThreadBus {
        handlerMap[handlerName]!!.postDelayed(runnable, delayMillis)
        return this
    }
    
    /**
     * @fun [getLooper]
     * @since v0.5
     * @author 1552980358
     * @param handlerName: name of the handler
     * @return Looper?
     **/
    fun getLooper(handlerName: String): Looper? {
        return handlerMap[handlerName]?.looper
    }
    
    /**
     * @fun [registerThread]
     * @author 1552980358
     * @param threadName: name of the handler register
     * @return ThreadBus
     **/
    @Synchronized
    fun registerThread(threadName: String): ThreadBus {
        pdThreadMap[threadName] = PriorityDivisionThread().startThread()
        return this
    }
    
    /**
     * @fun [unregisterPriorityDivisionThread]
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @return ThreadBus
     **/
    @Synchronized
    fun unregisterPriorityDivisionThread(threadName: String): ThreadBus {
        pdThreadMap.remove(threadName)?.interrupt()
        return this
    }
    
    /**
     * @fun [registerThread]
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
     * @fun [unregisterThread]
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
     * @fun [executeTask]
     * @author 1552980358
     * @param threadName: name of the thread unregister
     * @param task: task to be done
     * @param priority: priority of task
     * @return ThreadBus
     **/
    @Synchronized
    fun executeTask(threadName: String, task: ThreadTask, priority: Priority): ThreadBus {
        try {
            if (priority == Priority.NEW_THREAD) {
                pdThreadMap[threadName] = PriorityDivisionThread().startThread(task, pdThreadMap).setThreadName(threadName)
                return this
            }
            pdThreadMap[threadName]?.setTask(task, priority)?.setThreadName(threadName)
            return this
        } catch (e: Exception) {
            return this
        }
    }
    
    /**
     * @fun [stopCurrentExecution]
     * @author 1552980358
     * @since v0.7
     * @param threadName
     * @param timeGap
     * @return ThreadBus
     **/
    @Synchronized
    fun updateTimeGap(threadName: String, timeGap: Int): ThreadBus {
        return updateTimeGap(threadName, timeGap.toLong())
    }
    @Synchronized
    fun updateTimeGap(threadName: String, timeGap: Long): ThreadBus {
        pdThreadMap[threadName]?.updateTimeGap(timeGap)
        return this
    }
    
    /**
     * @fun [stopCurrentExecution]
     * @author 1552980358
     * @since v0.7
     * @param threadName
     * @return ThreadBus
     **/
    @Synchronized
    fun stopCurrentExecution(threadName: String): ThreadBus {
        pdThreadMap[threadName]?.stopCurrentExecution()
        return this
    }
    
    /**
     * @fun [sendMessage]
     * @author 1552980358
     * @since v0.8
     * @param threadName
     * @param message
     * @return ThreadBus
     **/
    @Synchronized
    fun sendMessage(threadName: String, message: ThreadMessage): ThreadBus {
        pdThreadMap[threadName]?.receiveMessage(message)
        return this
    }
    
}