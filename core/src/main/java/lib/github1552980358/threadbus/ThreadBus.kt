package lib.github1552980358.threadbus

import android.os.Handler
import lib.github1552980358.threadbus.interfaces.HandlerBusInterface

/**
 * @File    : ThreadBus
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:57
 **/

class ThreadBus private constructor() {
    companion object {
        /**
         * @author 1552980358
         **/
        val threadBus by lazy { ThreadBus() }
        
        const val CREATE = "CREATE"
    }
    
    private val handlerMap = mutableMapOf<String, Handler?>()
    
    /**
     * initialize
     * @author 1552980358
     **/
    init {
        // UI Thread
        handlerMap[CREATE] = Handler()
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
    
}