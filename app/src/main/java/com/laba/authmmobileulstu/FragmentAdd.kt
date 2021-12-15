package com.laba.authmmobileulstu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.laba.authmmobileulstu.databinding.FragmentAddBinding
import java.time.LocalDate

class FragmentAdd : Fragment(R.layout.fragment_add) {
    private lateinit var viewbindingadd: FragmentAddBinding
    private val userViewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbindingadd = FragmentAddBinding.bind(view)
        viewbindingadd.okAdd.setOnClickListener {
            onClickButtonAdd()
        }
        viewbindingadd.cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun onClickButtonAdd() {
        val newDanceName = viewbindingadd.addDanceNameElementEdit.text
        val newDanceId  = viewbindingadd.addDanceElementIdEdit.text.toString()
        var isModern = viewbindingadd.addDanceIsModern.text.toString()
        var isDanceModern = checkIsDanceModern(isModern)
        if (newDanceName.isNotEmpty() && newDanceId.isNotEmpty() && isModern.isNotEmpty()) {
            userViewModel.setItem(ItemList(Integer.parseInt(newDanceId), newDanceName.toString(),
                isDanceModern ))
            requireActivity().supportFragmentManager.popBackStack()
            Toast.makeText(context, "Добавлено", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkIsDanceModern(isModern : String) : Boolean{
        if(isModern == "Да" || isModern == "да" || isModern == "Yes" || isModern == "yes" )
            return true
        else   if(isModern == "Нет" || isModern == "нет" || isModern == "No" || isModern == "no")
            return false
        Toast.makeText(context, "Неверный критерий современности танца", Toast.LENGTH_LONG).show()
        return false
    }
}