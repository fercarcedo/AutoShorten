package fergaral.autoshorten.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fergaral.autoshorten.services.ClipboardService
import fergaral.autoshorten.ui.DomainActivity
import fergaral.autoshorten.ui.DomainsToShortenActivity
import fergaral.autoshorten.ui.ShortenUrlFloatingActivity

/**
 * Created by Fer on 11/02/2018.
 */
@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDomainsToShortenActivity(): DomainsToShortenActivity

    @ContributesAndroidInjector
    abstract fun contributeDomainActivity(): DomainActivity

    @ContributesAndroidInjector
    abstract fun contributeClipboardService(): ClipboardService
}