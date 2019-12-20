package lib.github1552980358.threadbus.util

import lib.github1552980358.threadbus.interfaces.MessageInterface
import java.io.Serializable

/**
 * @File    : ThreadMessage
 * @Author  : 1552980358
 * @Date    : 2019/12/19
 * @TIME    : 11:04
 **/

@Suppress("unused")
open class ThreadMessage: Serializable {
    /**
     * message and actions
     * @author 1552980358
     * @since v0.7
     **/
    private var msgMap: MutableMap<String, Any?> = mutableMapOf()
    private var actionArray: ArrayList<MessageInterface>? = ArrayList()
    
    /**
     * addMessage()
     * @author 1552980358
     * @since v0.7
     * @description add a message
     * @return ThreadMessage
     **/
    @Synchronized
    fun setMessage(msgName: String, msg: Any?): ThreadMessage {
        msgMap[msgName] = msg
        return this
    }
    
    /**
     * getMessage()
     * @author 1552980358
     * @since v0.7
     * @description get a specific message
     * @return Any?
     **/
    @Synchronized
    fun<T> getMessage(msgName: String): T? {
        @Suppress("UNCHECKED_CAST")
        return msgMap[msgName] as T?
    }
    
    /**
     * removeMessage()
     * @author 1552980358
     * @since v0.7
     * @description remove a message
     * @return ThreadMessage
     **/
    @Synchronized
    fun removeMessage(msgName: String): ThreadMessage {
        msgMap.remove(msgName)
        return this
    }
    
    /**
     * getMsgSize()
     * @author 1552980358
     * @since v0.7
     * @description get size of MsgMap
     * @return Int
     **/
    fun getMsgSize(): Int {
        return msgMap.size
    }
    
    /**
     * addAction()
     * @author 1552980358
     * @since v0.7
     * @description remove all actions
     * @return ThreadMessage
     **/
    fun removeAllMsg(): ThreadMessage {
        msgMap.clear()
        return this
    }
    
    /**
     * addAction()
     * @author 1552980358
     * @since v0.7
     * @description insert a msgAction into front of specific index of array
     *
     * @param msgAction
     * @return ThreadMessage
     **/
    fun insertAction(index: Int, msgAction: MessageInterface): ThreadMessage {
        actionArray!!.add(index, msgAction)
        return this
    }
    
    /**
     * addAction()
     * @author 1552980358
     * @since v0.7
     * @description set a msgAction into specific index of array
     *
     * @param msgAction
     * @return ThreadMessage
     **/
    fun setAction(index: Int, msgAction: MessageInterface): ThreadMessage {
        actionArray!![index] = msgAction
        return this
    }
    
    /**
     * addAction()
     * @author 1552980358
     * @since v0.7
     * @description add a msgAction into end of array
     *
     * @param msgAction
     * @return ThreadMessage
     **/
    fun addAction(msgAction: MessageInterface): ThreadMessage {
        actionArray!!.add(msgAction)
        return this
    }
    
}