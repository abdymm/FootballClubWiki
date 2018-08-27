package com.abdymalikmulky.fooball.footballclubwiki.ui.login

import com.abdymalikmulky.fooball.footballclubwiki.ui.club.ClubListActivity
import com.abdymalikmulky.fooball.footballclubwiki.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoginActivityUI : AnkoComponent<LoginActivity> {
    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui){
        relativeLayout {
            imageView(R.drawable.logo) {
                id = Ids.logo
            }.lparams(width = dip(150), height = dip(150)) {
                alignParentTop()
                centerHorizontally()
                margin = dip(15)
            }
            val username = editText {
                ems = 10
                hint = "Username"
                id = Ids.username
                inputType = 97
            }.lparams(width = matchParent, height = wrapContent) {
                below(Ids.logo)
                alignParentStart()
                leftMargin = dip(15)
                topMargin = dip(15)
                rightMargin = dip(15)
            }
            editText {
                ems = 10
                hint = "Password"
                id = Ids.password
                inputType = 129
            }.lparams(width = matchParent, height = wrapContent) {
                below(Ids.username)
                alignParentStart()
                leftMargin = dip(15)
                rightMargin = dip(15)
            }
            button("LOGIN") {
                id = Ids.button_login
                onClick {
                    alert("Anda Mau Login?") {
                        yesButton {
                            val clubs = listOf("Persib Bandung", "Arema FC", "Bali United")
                            selector("Hai ${username.text}! Pilih dulu club kesukaan anda", clubs) { _, i ->
                                toast("Anda menyukai ${clubs[i]}")
                                startActivity<ClubListActivity>("username" to "${username.text}")
                            }
                        }
                        noButton {
                            toast("Yah sedih")
                            indeterminateProgressDialog("Sad Loading").show()
                        }
                    }.show()
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                below(Ids.password)
                centerHorizontally()
                topMargin = dip(10)
            }
        }
    }
}

private object Ids {
    val button_login = 1
    val logo = 2
    val password = 3
    val username = 4
}