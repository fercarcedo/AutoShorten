package fergaral.autoshorten.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fergaral.autoshorten.ui.DomainsViewModel


/**
 * Created by Fer on 10/02/2018.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DomainsViewModel::class)
    abstract fun bindDomainViewModel(viewModel: DomainsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}