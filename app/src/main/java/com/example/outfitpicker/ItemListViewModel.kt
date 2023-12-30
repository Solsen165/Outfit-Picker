package com.example.outfitpicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.outfitpicker.databasefiles.ClothesRepository
import com.example.outfitpicker.databasefiles.Item
import com.example.outfitpicker.databasefiles.ItemWithOutfits
import com.example.outfitpicker.databasefiles.OutfitWithItems
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
    fun getAllItems(): LiveData<List<Item>>{
        return allItems
    }
    fun getOutfitsWithItemsId(itemId: Int): LiveData<List<OutfitWithItems>> {
        val result = MutableLiveData<List<OutfitWithItems>>()
        viewModelScope.launch {
            result.postValue(repository.getOutfitsWithItemsId(itemId))
        }
        return result
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