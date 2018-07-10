package nguyen.luan.getcard.Utils

/**
 * Created by PC on 12/11/2017.
 */
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import nguyen.luan.getcard.R


class MoveScreen(internal var context: Context) {


    fun clickedOn(idlayout: Int, fragment: Fragment) {

        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .addToBackStack(null)
                .replace(idlayout, fragment)
                .commit()
    }

    fun firstMoveFragment(idlayout: Int, fragment: Fragment) {

        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(idlayout, fragment)
                .commit()
    }

    fun moveOneFragment(idlayout: Int, fragment: Fragment, bundle: Bundle) {

        fragment.arguments = bundle
        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
//                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(null)
                .replace(idlayout, fragment)
                .commit()

    }

    fun moveTwoFragment(idlayout1: Int, idlayout2: Int, fragment1: Fragment, fragment2: Fragment, bundle: Bundle) {
        fragment1.arguments = bundle
        fragment2.arguments = bundle
        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(null)
                .replace(idlayout1, fragment1)
                .replace(idlayout2, fragment2)
                .commit()

    }

    fun finishFragment(fragment: Fragment) {
        //        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        (context as FragmentActivity).supportFragmentManager.popBackStack()
    }
}