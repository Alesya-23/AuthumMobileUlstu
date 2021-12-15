package com.laba.authmmobileulstu

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.laba.authmmobileulstu.database.DatabaseHelper
import com.laba.authmmobileulstu.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var viewMainFragmentBinding: FragmentMainBinding
    var arrayAdapter: ItemListAdapter? = null
    private var list = ArrayList<ItemList>()
    private var jsonHelper: JSONHelper = JSONHelper()
    private lateinit var checkedItemPositions: SparseBooleanArray
    private val userViewModel: MyViewModel by activityViewModels()
    private var typeData: String = "DATABASE"
    var dbHelper: DatabaseHelper? = null
    var sPref: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMainFragmentBinding = FragmentMainBinding.bind(view)
        initializationButton()
        initializationList()
        open()
        sPref = activity?.applicationContext?.getSharedPreferences("Dance.db", MODE_PRIVATE)
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

    companion object {
        fun newInstance(typeDataNow: String): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            fragment.typeData = typeDataNow
            return fragment
        }
    }

    private fun initializationList() {
        viewMainFragmentBinding?.list?.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        arrayAdapter = activity?.let {
            ItemListAdapter(
                it.applicationContext, list
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

    private fun addNewItem(itemView: ItemList) {
        list.add(itemView)
        if (typeData == "DATABASE") {
            storage!!.insert(itemView)
        } else save()
    }

    private fun deleteItems(): ArrayList<ItemList> {
        if (userViewModel.deleteFlag.value == true) {
            val checkedItemPositions: SparseBooleanArray =
                viewMainFragmentBinding?.list?.checkedItemPositions
            var countSelect = 0
            for (i in list.size - 1 downTo 0) {
                if (checkedItemPositions[i]) {
                    countSelect++
                    if (typeData == "DATABASE") {
                        storage?.delete(list[i])
                    }
                    else  jsonHelper.deleteFile()
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
        list[key!!] = userViewModel.newItem.value!!
        if (typeData == "DATABASE") {
            storage!!.update(list[key])
        } else save()
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
            "Вы не выбрали элемент для удаления",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun openResultActivity() {
        if (viewMainFragmentBinding.textViewSearch.text.isNotEmpty()) {
            val substring: String = viewMainFragmentBinding.textViewSearch.text.toString()
            val newList = ArrayList<ItemList>()
            for (i in list.indices) {
                if (list[i].nameDance == substring) {
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

    private fun save() {
        var result: Boolean = false
        if (typeData == "JSON") {
            CoroutineScope(Dispatchers.IO).launch {
                result =
                    jsonHelper.exportToJSON((activity as MainActivity).applicationContext, list)
            }
            if (result) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Данные сохранены",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Не удалось сохранить данные",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun open() {
        dbHelper = activity?.applicationContext?.let { DatabaseHelper(it) }
        list = (activity as MainActivity).getService().getList() as ArrayList<ItemList>
//        list = jsonHelper.loadDataFromJson((activity as MainActivity).applicationContext)!!
        CoroutineScope(Dispatchers.IO).launch {
            list = (jsonHelper.loadDataFromJson((activity as MainActivity).applicationContext) as ArrayList<ItemList>?)!!
        }
        if (list != null) {
            arrayAdapter = activity?.applicationContext?.let { ItemListAdapter(it, list) }
            viewMainFragmentBinding?.list?.adapter = arrayAdapter
            Toast.makeText(activity?.applicationContext, "Данные восстановлены", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(
                activity?.applicationContext,
                "Не удалось открыть данные",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    fun savePrefs(view: View?) {
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.setUsers(list)
        val jsonString = gson.toJson(dataItems)
        val prefsEditor = sPref!!.edit()
        prefsEditor.putString("MyObject", jsonString)
        prefsEditor.commit()
        Toast.makeText(activity?.applicationContext, "Данные сохранены", Toast.LENGTH_LONG).show()
    }

    fun loadPrefs(view: View?) {
        val gson = Gson()
        val json = sPref!!.getString("MyObject", "")
        val obj: DataItems = gson.fromJson(json, DataItems::class.java)
        list = obj.getUsers() as ArrayList<ItemList>
        if (list != null) {
            arrayAdapter = activity?.applicationContext?.let { ItemListAdapter(it, list) }
            viewMainFragmentBinding?.list?.adapter = arrayAdapter
            Toast.makeText(activity?.applicationContext, "Данные восстановлены Prefs", Toast.LENGTH_LONG).show()
        }
    }
}