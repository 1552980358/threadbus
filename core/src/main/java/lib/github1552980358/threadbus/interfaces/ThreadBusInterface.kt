package lib.github1552980358.threadbus.interfaces

import java.io.Serializable

/**
 * @File    : ThreadBusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:00
 **/

@Suppress("unused")
abstract class ThreadBusInterface: BaseActionInterface(), Serializable {
    
    private var obtainedMessage: MessageInterface? = null
    
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
     * onMessageObtain()
     * @author 1552980358
     * @since v0.7
     * @param messageInterface?
     * @return void
     *
     * @warning INTERNAL
     *
     * @hide
     **/
    internal fun obtainMessage(messageInterface: MessageInterface?) {
        obtainedMessage = messageInterface
        resultObtained = true
        onMessageObtain(messageInterface)
    }
    
    /**
     * onMessageObtain()
     * @author 1552980358
     * @since v0.7
     * @description called by internal obtainMessage()
     * @param messageInterface?
     * @return void
     **/
    open fun onMessageObtain(messageInterface: MessageInterface?) {
    }
    
    /**
     * waitForMessage()
     * @author 1552980358
     * @since v0.7
     * @description thread sleeper for waiting for result
     * @return void
     **/
    fun waitForMessage(): MessageInterface? {
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
    
}