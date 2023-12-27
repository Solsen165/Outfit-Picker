package com.example.outfitpicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.outfitpicker.databasefiles.ClothesRepository
import com.example.outfitpicker.databasefiles.Item
import kotlinx.coroutines.launch

class ItemsViewModel(private val repository: ClothesRepository): ViewModel() {
    private val allItems = repository.getAllItems()

    fun insert(item: Item): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            result.postValue(repository.insertItem(item))
        }
        return result
    }
    fun update(item: Item) = viewModelScope.launch {
        repository.updateItem(item)
    }
    fun delete(item: Item) = viewModelScope.launch {
        repository.deleteItem(item)
    }
    fun getAllNotes(): LiveData<List<Item>>{
        return allItems
    }
}

class ItemsViewModelFactory(private val repository: ClothesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ItemsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}