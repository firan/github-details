package com.example.applausegithubapp.usecase

import java.util.concurrent.Executor

/**
 * author: Artur Godlewski
 */
class SynchronousExecutor : Executor {

    override fun execute(command: Runnable?) {
        command?.run()
    }
}