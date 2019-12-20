package lib.github1552980358.threadbus.interfaces

/**
 * @File    : MessageInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/19
 * @TIME    : 11:17
 **/

abstract class MessageInterface(val actionName: String, private val hasCallback: Boolean = true): BaseActionInterface() {
    
    private var resultObtained = false
    
    val runnable by lazy {
        Runnable {
            try {
                doAction()
            } catch (e: Exception) {
                if (hasCallback) {
                    onException(e)
                }
                return@Runnable
            }
            if (hasCallback) {
                onActionDone()
            }
        }
    }
}