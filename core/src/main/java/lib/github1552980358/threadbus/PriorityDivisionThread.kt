package lib.github1552980358.threadbus

import lib.github1552980358.threadbus.interfaces.ThreadTask
import lib.github1552980358.threadbus.util.Priority
import lib.github1552980358.threadbus.util.ThreadMessage
import java.io.Serializable

/**
 * @File    : PriorityDivisionThread
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:01
 **/

@Suppress("unused")
internal class PriorityDivisionThread : Thread(), Serializable {
    
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
    private var threadTask: ThreadTask? = null
    
    /**
     * arrays storing tasks with reference to priority
     **/
    private val maxArray = arrayListOf<ThreadTask?>()
    private val midArray = arrayListOf<ThreadTask?>()
    private val minArray = arrayListOf<ThreadTask?>()
    
    /**
     * timeGap
     * @author 1552980358
     **/
    private var timeGap = 1000L
    
    private lateinit var threadName: String
    private var threadMap: MutableMap<String, PriorityDivisionThread?>? = null
    
    private var removeOnDone = false
    
    /**
     * setTask
     * @author 1552980358
     * @param task add a interface to be executed
     * @param priority consider force
     * @return void
     *
     * @warning INTERNAL
     *
     **/
    @Synchronized
    internal fun setTask(task: ThreadTask?, priority: Priority): PriorityDivisionThread {
        if (threadInterrupt) {
            throw BusSubThreadException("Thread has been interrupted")
        }
        when (priority) {
            Priority.MAX -> {
                maxArray.add(task)
            }
            Priority.MID -> {
                midArray.add(task)
            }
            Priority.MIN -> {
                minArray.add(task)
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
     * @return PriorityDivisionThread
     *
     * @warning INTERNAL
     *
     **/
    internal fun setThreadName(name: String): PriorityDivisionThread {
        threadName = name
        return this
    }
    
    /**
     * receiveMessage()
     * @author 1552980358
     * @since v0.8
     * @param message: message received
     * @return PriorityDivisionThread
     *
     * @warning INTERNAL
     *
     **/
    internal fun receiveMessage(message: ThreadMessage): PriorityDivisionThread {
        threadTask?.receiveMessage(message)
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
                threadTask?.executeTask()
            } catch (e: Exception) {
                threadTask?.onExceptionOccurs(e)
                threadMap!!.remove(threadName)
                threadMap = null
                System.gc()
                return
            }
            threadTask?.onTaskComplete()
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
                    threadTask = maxArray.removeAt(0)
                }
                midArray.isNotEmpty() -> {
                    threadTask = midArray.removeAt(0)
                }
                minArray.isNotEmpty() -> {
                    threadTask = minArray.removeAt(0)
                }
            }
            
            // nothing done, move to next loop
            threadTask ?: continue
            interfaceExecuting = true
            
            try {
                if (!interfaceExecuting) {
                    continue
                }
                threadTask?.executeTask()
            } catch (e: Exception) {
                if (!interfaceExecuting) {
                    continue
                }
                threadTask?.errorThrown = true
                threadTask?.handler?.post { threadTask?.onExceptionOccurs(e) }
            }
            try {
                if (!interfaceExecuting) {
                    continue
                }
                
                if (threadTask == null || threadTask?.errorThrown == null || !threadTask!!.errorThrown) {
                    threadTask?.handler?.post { threadTask?.onTaskComplete() }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            // remove interface when no changed during execution
            threadTask = null
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
     * @return PriorityDivisionThread
     **/
    @Synchronized
    internal fun startThread(): PriorityDivisionThread {
        super.start()
        return this
    }
    
    /**
     * startThread()
     * @author 1552980358
     * @param task task to be done
     * @param map map storing threads
     * @return PriorityDivisionThread
     **/
    @Synchronized
    internal fun startThread(task: ThreadTask, map: MutableMap<String, PriorityDivisionThread?>): PriorityDivisionThread {
        removeOnDone = true
        threadMap = map
        threadTask = task
        return startThread()
    }
    
}