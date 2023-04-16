package anton.dev.profinet.presentation.common.di

import android.content.Context
import anton.dev.profinet.presentation.common.navigation.NavEventsHandler
import anton.dev.profinet.presentation.common.navigation.NavEventsHandlerImpl
import anton.dev.profinet.presentation.common.ui.ErrorViewHolder
import anton.dev.profinet.presentation.common.ui.ViewModelLifecycleOwner
import anton.dev.profinet.presentation.main_screen.services.GmsLastLocationService
import anton.dev.profinet.presentation.main_screen.services.LastLocationService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.logging.Logger
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

    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LastLocationService =
        GmsLastLocationService(context)

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.getLogger("Profinet")
}