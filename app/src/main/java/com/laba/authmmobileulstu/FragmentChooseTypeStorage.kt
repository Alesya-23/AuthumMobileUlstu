package com.laba.authmmobileulstu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.laba.authmmobileulstu.databinding.FragmentChooseStorageBinding

class FragmentChooseTypeStorage : Fragment(R.layout.fragment_choose_storage) {
    private lateinit var dataBinding : FragmentChooseStorageBinding
    private var typeData: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding = FragmentChooseStorageBinding.bind(view)
        chooseTypeDownloadData()
    }


    private fun chooseTypeDownloadData() {
        with(dataBinding) {
            database.setOnClickListener {
                typeData = "DATABASE"
                var mainFragment: MainFragment = MainFragment.newInstance(typeData)
                childFragmentManager.beginTransaction()
                    .addToBackStack(MainFragment::class.simpleName)
                    .replace(R.id.choose, mainFragment)
                    .commit()
            }
            json.setOnClickListener {
                typeData = "JSON"
                var mainFragment: MainFragment = MainFragment.newInstance(typeData)
                childFragmentManager.beginTransaction()
                    .addToBackStack(MainFragment::class.simpleName)
                    .replace(R.id.choose, mainFragment)
                    .commit()
            }
        }
    }
}