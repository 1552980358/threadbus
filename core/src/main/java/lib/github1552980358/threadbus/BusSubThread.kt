package lib.github1552980358.threadbus

import lib.github1552980358.threadbus.interfaces.ThreadBusInterface
import lib.github1552980358.threadbus.util.Priority
import lib.github1552980358.threadbus.util.ThreadMessage
import java.io.Serializable

/**
 * @File    : BusSubThread
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:01
 **/

@Suppress("unused")
internal class BusSubThread : Thread(), Serializable {
    
    /**
     * Interruption thread flag
     * @author 1552980358
     **/
    private var threadInterrupt = false
    
    /**
     * Interruption task flag
     * @author 1552980358
     **/
    private var interfaceExecuting = false
    
    /**
     * Template variable
     * @author 1552980358
     **/
    private var threadBusInterface: ThreadBusInterface? = null
    
    /**
     * arrays storing tasks with reference to priority
     **/
    private val maxArray = arrayListOf<ThreadBusInterface?>()
    private val midArray = arrayListOf<ThreadBusInterface?>()
    private val minArray = arrayListOf<ThreadBusInterface?>()
    
    /**
     * timeGap
     * @author 1552980358
     **/
    private var timeGap = 1000L
    
    private lateinit var threadName: String
    private var threadMap: MutableMap<String, BusSubThread?>? = null
    
    private var removeOnDone = false
    
    /**
     * setBusInterface
     * @author 1552980358
     * @param busInterface add a interface to be executed
     * @param priority consider force
     * @return void
     *
     * @warning INTERNAL
     *
     **/
    @Synchronized
    internal fun setBusInterface(busInterface: ThreadBusInterface?, priority: Priority): BusSubThread {
        if (threadInterrupt) {
            throw BusSubThreadException("Thread has been interrupted")
        }
        when (priority) {
            Priority.MAX -> {
                maxArray.add(busInterface)
            }
            Priority.MID -> {
                midArray.add(busInterface)
            }
            Priority.MIN -> {
                minArray.add(busInterface)
            }
            else -> {
                throw BusSubThreadException("Illegal Priority")
            }
        }
        return this
    }
    
    /**
     * setThreadName()
     * @author 1552980358
     * @since v0.8
     * @param name: name of thread
     * @return BusSubThread
     *
     * @warning INTERNAL
     *
     **/
    internal fun setThreadName(name: String): BusSubThread {
        threadName = name
        return this
    }
    
    /**
     * receiveMessage()
     * @author 1552980358
     * @since v0.8
     * @param message: message received
     * @return BusSubThread
     *
     * @warning INTERNAL
     *
     **/
    internal fun receiveMessage(message: ThreadMessage): BusSubThread {
        threadBusInterface?.receiveMessage(message)
        return this
    }
    
    /**
     * run: rewrite super function
     * @author 1552980358
     * @return void
     **/
    override fun run() {
        super.run()
        
        if (!removeOnDone) {
            try {
                threadBusInterface?.doAction()
            } catch (e: Exception) {
                threadBusInterface?.onException(e)
                threadMap!!.remove(threadName)
                threadMap = null
                System.gc()
                return
            }
            threadBusInterface?.onActionDone()
            threadMap!!.remove(threadName)
            threadMap = null
            System.gc()
            return
        }
        
        // Keep active
        do {
            // check periodically
            if (maxArray.isEmpty() && midArray.isEmpty() && minArray.isEmpty()) {
                try {
                    // time gap
                    sleep(timeGap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                continue
            }
            
            // priority assigning task
            when {
                maxArray.isNotEmpty() -> {
                    threadBusInterface = maxArray.removeAt(0)
                }
                midArray.isNotEmpty() -> {
                    threadBusInterface = midArray.removeAt(0)
                }
                minArray.isNotEmpty() -> {
                    threadBusInterface = minArray.removeAt(0)
                }
            }
            
            // nothing done, move to next loop
            threadBusInterface ?: continue
            interfaceExecuting = true
            
            try {
                if (!interfaceExecuting) {
                    continue
                }
                threadBusInterface?.doAction()
            } catch (e: Exception) {
                if (!interfaceExecuting) {
                    continue
                }
                threadBusInterface?.errorThrown = true
                threadBusInterface?.handler?.post { threadBusInterface?.onException(e) }
            }
            try {
                if (!interfaceExecuting) {
                    continue
                }
                
                if (threadBusInterface == null || threadBusInterface?.errorThrown == null || !threadBusInterface!!.errorThrown) {
                    threadBusInterface?.handler?.post { threadBusInterface?.onActionDone() }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            // remove interface when no changed during execution
            threadBusInterface = null
            interfaceExecuting = false
        } while (!threadInterrupt)
    }
    
    /**
     * updateTimeGap()
     * @author 1552980358
     * @param gap
     * @throws BusSubThreadException
     *
     * @warning INTERNAL
     **/
    internal fun updateTimeGap(gap: Int) = updateTimeGap(gap.toLong())
    internal fun updateTimeGap(gap: Long) {
        if (gap <= 0) {
            throw BusSubThreadException("Time gap should be GREATER than 0")
        }
        timeGap = gap
    }
    
    /**
     * stopCurrentExecution()
     * @author 1552980358
     * @return void
     **/
    internal fun stopCurrentExecution() {
        interfaceExecuting = false
    }
    
    /**
     * interrupt()
     * @author 1552980358
     * @return void
     **/
    override fun interrupt() {
        threadInterrupt = true
        super.interrupt()
    }
    
    /**
     * startThread()
     * @author 1552980358
     * @return BusSubThread
     **/
    @Synchronized
    internal fun startThread(): BusSubThread {
        super.start()
        return this
    }
    
    /**
     * startThread()
     * @author 1552980358
     * @param busInterface task to be done
     * @param map map storing threads
     * @return BusSubThread
     **/
    @Synchronized
    internal fun startThread(busInterface: ThreadBusInterface, map: MutableMap<String, BusSubThread?>): BusSubThread {
        removeOnDone = true
        threadMap = map
        threadBusInterface = busInterface
        return startThread()
    }
    
}