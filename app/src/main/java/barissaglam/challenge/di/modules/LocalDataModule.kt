package barissaglam.challenge.di.modules

import android.content.Context
import androidx.room.Room
import barissaglam.challenge.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase = Room.databaseBuilder(appContext, LocalDatabase::class.java, "local_database").build()

    @Provides
    @Singleton
    fun provideSpaceVehicleDao(db: LocalDatabase) = db.getSpaceVehicleDao()

    @Provides
    @Singleton
    fun provideFavoriteStationDao(db: LocalDatabase) = db.getFavoriteStationDao()
}