package com.example.myapplication.usecase.account

class WrongCredentialsException : Exception("Login failed, wrong e-mail/username or password")
