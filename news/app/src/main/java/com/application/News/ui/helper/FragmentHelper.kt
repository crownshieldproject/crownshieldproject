package com.application.News.ui.helper

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentHelper(val fragmentManager: FragmentManager) {

    var frameLayout: FrameLayout? = null

    fun setUpFrame(fragment: Fragment, frameLayout: FrameLayout){
        this.frameLayout = frameLayout
        push(fragment)
    }

    fun popBackStackImmediate() {
        fragmentManager.popBackStack()
        fragmentManager.executePendingTransactions()
    }

    fun popAllBackstack() {
        val backCount = fragmentManager.backStackEntryCount
        if (backCount > 0) {
            fragmentManager.popBackStackImmediate(
                    fragmentManager.getBackStackEntryAt(0).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    fun push(fragment: Fragment, title: String?) {
        val tag = fragment.javaClass.canonicalName

        if (title != null) {
            try {
                fragmentManager.beginTransaction()
                        .replace(frameLayout!!.id, fragment, tag)
                        .addToBackStack(title)
                        .commit()
            } catch (ile: IllegalStateException) {
                fragmentManager.beginTransaction()
                        .replace(frameLayout!!.id, fragment, tag)
                        .addToBackStack(title)
                        .commitAllowingStateLoss()
            }

        } else {
            try {
                fragmentManager.beginTransaction()
                        .replace(frameLayout!!.id, fragment, tag).commit()
            } catch (ile: IllegalStateException) {
                fragmentManager.beginTransaction()
                        .replace(frameLayout!!.id, fragment, tag).commitAllowingStateLoss()
                //  .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )

            }

        }

    }

    fun push(fragment: Fragment?) {
        if (fragment == null) {
            return
        }
        push(fragment, null)
    }

}