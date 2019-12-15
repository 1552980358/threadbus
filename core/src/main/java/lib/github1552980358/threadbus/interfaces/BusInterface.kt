package lib.github1552980358.threadbus.interfaces

import android.os.Handler
import lib.github1552980358.threadbus.BusInstanceException
import lib.github1552980358.threadbus.util.Result

/**
 * @File    : BusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:59
 **/

@Suppress("unused")
abstract class BusInterface {
    var handler: Handler? = null
    
    /**
     * storing results in the thread
     * @author 1552980358
     **/
    private var resultsArray: ArrayList<Any?>? = arrayListOf()
    
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
     * constructor: create a handler with initial thread
     * @author 1552980358
     **/
    constructor(): super() {
        this.handler = Handler()
    }
    /**
     * constructor: set a handler with initial thread
     * @author 1552980358
     * @param handler
     **/
    constructor(handler: Handler): super() {
        this.handler = handler
    }
    
    /**
     * initWithHandler
     * @author 1552980358
     * @param handler should init interface handler when creation
     * @return BusInterface
     **/
    fun updateHandler(handler: Handler): BusInterface {
        this.handler = handler
        return this
    }
    
    /**
     * setResults
     * @author 1552980358
     * @param results results array
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    fun setResults(vararg results: Result): BusInterface {
        return try {
           results.forEach {
               resultsArray!![it.index] = it.result
           }
           this
       } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * setResult
     * @author 1552980358
     * @param index index to be inserted
     * @param result result object
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    fun setResult(index: Int, result: Any?): BusInterface {
        return try {
            resultsArray!![index] = result
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * addResult
     * @author 1552980358
     * @param result result object added to map
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    @Synchronized
    fun addResult(result: Any?): BusInterface {
        return try {
            resultsArray!!.add(result)
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * getResult
     * @author 1552980358
     * @param T type of returning object
     * @param index index of the object
     * @return T
     * @throws BusInstanceException
     **/
    fun <T> getResult(index: Int): T {
        @Suppress("UNCHECKED_CAST")
        return try {
            resultsArray!![index] as T
        } catch (e:Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * getResults
     * @author 1552980358
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    fun getResults(): ArrayList<Any?> {
        return resultsArray ?: throw BusInstanceException("Request for result data actions after release")
    }
    
    /**
     * releaseAll
     * @author 1552980358
     * @param cleaning whether clean garbage
     * @return void
     **/
    @Synchronized
    fun releaseAll(cleaning: Boolean = true) {
        handler = null
        resultsArray = null
        
        // call for cleaning
        if (cleaning) {
            System.gc()
        }
    }
}