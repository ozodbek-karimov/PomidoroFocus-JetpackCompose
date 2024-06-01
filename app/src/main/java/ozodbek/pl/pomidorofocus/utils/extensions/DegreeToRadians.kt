package ozodbek.pl.pomidorofocus.utils.extensions

fun Float.toRadians(): Float {
	return Math.toRadians(this.toDouble()).toFloat()
}
