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
        val newDanceDate = viewbindingadd.addDanceDateElementEdit.text
        if (newDanceName.isNotEmpty() && newDanceId.isNotEmpty() && newDanceDate.isNotEmpty()) {
            userViewModel.setItem(ItemList(Integer.parseInt(newDanceId), newDanceName.toString(),
                LocalDate.parse(newDanceDate)))
            requireActivity().supportFragmentManager.popBackStack()
            Toast.makeText(context, "Добавлено", Toast.LENGTH_LONG).show()
        }
    }
}