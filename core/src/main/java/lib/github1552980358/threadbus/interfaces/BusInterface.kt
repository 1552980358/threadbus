@file:Suppress("DEPRECATION")

package lib.github1552980358.threadbus.interfaces

import android.os.Handler
import android.os.Looper
import lib.github1552980358.threadbus.BusInstanceException
import lib.github1552980358.threadbus.util.Result
import lib.github1552980358.threadbus.util.ResultClass
import java.io.Serializable

/**
 * @File    : BusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 8:59
 **/

@Suppress("unused")
abstract class BusInterface: Serializable{
    var handler: Handler? = null
    
    /**
     * storing results in the thread
     * @author 1552980358
     *
     * @deprecated v0.4 Use String as Identifier
     * @see resultMap
     **/
    @Deprecated("Use String as Identifier", ReplaceWith("resultsMap"), DeprecationLevel.WARNING)
    private var resultsArray: ArrayList<Any?>? = arrayListOf()
    
    /**
     * storing results in the thread
     * @author 1552980358
     * @since v0.4
     **/
    private var resultMap: MutableMap<String, Any?>? = mutableMapOf()
    
    /**
     * doAction()
     * @author 1552980358
     * @return void
     **/
    abstract fun doAction()
    
    /**
     * onException()
     * @author 1552980358
     * @param e: Exception output
     * @return void
     **/
    abstract fun onException(e: Exception)
    
    /**
     * onActionDone()
     * @author 1552980358
     * @return void
     **/
    abstract fun onActionDone()
    
    /**
     * constructor()
     * @author 1552980358
     * @description create a handler with initial thread
     **/
    constructor(): super() {
        this.handler = Handler()
    }
    /**
     * constructor()
     * @author 1552980358
     * @description set a handler with initial thread
     * @param handler
     **/
    constructor(handler: Handler): super() {
        this.handler = handler
    }
    /**
     * constructor()
     * @author 1552980358
     * @since v0.5
     * @description set a handler with initial thread
     * @param looper
     **/
    constructor(looper: Looper): super() {
        this.handler = Handler(looper)
    }
    
    /**
     * initWithHandler()
     * @author 1552980358
     * @param handler: should init interface handler when creation
     * @return BusInterface
     **/
    fun updateHandler(handler: Handler): BusInterface {
        this.handler = handler
        return this
    }
    
    /**
     * setResults()
     * @author 1552980358
     * @param results: results array
     * @return HandlerBusInterface
     * @throws BusInstanceException
     *
     * @deprecated v0.4
     * Use String as Identifier
     * @see setResultClasses
     **/
    @Suppress("DEPRECATION")
    @Deprecated("Use String as Identifier", ReplaceWith("setResultClasses(vararg ResultClass)"), DeprecationLevel.WARNING)
    @Synchronized
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
     * setResultClasses()
     * @author 1552980358
     * @since v0.4
     * @param results: results array
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    @Synchronized
    fun setResultClasses(vararg results: ResultClass): BusInterface {
        return try {
            results.forEach {
                resultMap!![it.name] = it.result
            }
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * setResult()
     * @author 1552980358
     * @param index: index to be inserted
     * @param result: result object
     * @return HandlerBusInterface
     * @throws BusInstanceException
     *
     * @deprecated v0.4
     * Use String as Identifier
     * @see setResult
     **/
    @Suppress("DEPRECATION")
    @Deprecated("Use String as Identifier", ReplaceWith("setResult(String, Any?)"), DeprecationLevel.WARNING)
    @Synchronized
    fun setResult(index: Int, result: Any?): BusInterface {
        return try {
            resultsArray!![index] = result
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * addResult()
     * @author 1552980358
     * @param result: result object added to map
     * @return HandlerBusInterface
     * @throws BusInstanceException
     *
     * @deprecated v0.4
     * Use String as Identifier
     * @see BusInterface.setResult(String, Any?)
     **/
    @Suppress("DEPRECATION")
    @Deprecated("Use String as Identifier", ReplaceWith("setResult(String, Any?)"), DeprecationLevel.WARNING)
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
     * setResult()
     * @author 1552980358
     * @since v0.4
     * @param result: result object added to map
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    @Synchronized
    fun setResult(name: String, result: Any?): BusInterface {
        return try {
            resultMap!![name] = result
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * setResult()
     * @author 1552980358
     * @since v0.4
     * @param result: result object added to map
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    @Synchronized
    fun setResult(result: ResultClass): BusInterface {
        return try {
            resultMap!![result.name] = result.result
            this
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * getResult()
     * @author 1552980358
     * @param T: type of returning object
     * @param index: index of the object
     * @return T
     * @throws BusInstanceException
     *
     * @deprecated v0.4
     * Use String as Identifier
     * @see BusInterface.getResult(String)
     **/
    @Suppress("DEPRECATION")
    @Deprecated("Use String as Identifier", ReplaceWith("getResult(String)"), DeprecationLevel.WARNING)
    fun <T> getResult(index: Int): T {
        @Suppress("UNCHECKED_CAST")
        return try {
            resultsArray!![index] as T
        } catch (e:Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * getResult()
     * @author 1552980358
     * @since v0.4
     * @param name: result object added to map
     * @return HandlerBusInterface
     * @throws BusInstanceException
     **/
    @Suppress("MemberVisibilityCanBePrivate")
    fun<T> getResult(name: String): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            resultMap!![name] as T
        } catch (e: Exception) {
            throw BusInstanceException("Request for result data actions after release")
        }
    }
    
    /**
     * getResults()
     * @author 1552980358
     * @return ArrayList<Any?>
     * @throws BusInstanceException
     *
     * @deprecated v0.4
     * Use String as Identifier
     * @see getResultsMap()
     **/
    @Suppress("DEPRECATION")
    @Deprecated("Use String as Identifier", ReplaceWith("getResults()"), DeprecationLevel.WARNING)
    fun getResults(): ArrayList<Any?> {
        return resultsArray ?: throw BusInstanceException("Request for result data actions after release")
    }
    
    /**
     * getResults()
     * @author 1552980358
     * @since v0.4
     * @return MutableMap<String, Any?>
     * @throws BusInstanceException
     **/
    @Suppress("MemberVisibilityCanBePrivate")
    fun getResultsMap(): MutableMap<String, Any?> {
        return resultMap ?: throw BusInstanceException("Request for result data actions after release")
    }
    
    /**
     * releaseAll()
     * @author 1552980358
     * @param cleaning whether clean garbage
     * @return void
     **/
    @Synchronized
    fun releaseAll(cleaning: Boolean = true) {
        handler = null
        //resultsArray = null
        resultMap = null
        
        // call for cleaning
        if (cleaning) {
            System.gc()
        }
    }
}