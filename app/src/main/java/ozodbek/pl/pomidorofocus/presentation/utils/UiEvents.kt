package ozodbek.pl.pomidorofocus.presentation.utils

sealed class UiEvents {
	data class ShowSnackBar(val message: String) : UiEvents()
}
