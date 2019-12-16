package lib.github1552980358.threadbus.util

/**
 * @File    : Result
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:19
 **/

/**
 * Result()
 * @author 1552980358
 *
 * @deprecated v0.4
 * @see ResultClass
 **/
@Deprecated("Use String as Identifier", ReplaceWith("ResultClass"), DeprecationLevel.WARNING)
open class Result(val index: Int, val result: Any?)

/**
 * ResultClass()
 * @author 1552980358
 * @added v0.4
 **/
open class ResultClass(val name: String, val result: Any?)