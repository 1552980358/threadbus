package lib.github1552980358.threadbus

import java.lang.IllegalStateException

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
open class BaseException(msg: String): IllegalStateException(msg)

/**
 * IllegalStateInitializeException
 * @author 1552980358
 **/
class IllegalStateInitializeException: BaseException("Initialize wrongly")

/**
 * BusInstanceException
 * @author 1552980358
 **/
class BusInstanceException(msg: String) : BaseException(msg)

/**
 * BusSubThreadException
 * @author 1552980358
 **/
class BusSubThreadException(msg: String) : BaseException(msg)