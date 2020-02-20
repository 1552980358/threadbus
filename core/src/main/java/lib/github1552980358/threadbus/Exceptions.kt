package lib.github1552980358.threadbus

import java.io.Serializable
import java.lang.IllegalStateException
import java.lang.RuntimeException

/**
 * @File    : Exceptions
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 16:59
 **/

/**
 * BaseException
 * @author 1552980358
 **/
open class BaseException(msg: String): IllegalStateException(msg), Serializable

/**
 * IllegalStateInitializeException
 * @author 1552980358
 **/
internal class IllegalStateInitializeException: BaseException("Initialize wrongly"), Serializable

/**
 * BusInstanceException
 * @author 1552980358
 **/
internal class BusInstanceException(msg: String) : BaseException(msg), Serializable

/**
 * BusSubThreadException
 * @author 1552980358
 **/
internal class BusSubThreadException(msg: String) : BaseException(msg), Serializable

/**
 * TaskCompleteFlagException
 * @author 1552980358
 * @since v0.17
 **/
internal class TaskCompleteFlagException: RuntimeException("RUNTIME COMPLETE")