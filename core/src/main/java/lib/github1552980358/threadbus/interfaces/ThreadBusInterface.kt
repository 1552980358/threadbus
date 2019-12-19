package lib.github1552980358.threadbus.interfaces

import java.io.Serializable

/**
 * @File    : ThreadBusInterface
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 9:00
 **/

abstract class ThreadBusInterface: BusInterface(), Serializable {
    var errorThrown = false
}