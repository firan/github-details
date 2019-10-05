package com.example.myapplication.view.fragment.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.view.fragment.interfaces.FlatActionBar
import kotlinx.android.synthetic.main.fragment_second_host.*

class SecondHostFragment : Fragment(), FlatActionBar {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs.setupWithViewPager(viewPager)
        val adapter = SecondFragmentTabAdapter(childFragmentManager)
        adapter.addFragment(FirstTabFragment(), requireContext().getString(R.string.label_first_tab_fragment))
        adapter.addFragment(SecondTabFragment(), requireContext().getString(R.string.label_second_tab_fragment))
        viewPager.adapter = adapter
    }
}
