package ozodbek.pl.pomidorofocus.data.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ozodbek.pl.pomidorofocus.R
import ozodbek.pl.pomidorofocus.data.room.convertors.DateConvertor
import ozodbek.pl.pomidorofocus.data.room.convertors.DurationConvertor
import ozodbek.pl.pomidorofocus.data.room.convertors.LocalDateConverter
import ozodbek.pl.pomidorofocus.data.room.convertors.TimerModeConvertor
import ozodbek.pl.pomidorofocus.data.room.dao.DaySessionDao
import ozodbek.pl.pomidorofocus.data.room.dao.SessionInfoDao
import ozodbek.pl.pomidorofocus.data.room.entity.DaySessionEntry
import ozodbek.pl.pomidorofocus.data.room.entity.SessionInfoEntity

@Database(
	version = 3,
	entities = [
		DaySessionEntry::class,
		SessionInfoEntity::class,
	],
//	autoMigrations = [
//		AutoMigration(
//			from = 1,
//			to = 2,
//			spec = AppMigrations.RenameSessionDurationField::class
//		),
//		AutoMigration(
//			from = 2,
//			to = 3,
//			spec = AppMigrations.DeleteSessionAtField::class
//		)
//	],
	exportSchema = true,
)
@TypeConverters(
	DateConvertor::class,
	DurationConvertor::class,
	TimerModeConvertor::class,
	LocalDateConverter::class
)
abstract class AppDataBase : RoomDatabase() {

	abstract fun sessionDao(): SessionInfoDao

	abstract fun daySessionDao(): DaySessionDao

	companion object {
		fun getInstance(context: Context): AppDataBase =
			Room.databaseBuilder(
				context = context,
				klass = AppDataBase::class.java,
				name = context.getString(R.string.database_name)
			)
				// comment out the to check the fake_data for statistical graph
//                .createFromAsset("database/fake_data.db")
				.addTypeConverter(DateConvertor())
				.addTypeConverter(TimerModeConvertor())
				.addTypeConverter(DurationConvertor())
				.addTypeConverter(LocalDateConverter())
				.build()
	}
}