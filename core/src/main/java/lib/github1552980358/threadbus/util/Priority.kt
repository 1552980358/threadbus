package lib.github1552980358.threadbus.util

import java.io.Serializable

/**
 * @File    : Priority
 * @Author  : 1552980358
 * @Date    : 2019/12/15
 * @TIME    : 15:37
 **/

enum class Priority: Serializable {
    NEW_THREAD,
    MAX,
    MID,
    MIN
}