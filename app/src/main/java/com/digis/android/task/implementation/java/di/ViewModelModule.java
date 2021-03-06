package com.digis.android.task.implementation.java.di;

import com.digis.android.task.implementation.java.MainViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
@SuppressWarnings("unused")
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory viewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
