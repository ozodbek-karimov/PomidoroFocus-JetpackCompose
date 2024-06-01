package ozodbek.pl.pomidorofocus.presentation.utils

data class ShowContent<T>(
	val isLoading: Boolean = true,
	val content: T? = null
)
