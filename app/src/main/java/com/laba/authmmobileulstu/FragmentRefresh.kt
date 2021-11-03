package com.laba.authmmobileulstu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.laba.authmmobileulstu.databinding.FragmentRefreshBinding

class FragmentRefresh : Fragment(R.layout.fragment_refresh) {
    private lateinit var viewBindingRefresh: FragmentRefreshBinding
    private val userViewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBindingRefresh = FragmentRefreshBinding.bind(view)
        viewBindingRefresh.buttonSet.setOnClickListener {
            onClickButtonAdd()
        }
        viewBindingRefresh.buttonCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        var oldItem = userViewModel.newItem.value
        viewBindingRefresh.textViewData.text = String.format("Старый вариант : $oldItem")
    }

    private fun onClickButtonAdd() {
        if (viewBindingRefresh.editTextTextData.text.toString().isNotEmpty()) {
            userViewModel.newItem.value =
                viewBindingRefresh.editTextTextData.text.toString()
            requireActivity().supportFragmentManager.popBackStack()
        } else Toast.makeText(
            activity?.applicationContext,
            "Ошибка! Не введено новое значение",
            Toast.LENGTH_LONG
        ).show()
    }
}