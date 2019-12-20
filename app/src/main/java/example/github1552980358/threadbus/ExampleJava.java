package example.github1552980358.threadbus;

import android.os.Looper;
import android.util.Log;
import lib.github1552980358.threadbus.ThreadBus;
import lib.github1552980358.threadbus.interfaces.HandlerBusInterface;
import lib.github1552980358.threadbus.interfaces.ThreadBusInterface;
import lib.github1552980358.threadbus.util.Priority;
import org.jetbrains.annotations.NotNull;

public class ExampleJava {

    public ExampleJava() {
        initial();

        // Handler
        registerHandler();
        runRunnableAction();
        unregisterHandler();

        // Thread
        createNewThread();
        addTaskToThread();
        unregisterThread();
    }

    private void initial() {
        ThreadBus.Companion.getThreadBus();
    }

    private Looper looper;

    private void registerHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                looper = Looper.myLooper();
                Looper.loop();
            }
        }).start();
        // Confirm thread start success
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadBus.Companion.getThreadBus()
                .registerHandler("Handler1", looper);
    }

    private void unregisterHandler() {
        ThreadBus.Companion.getThreadBus().unregisterHandler("Handler1");
    }

    private void runRunnableAction() {
        ThreadBus.Companion.getThreadBus().runRunnableAction("Handler1", new HandlerBusInterface() {

            @Override
            public void onException(@NotNull Exception e) {
                Log.e("ThreadBusInterface", "onActionDone");
                e.printStackTrace();
            }

            @Override
            public void onActionDone() {
                Log.e("ThreadBusInterface", "onActionDone");
                Log.e("msg", (String) getResult("msg"));
                Log.e("666", (String) getResult("666"));
                // Result map, can be copied and saved
                getResultsMap();
                // Release result
                releaseAll(true);
            }

            @Override
            public void doAction() {
                // Actions
                Log.e("ThreadBusInterface", "doAction");
                // Results
                setResult("msg", "Hello World!");
                setResult("666", 666);
            }

        }, 0);

        ThreadBus.Companion.getThreadBus().postHandler("Handler2", () -> {
            Log.e("Runnable", "run");
        }, 0);
    }

    private void createNewThread() {
        ThreadBus.Companion.getThreadBus()
                .registerBusSubThread("ExampleThread1")
                .registerBusSubThread("ExampleThread2");
    }

    private void addTaskToThread() {
        ThreadBus.Companion.getThreadBus()
                .runActionOnBusSubThread("ExampleThread1", new ThreadBusInterface() {

                    @Override
                    public void onException(@NotNull Exception e) {
                        Log.e("ThreadBusInterface", "onActionDone");
                        e.printStackTrace();
                    }

                    @Override
                    public void onActionDone() {
                        Log.e("ThreadBusInterface", "onActionDone");
                        Log.e("msg", (String) getResult("msg"));
                        Log.e("666", (String) getResult("666"));
                        // Result map, can be copied and saved
                        getResultsMap();
                        // Release result
                        releaseAll(true);
                    }

                    @Override
                    public void doAction() {
                        // Actions
                        Log.e("ThreadBusInterface", "doAction");
                        // Results
                        setResult("msg", "Hello World!");
                        setResult("666", 666);
                    }

                }, Priority.MAX);
    }

    private void unregisterThread() {
        ThreadBus.Companion.getThreadBus().unregisterBusSubThread("ExampleThread2");
    }

}
