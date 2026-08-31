package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vascs.data.repository.VascsRepository

class OmegaViewModelFactory(
    private val repository: VascsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OmegaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OmegaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
