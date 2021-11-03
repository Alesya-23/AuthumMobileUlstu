package com.laba.authmmobileulstu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.laba.authmmobileulstu.databinding.FragmentDeleteBinding

class FragmentDelete : Fragment(R.layout.fragment_delete) {
    private lateinit var viewbindingdel: FragmentDeleteBinding
    private val userViewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbindingdel = FragmentDeleteBinding.bind(view)
        viewbindingdel.deleteButton.setOnClickListener { onClickButtonDelete() }
        viewbindingdel.cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }
    }

    private fun onClickButtonDelete() {
        userViewModel.deleteFlag.value = true
        activity?.supportFragmentManager?.popBackStack()
        Toast.makeText(context, "Удалено", Toast.LENGTH_LONG).show()
    }
}