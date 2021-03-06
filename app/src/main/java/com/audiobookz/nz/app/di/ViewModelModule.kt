package com.audiobookz.nz.app.browse.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.audiobookz.nz.app.browse.ui.CategoryViewModel
//import com.audiobookz.nz.app.browse.ui.viewmo

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindThemeViewModel(viewModel: CategoryViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(LegoSetsViewModel::class)
//    abstract fun bindLegoSetsViewModel(viewModel: LegoSetsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LegoSetViewModel::class)
//    abstract fun bindLegoSetViewModel(viewModel: LegoSetViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
