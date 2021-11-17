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
        viewBindingRefresh.okAdd.setOnClickListener {
            onClickButtonAdd()
        }
        viewBindingRefresh.cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        var oldItemId = userViewModel.newItem.value?.id
        var oldItemName = userViewModel.newItem.value?.nameDance
        var oldItemDate = userViewModel.newItem.value?.isModernDance
        viewBindingRefresh.inId.text = String.format("Старый вариант ID: $oldItemId")
        viewBindingRefresh.inName.text = String.format("Старый вариант названия: $oldItemName")
        viewBindingRefresh.inDate.text = String.format("Старый вариант даты: $oldItemDate")
    }

    private fun onClickButtonAdd() {
        val newDanceName = viewBindingRefresh.addDanceNameElementEdit.text
        val newDanceId  = viewBindingRefresh.addDanceElementIdEdit.text.toString()
        var isModern = viewBindingRefresh.addDanceIsModern.text.toString()
        var isDanceModern = checkIsDanceModer(isModern)
        if (newDanceName.isNotEmpty() && newDanceId.isNotEmpty() && isModern.isNotEmpty()) {
            userViewModel.newItem.value = (ItemList(Integer.parseInt(newDanceId), newDanceName.toString(),
                isDanceModern ))
            requireActivity().supportFragmentManager.popBackStack()
        } else Toast.makeText(
            activity?.applicationContext,
            "Ошибка! Не введено новое значение",
            Toast.LENGTH_LONG
        ).show()
    }

    fun checkIsDanceModer(isModern : String) : Boolean{
        if(isModern == "Да" || isModern == "да" || isModern == "Yes" || isModern == "yes" )
            return true
        else   if(isModern == "Нет" || isModern == "нет" || isModern == "No" || isModern == "no")
            return false
        Toast.makeText(context, "Неверный критерий современности танца", Toast.LENGTH_LONG).show()
        return false
    }
}