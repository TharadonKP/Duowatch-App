package com.example.maskshop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class UserFragment : Fragment() {
    var btnedit:Button?=null;
    var userID:String?=null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_user, container, false)
        btnedit= root.findViewById(R.id.btnedit)
        btnedit?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userID", userID)

            val fm = UserUpdateFragment()
            fm.arguments = bundle;

            val fragmentTransaction = requireActivity().
            supportFragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.nav_host_fragment, fm)
            fragmentTransaction.commit()
        }


/*

val fragmentTransaction = requireActivity().
            supportFragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.nav_host_fragment, UserUpdateFragment())
            fragmentTransaction.commit()
 */






        return root
    }

}

