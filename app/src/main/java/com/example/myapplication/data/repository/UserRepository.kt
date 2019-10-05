package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.UserDao
import com.example.myapplication.data.entity.User
import java.util.concurrent.Executor

interface UserRepository {
    fun registerUser(user: User)
    fun findUserByEmail(email: String): LiveData<User>
    fun findUserAccounts(): LiveData<List<User>>
}

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val diskIOExecutor: Executor
) : UserRepository {
    override fun registerUser(user: User) {
        diskIOExecutor.execute {
            userDao.insertAll(user)
        }
    }

    override fun findUserByEmail(email: String): LiveData<User> {
        return userDao.findByEmail(email)
    }

    override fun findUserAccounts(): LiveData<List<User>> {
        return userDao.getAll()
    }
}