package gr.sportsbook.data.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.sportsbook.data.repository.SportsRepositoryImpl
import gr.sportsbook.domain.repository.SportsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSportsRepository(
        sportsRepositoryImpl: SportsRepositoryImpl
    ): SportsRepository
}
