package lib.github1552980358.threadbus.interfaces

import lib.github1552980358.threadbus.util.ThreadMessage
import java.io.Serializable

/**
 * @File    : ThreadTask
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:00
 **/

@Suppress("unused")
abstract class ThreadTask: BaseActionInterface(), Serializable {
    
    private var obtainedMessage: ThreadMessage? = null
    
    /**
     * errorThrown
     * @author 1552980358
     * @since v0.7
     * @description flag indicating whether error thrown
     **/
    var errorThrown = false
    /**
     * resultObtained
     * @author 1552980358
     * @since v0.7
     * @description flag indicating whether result obtained
     **/
    private var resultObtained = true
    
    /**
     * receiveMessage()
     * @author 1552980358
     * @since v0.8
     * @param threadMessage?
     * @return void
     *
     * @warning INTERNAL
     *
     * @hide
     **/
    internal fun receiveMessage(threadMessage: ThreadMessage?) {
        obtainedMessage = threadMessage
        resultObtained = true
        onReceiveMessage(threadMessage)
    }
    
    /**
     * onReceiveMessage()
     * @author 1552980358
     * @since v0.8
     * @description called by internal obtainMessage()
     * @param threadMessage?
     * @return void
     **/
    open fun onReceiveMessage(threadMessage: ThreadMessage?) {
    }
    
    /**
     * waitForMessage()
     * @author 1552980358
     * @since v0.7
     * @description thread sleeper for waiting for result
     * @return void
     **/
    fun waitForMessage(): ThreadMessage? {
        // Init variable and flag
        resultObtained = false
        obtainedMessage = null
        
        while (obtainedMessage == null && !resultObtained) {
            try {
                // Time gap
                Thread.sleep(500)
            } catch (e: Exception) {
                //e.printStackTrace()
            }
        }
        
        return obtainedMessage
    }
    
    /**
     * stopWaiting()
     * @author 1552980358
     * @since v0.7
     * @return void
     **/
    fun stopWaiting() {
        resultObtained = true
    }
    
    /**
     * releaseMessage()
     * @author 1552980358
     * @since v0.14
     * @description remove message obtained
     * @return void
     **/
    fun releaseMessage() {
        obtainedMessage = null
    }
    
    /**
     * getObtainedMessage()
     * @author 1552980358
     * @since v0.14
     * @description remove message obtained
     * @return ThreadMessage
     **/
    fun getObtainedMessage(): ThreadMessage? {
        return obtainedMessage
    }
    
}