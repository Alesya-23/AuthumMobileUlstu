package com.laba.authmmobileulstu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.laba.authmmobileulstu.databinding.FragmentRefreshBinding
import java.time.LocalDate

class FragmentRefresh : Fragment(R.layout.fragment_refresh) {
    private lateinit var viewBindingRefresh: FragmentRefreshBinding
    private val userViewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBindingRefresh = FragmentRefreshBinding.bind(view)
        viewBindingRefresh.okAdd.setOnClickListener {
            onClickButtonAdd()
        }
        viewBindingRefresh.cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        var oldItemId = userViewModel.newItem.value?.id
        var oldItemName = userViewModel.newItem.value?.nameDance
        var oldItemDate = userViewModel.newItem.value?.DateCreate
        viewBindingRefresh.inId.text = String.format("Старый вариант ID: $oldItemId")
        viewBindingRefresh.inName.text = String.format("Старый вариант названия: $oldItemName")
        viewBindingRefresh.inDate.text = String.format("Старый вариант даты: $oldItemDate")
    }

    private fun onClickButtonAdd() {
        val newDanceName = viewBindingRefresh.addDanceNameElementEdit.text
        val newDanceId  = viewBindingRefresh.addDanceElementIdEdit.text.toString()
        val newDanceDate = viewBindingRefresh.addDanceDateElementEdit.text
        if (newDanceName.isNotEmpty() && newDanceId.isNotEmpty() && newDanceDate.isNotEmpty()) {
            userViewModel.newItem.value = ItemList(Integer.parseInt(newDanceId), newDanceName.toString(),
                LocalDate.parse(newDanceDate))
            requireActivity().supportFragmentManager.popBackStack()
        } else Toast.makeText(
            activity?.applicationContext,
            "Ошибка! Не введено новое значение",
            Toast.LENGTH_LONG
        ).show()
    }
}