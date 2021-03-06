package com.laba.authmmobileulstu

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.laba.authmmobileulstu.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var viewMainFragmentBinding: FragmentMainBinding
    var arrayAdapter: ArrayAdapter<*>? = null
    private var list = ArrayList<String>()
    private lateinit var checkedItemPositions: SparseBooleanArray
    private val userViewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMainFragmentBinding = FragmentMainBinding.bind(view)
        initializationButton()
        initializationList()
        userViewModel.itemList.observe(viewLifecycleOwner, Observer {
            addNewItem(it)
            arrayAdapter?.notifyDataSetChanged()
        })
        userViewModel.deleteFlag.observe(viewLifecycleOwner, {
            deleteItems()
            arrayAdapter?.notifyDataSetChanged()
        })
        userViewModel.newItem.observe(viewLifecycleOwner, {
            changeItem()
            arrayAdapter?.notifyDataSetChanged()
        })
    }

    override fun onSaveInstanceState(savedState: Bundle) {
        super.onSaveInstanceState(savedState)
        savedState.putStringArrayList("list", list)
    }

    companion object {
        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun initializationList() {
        list.add("Hip-hop")
        list.add("Break")
        list.add("Vog")
        list.add("Locking")
        viewMainFragmentBinding?.list?.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        arrayAdapter = activity?.let {
            ArrayAdapter(
                it.applicationContext,
                R.layout.list_item, list
            )
        }
        viewMainFragmentBinding.list.adapter = arrayAdapter
    }

    private fun onCLickAdd() {
        val fragmentAdd = FragmentAdd()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(FragmentAdd::class.simpleName)
            .replace(R.id.fragment_main, fragmentAdd)
            .commit()
    }

    private fun addNewItem(itemView: String) {
        list.add(itemView)
    }

    private fun deleteItems(): ArrayList<String> {
        if (userViewModel.deleteFlag.value == true) {
            val checkedItemPositions: SparseBooleanArray =
                viewMainFragmentBinding?.list?.checkedItemPositions
            var countSelect = 0
            for (i in list.size - 1 downTo 0) {
                if (checkedItemPositions[i]) {
                    countSelect++
                    list.removeAt(i)
                }
            }
            checkedItemPositions.clear()
            arrayAdapter?.notifyDataSetChanged()
            userViewModel.deleteFlag.value = false
        }
        return list
    }

    private fun getFirstSelectedItem() {
        val sbArray: SparseBooleanArray = viewMainFragmentBinding.list.checkedItemPositions
        for (i in 0 until sbArray.size()) {
            val key = sbArray.keyAt(i)
            if (sbArray[key]) {
                viewMainFragmentBinding.list.setItemChecked(key, false)
                userViewModel.itemKey.value = key
                userViewModel.newItem.value = list[key]
                break
            }
        }
    }

    private fun changeItem() {
        val key: Int? = userViewModel.itemKey.value
        list[key!!] = userViewModel.newItem.value.toString()
    }

    private fun onClickDelete() {
        checkedItemPositions = viewMainFragmentBinding?.list?.checkedItemPositions
        var countSelect = 0
        for (i in list.size - 1 downTo 0) {
            if (checkedItemPositions[i]) {
                countSelect++
            }
        }
        if (countSelect != 0) {
            val fragmentDelete = FragmentDelete()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(FragmentDelete::class.simpleName)
                .replace(R.id.fragment_main, fragmentDelete)
                .commit()
        } else Toast.makeText(
            activity?.applicationContext,
            "???? ???? ?????????????? ?????????????? ?????? ????????????????",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun openResultActivity() {
        if (viewMainFragmentBinding.textViewSearch.text.isNotEmpty()) {
            val substring: String = viewMainFragmentBinding.textViewSearch.text.toString()
            val newList = ArrayList<String>()
            for (i in list.indices) {
                if (list[i] == substring) {
                    newList.add(list[i])
                }
            }
            viewMainFragmentBinding.textViewSearch.text.clear()
            (activity as MainActivity?)?.openDisplayResultActivity(newList)
        }
    }

    private fun onCLickRefresh() {
        getFirstSelectedItem()
        val fragmentRefresh = FragmentRefresh()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(FragmentRefresh::class.simpleName)
            .replace(R.id.fragment_main, fragmentRefresh)
            .commit()
    }

    private fun initializationButton() {
        with(viewMainFragmentBinding) {
            buttonAdd.setOnClickListener { onCLickAdd() }
            buttonDelete.setOnClickListener { onClickDelete() }
            buttonRefresh.setOnClickListener { onCLickRefresh() }
            buttonSearch.setOnClickListener { openResultActivity() }
        }
    }
}