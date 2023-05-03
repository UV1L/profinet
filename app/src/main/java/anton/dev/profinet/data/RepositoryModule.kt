package anton.dev.profinet.data

import anton.dev.profinet.domain.repositories.CustomerRepository
import anton.dev.profinet.domain.repositories.ServicesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryProvidesModule {

    @Provides
    @Reusable
    fun provides(): Repository = Repository()
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryBindsModule {

    @Binds
    fun bind2(repository: Repository): ServicesRepository

    @Binds
    fun bind3(repository: Repository): CustomerRepository
}