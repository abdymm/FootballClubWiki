package com.abdymalikmulky.fooball.footballclubwiki.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.abdymalikmulky.fooball.footballclubwiki.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun openFragment(activity: FragmentActivity?, fragment: Fragment, containerView: Int) {
    val transaction = activity?.supportFragmentManager?.beginTransaction()
    transaction?.replace(containerView, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
}