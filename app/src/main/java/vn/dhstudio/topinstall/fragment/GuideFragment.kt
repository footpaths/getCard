package vn.dhstudio.topinstall.fragment

 import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
 import vn.dhstudio.topinstall.R


/**
 * Created by PC on 12/8/2017.
 */
class GuideFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.spin_fragment, container, false)

        return view
    }




}