package ozodbek.pl.pomidorofocus.di

import android.content.Context
import ozodbek.pl.pomidorofocus.data.repository.StatisticsRepoImpl
import ozodbek.pl.pomidorofocus.data.room.AppDataBase
import ozodbek.pl.pomidorofocus.data.services.UIServiceController
import ozodbek.pl.pomidorofocus.domain.repository.StatisticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

	@Provides
	@ViewModelScoped
	fun providesServiceIntents(
		@ApplicationContext context: Context
	): UIServiceController = UIServiceController(context)


	@Provides
	@ViewModelScoped
	fun providesSessionStatisticsRepo(
		dataBase: AppDataBase
	): StatisticsRepository = StatisticsRepoImpl(
		sessionDao = dataBase.sessionDao(),
		daySessionDao = dataBase.daySessionDao()
	)

}