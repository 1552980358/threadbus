@file:Suppress("unused")

package lib.github1552980358.threadbus.util

import lib.github1552980358.threadbus.ThreadBus

/**
 * @File    : ObjectEntry
 * @Author  : 1552980358
 * @Date    : 2019/12/17
 * @TIME    : 18:13
 * @since     : v0.5
 **/

/* For java */
@Deprecated("Seems Useless, use original method calling", ReplaceWith(""), DeprecationLevel.ERROR)
class ObjectEntry {
    companion object {
        /**
         * Call ThreadBus in Any object
         * @author 1552980358
         *
         * @since v0.5
         **/
        fun threadBus() = ThreadBus.threadBus
        /**
         * Setup a looper for called class
         * @author 1552980358
         * @since v0.5
         **/
        fun setUpLooper() = ThreadBus.setUpLooper()
    }
}

/* For Kotlin */
/**
 * Call ThreadBus in Any object
 * @author 1552980358
 * @since v0.5
 **/
fun Any.threadBus() = ThreadBus.threadBus

/**
 * Setup a looper for called class
 * @author 1552980358
 * @since v0.5
 **/
fun Any.setUpLooper() = ThreadBus.setUpLooper()