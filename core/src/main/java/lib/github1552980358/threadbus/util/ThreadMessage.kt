package lib.github1552980358.threadbus.util

import lib.github1552980358.threadbus.interfaces.MessageTask
import java.io.Serializable

/**
 * @File    : ThreadMessage
 * @Author  : 1552980358
 * @Date    : 2019/12/19
 * @TIME    : 11:04
 **/

@Suppress("unused")
open class ThreadMessage(val messageName: String): Serializable {
    
    /**
     * messages and actions
     * @author 1552980358
     * @since v0.7
     **/
    private var msgMap: MutableMap<String, Any?> = mutableMapOf()
    private var taskArray: ArrayList<MessageTask>? = ArrayList()
    
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
     * insertTask()
     * @author 1552980358
     * @since v0.7
     * @description insert a msgAction into front of specific index of array
     *
     * @param task
     * @return ThreadMessage
     **/
    fun insertTask(index: Int, task: MessageTask): ThreadMessage {
        taskArray!!.add(index, task)
        return this
    }
    
    /**
     * setTask()
     * @author 1552980358
     * @since v0.7
     * @description set a msgAction into specific index of array
     *
     * @param task
     * @return ThreadMessage
     **/
    fun setTask(index: Int, task: MessageTask): ThreadMessage {
        taskArray!![index] = task
        return this
    }
    
    /**
     * addTask()
     * @author 1552980358
     * @since v0.7
     * @description add a msgAction into end of array
     *
     * @param task
     * @return ThreadMessage
     **/
    fun addTask(task: MessageTask): ThreadMessage {
        taskArray!!.add(task)
        return this
    }
    
}