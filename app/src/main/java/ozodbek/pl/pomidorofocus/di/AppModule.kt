package ozodbek.pl.pomidorofocus.di

import android.content.Context
import ozodbek.pl.pomidorofocus.data.datastore.SettingsPreferencesImpl
import ozodbek.pl.pomidorofocus.data.repository.AirPlaneSettingsInfo
import ozodbek.pl.pomidorofocus.data.repository.SessionReminderAlarm
import ozodbek.pl.pomidorofocus.data.room.AppDataBase
import ozodbek.pl.pomidorofocus.data.room.dao.SessionInfoDao
import ozodbek.pl.pomidorofocus.domain.facade.SessionReminderFacade
import ozodbek.pl.pomidorofocus.domain.facade.SettingsInfoFacade
import ozodbek.pl.pomidorofocus.domain.facade.SettingsPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesAppDataBase(
		@ApplicationContext context: Context
	): AppDataBase = AppDataBase.getInstance(context)

	@Provides
	@Singleton
	fun providesSessionInfoDao(
		dataBase: AppDataBase
	): SessionInfoDao = dataBase.sessionDao()

	@Provides
	@Singleton
	fun provideSettingsPrefs(
		@ApplicationContext context: Context
	): SettingsPreferences = SettingsPreferencesImpl(context)

	@Provides
	@Singleton
	fun providesAirplaneListener(
		@ApplicationContext context: Context
	): SettingsInfoFacade = AirPlaneSettingsInfo(context)

	@Provides
	@Singleton
	fun providesReminderAlarm(
		@ApplicationContext context: Context
	): SessionReminderFacade = SessionReminderAlarm(context)
}