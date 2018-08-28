package com.polri.sipp.app.ui


interface BaseView<T> {

    fun setPresenter(presenter: T)

    fun showError(message: String)
}