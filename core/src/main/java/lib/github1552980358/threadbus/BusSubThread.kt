package lib.github1552980358.threadbus

import lib.github1552980358.threadbus.interfaces.ThreadBusInterface

/**
 * @File    : BusSubThread
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:01
 **/

class BusSubThread: Thread() {
    private var threadInterrupt = false
    private var threadBusInterface: ThreadBusInterface? = null
    private var interfaceExecuting = false
    
    /**
     * setBusInterface
     * @param busInterface add a interface to be executed
     * @param forceSet consider force
     * @return void
     **/
    @Synchronized
    fun setBusInterface(busInterface: ThreadBusInterface?, forceSet: Boolean = false) {
        if (threadInterrupt) {
            throw IllegalThreadStateException()
        }
        if (!interfaceExecuting) {
            threadBusInterface = busInterface
            return
        }
        if (forceSet) {
            interfaceExecuting = false
            threadBusInterface = busInterface
        }
    }
    
    /**
     * run
     * @author 1552980358
     **/
    override fun run() {
        super.run()
        
        // Keep active
        while (!threadInterrupt) {
            try {
                sleep(1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            threadBusInterface?:continue
            interfaceExecuting = true
            
            try {
                if (interfaceExecuting) threadBusInterface?.doAction()
            } catch (e: Exception) {
                if (interfaceExecuting) {
                    threadBusInterface?.errorThrown = true
                    threadBusInterface?.handler?.post { threadBusInterface?.onException(e) }
                }
            }
            try {
                if (interfaceExecuting) {
                    if (threadBusInterface == null || threadBusInterface?.errorThrown == null || !threadBusInterface!!.errorThrown) {
                        threadBusInterface?.handler?.post { threadBusInterface?.onActionDone() }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            // remove interface when no changed during execution
            if (interfaceExecuting) {
                threadBusInterface = null
                interfaceExecuting = false
            }
        }
    }
    
    /**
     * interrupt
     * @author 1552980358
     **/
    override fun interrupt() {
        threadInterrupt = true
        super.interrupt()
    }
}