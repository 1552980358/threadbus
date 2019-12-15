package lib.github1552980358.threadbus.interfaces

import android.os.Handler

/**
 * @File    : BusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:59
 **/

abstract class BusInterface {
    val handler = Handler()
    
    private val resultsArray = arrayListOf<Any?>()
    
    /**
     * doAction
     * @author 1552980358
     * @return void
     **/
    abstract fun doAction()
    
    /**
     * onException
     * @author 1552980358
     * @param e Exception output
     * @return void
     **/
    abstract fun onException(e: Exception)
    
    /**
     * onActionDone
     * @author 1552980358
     * @return void
     **/
    abstract fun onActionDone()
    
    /**
     * @author 1552980358
     * @param results results array
     * @return HandlerBusInterface
     **/
    fun setResults(vararg results: Result): BusInterface {
        results.forEach {
            resultsArray[it.index] = it.result
        }
        return this
    }
    
    /**
     * @author 1552980358
     * @param index index to be inserted
     * @param result result object
     * @return HandlerBusInterface
     **/
    fun setResult(index: Int, result: Any?): BusInterface {
        resultsArray[index] = result
        return this
    }
    
    /**
     * @author 1552980358
     * @param result result object added to map
     * @return HandlerBusInterface
     **/
    fun addResult(result: Any?): BusInterface {
        resultsArray.add(result)
        return this
    }
    
    /**
     * @author 1552980358
     * @param T type of returning object
     * @param index index of the object
     * @return T
     **/
    fun <T> getResult(index: Int): T {
        @Suppress("UNCHECKED_CAST")
        return resultsArray[index] as T
    }
    
    /**
     * @author 1552980358
     * @return HandlerBusInterface
     **/
    fun getResults(): ArrayList<Any?> {
        return resultsArray
    }
}