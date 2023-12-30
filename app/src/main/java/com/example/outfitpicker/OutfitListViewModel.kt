package com.example.outfitpicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.outfitpicker.databasefiles.ClothesRepository
import com.example.outfitpicker.databasefiles.ItemOutfitCrossRef
import com.example.outfitpicker.databasefiles.Outfit
import com.example.outfitpicker.databasefiles.OutfitWithItems
import kotlinx.coroutines.launch

class OutfitListViewModel(private val repository: ClothesRepository): ViewModel() {
    private val allOutfits = repository.getAllOutfits()

    fun insert(outfit: Outfit): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            result.postValue(repository.insertOutfit(outfit))
        }
        return result
    }
    fun update(outfit: Outfit) = viewModelScope.launch {
        repository.updateOutfit(outfit)
    }
    fun delete(outfit: Outfit) = viewModelScope.launch {
        repository.deleteOutfit(outfit)
    }
    fun getAllOutfits(): LiveData<List<OutfitWithItems>> {
        return allOutfits
    }

    fun getOutfitWithItems(outfitId : Int): LiveData<OutfitWithItems> {
        val result = MutableLiveData<OutfitWithItems>()
        viewModelScope.launch {
            result.postValue(repository.getOutfitWithItems(outfitId))
        }
        return result
    }
    fun getOutfitWithAttributes(season: String, occasion: String): LiveData<List<OutfitWithItems>> {
        val result = MutableLiveData<List<OutfitWithItems>>()
        viewModelScope.launch {
            val queryString = "SELECT * FROM outfit_table WHERE $season = 1 AND $occasion = 1"
            val query = SimpleSQLiteQuery(queryString)
            result.postValue(repository.getOutfitsWithAttributes(query))
        }
        return result
    }

    fun insert(itemOutfitCrossRef: ItemOutfitCrossRef) = viewModelScope.launch {
        repository.insertItemOutfitCrossRef(itemOutfitCrossRef)
    }
    fun delete(itemOutfitCrossRef: ItemOutfitCrossRef) = viewModelScope.launch {
        repository.deleteItemOutfitCrossRef(itemOutfitCrossRef)
    }

}

class OutfitViewModelFactory(private val repository: ClothesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutfitListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return OutfitListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}