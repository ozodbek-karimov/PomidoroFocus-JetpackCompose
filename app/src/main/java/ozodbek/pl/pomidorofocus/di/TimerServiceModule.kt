package ozodbek.pl.pomidorofocus.di

import android.content.Context
import ozodbek.pl.pomidorofocus.data.repository.TimerServiceRepoImpl
import ozodbek.pl.pomidorofocus.data.room.AppDataBase
import ozodbek.pl.pomidorofocus.data.services.NotificationBuilderHelper
import ozodbek.pl.pomidorofocus.domain.repository.TimerServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object TimerServiceModule {

	@Provides
	@ServiceScoped
	fun providesNotificationHelper(
		@ApplicationContext context: Context
	): NotificationBuilderHelper = NotificationBuilderHelper(context)


	@Provides
	@ServiceScoped
	fun providesSessionServiceRepo(
		dataBase: AppDataBase
	): TimerServiceRepository = TimerServiceRepoImpl(
		dailySessionDao = dataBase.daySessionDao(),
		sessionDao = dataBase.sessionDao()
	)
}