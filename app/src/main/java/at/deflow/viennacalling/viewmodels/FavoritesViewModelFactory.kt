@file:Suppress("WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")

package at.deflow.viennacalling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.deflow.viennacalling.repository.EventsRepository


@Suppress("UNCHECKED_CAST")
class FavoritesViewModelFactory(private val repository: EventsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}