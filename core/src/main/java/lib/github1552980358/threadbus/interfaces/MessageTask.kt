package lib.github1552980358.threadbus.interfaces

import android.os.Handler
import java.io.Serializable

/**
 * @File    : MessageTask
 * @Author  : 1552980358
 * @Date    : 2019/12/19
 * @TIME    : 11:17
 **/

abstract class MessageTask : BaseActionInterface, Serializable {
    
    /**
     * hasCallback
     * @author 1552980358
     * @since v0.9
     **/
    private var hasCallback = false
    
    /**
     * constructors
     * @author 1552980358
     * @since v0.9
     **/
    constructor(hasCallBack: Boolean = false): super() {
        this.hasCallback = hasCallBack
    }
    constructor(hasCallBack: Boolean = false, handler: Handler): super(handler) {
        this.hasCallback = hasCallBack
    }
    
    /**
     * @fun [execute]
     * @author 1552980358
     * @date 2020/2/3
     * @time 18:12
     * @description
     * @return void
     **/
    fun execute() {
        try {
            executeTask(resultMap)
        } catch (e: Exception) {
            if (hasCallback) {
                handler?.post { onExceptionOccurs(e, resultMap) }
            }
            return
        }
        if (hasCallback) {
            handler?.post { onTaskComplete(resultMap) }
        }
    }
    
    /**
     * @fun [updateHandler]
     * @author 1552980358
     * @since v0.9
     * @param handler: should init interface handler when creation
     * @return MessageTask
     **/
    override fun updateHandler(handler: Handler): MessageTask {
        super.updateHandler(handler)
        return this
    }
    
}
