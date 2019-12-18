package example.github1552980358.threadBus

import lib.github1552980358.threadbus.interfaces.HandlerBusInterface
import lib.github1552980358.threadbus.interfaces.ThreadBusInterface
import lib.github1552980358.threadbus.util.Priority
import lib.github1552980358.threadbus.util.threadBus
import android.os.Looper
import android.util.Log

/**
 * @File    : ExampleKt
 * @Author  : 1552980358
 * @Date    : 2019/12/18
 * @TIME    : 20:44
 **/

class ExampleKt {
    
    init {
        initial()
        // Handler
        registerHandler()
        runRunnableAction()
        unregisterHandler()
        // Thread
        createNewThread()
        addTaskToThread()
        unregisterThread()
    }
    
    private fun initial() {
        threadBus()
    }
    
    private var looper: Looper? = null
    private fun registerHandler() {
        Thread(Runnable {
            Looper.prepare()
            looper = Looper.myLooper()
            Looper.loop()
        }).start()
        // Confirm thread start success
        try {
            Thread.sleep(10000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        threadBus()
            .registerHandler("Handler1", looper!!)
    }
    
    private fun unregisterHandler() {
        threadBus().unregisterHandler("Handler1")
    }
    
    private fun runRunnableAction() {
        threadBus().runRunnableAction(
            "Handler1",
            object : HandlerBusInterface() {
                override fun onException(e: Exception) {
                    Log.e("threadBus()Interface", "onActionDone")
                    e.printStackTrace()
                }
                
                override fun onActionDone() {
                    Log.e("threadBus()Interface", "onActionDone")
                    Log.e("msg", getResult<Any>("msg") as String)
                    Log.e("666", getResult<Any>("666") as String)
                    // Result map, can be copied and saved
                    getResultsMap()
                    // Release result
                    releaseAll(true)
                }
                
                override fun doAction() { // Actions
                    Log.e("threadBus()Interface", "doAction")
                    // Results
                    setResult("msg", "Hello World!")
                    setResult("666", 666)
                }
            })
    }
    
    private fun createNewThread() {
        threadBus()
            .registerBusSubThread("ExampleThread1")
            .registerBusSubThread("ExampleThread2")
    }
    
    private fun addTaskToThread() {
        threadBus()
            .runActionOnBusSubThread(
                "ExampleThread1",
                object : ThreadBusInterface() {
                    override fun onException(e: Exception) {
                        Log.e("threadBus()Interface", "onActionDone")
                        e.printStackTrace()
                    }
                    
                    override fun onActionDone() {
                        Log.e("threadBus()Interface", "onActionDone")
                        Log.e("msg", getResult<Any>("msg") as String)
                        Log.e("666", getResult<Any>("666") as String)
                        // Result map, can be copied and saved
                        getResultsMap()
                        // Release result
                        releaseAll(true)
                    }
                    
                    override fun doAction() { // Actions
                        Log.e("threadBus()Interface", "doAction")
                        // Results
                        setResult("msg", "Hello World!")
                        setResult("666", 666)
                    }
                },
                Priority.MAX
            )
    }
    
    private fun unregisterThread() {
        threadBus().unregisterBusSubThread("ExampleThread2")
    }
}
