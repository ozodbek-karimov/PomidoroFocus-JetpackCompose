package ozodbek.pl.pomidorofocus.domain.facade

import kotlinx.coroutines.flow.Flow

interface SettingsInfoFacade {

	val initialValue: Boolean

	val status: Flow<Boolean>

}