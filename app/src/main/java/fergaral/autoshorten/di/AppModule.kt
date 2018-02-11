package fergaral.autoshorten.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.ClipboardManager
import dagger.Module
import dagger.Provides
import fergaral.autoshorten.AutoShorten
import fergaral.autoshorten.data.AppDatabase
import fergaral.autoshorten.data.DomainDao
import fergaral.autoshorten.data.repository.DomainDataSource
import fergaral.autoshorten.data.repository.DomainRepository
import fergaral.autoshorten.listeners.ClipboardListener
import javax.inject.Singleton

/**
 * Created by Fer on 10/02/2018.
 */
@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = app

    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase::class.java.simpleName).build()
    }

    @Provides
    @Singleton
    fun providesDomainDao(db: AppDatabase): DomainDao = db.domainDao()

    @Provides
    @Singleton
    fun providesDomainRepository(dao: DomainDao): DomainRepository = DomainDataSource(dao)

    @Provides
    @Singleton
    fun providesClipboardListener(app: Application, repository: DomainRepository): ClipboardManager.OnPrimaryClipChangedListener
            = ClipboardListener(app, repository)
}