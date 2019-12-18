package example.github1552980358.threadbus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lib.github1552980358.threadbus.ThreadBus
import lib.github1552980358.threadbus.util.threadBus

/**
 * @File    : MainActivity
 * @Author  : 1552980358
 * @Date    : 2019/12/18
 * @TIME    : 8:10
 **/

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)
    
    
        threadBus().postHandler(ThreadBus.CREATE, Runnable {
            // ...
        })
        
        threadBus().postHandler(ThreadBus.CREATE, Runnable {
            // ...
        }, 0)
        
        
    }
}