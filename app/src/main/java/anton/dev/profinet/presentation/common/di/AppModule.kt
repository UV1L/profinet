package anton.dev.profinet.presentation.common.di

import anton.dev.profinet.presentation.common.navigation.NavEventsHandler
import anton.dev.profinet.presentation.common.navigation.NavEventsHandlerImpl
import anton.dev.profinet.presentation.common.ui.ErrorViewHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Provides
    @Singleton
    fun provideNavHandler(): NavEventsHandler = NavEventsHandlerImpl()

    @Provides
    @Singleton
    fun provideErrorViewHolder(): ErrorViewHolder = ErrorViewHolder()
}