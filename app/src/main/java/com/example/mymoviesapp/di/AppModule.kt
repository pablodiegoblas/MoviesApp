package com.example.mymoviesapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymoviesapp.BuildConfig
import com.example.mymoviesapp.data.api.APIServices.MoviesService
import com.example.mymoviesapp.data.api.interceptors.ApiKeyInterceptor
import com.example.mymoviesapp.data.api.interceptors.LanguageInterceptor
import com.example.mymoviesapp.data.database.MoviesDatabase
import com.example.mymoviesapp.data.database.dao.GenresDao
import com.example.mymoviesapp.data.database.dao.MoviesDao
import com.example.mymoviesapp.handlers.PreferencesHandler
import com.example.mymoviesapp.handlers.PreferencesHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    /* Retrofit */
    @Singleton
    @Provides
    fun provideRetrofit(apiKeyInterceptor: ApiKeyInterceptor, languageInterceptor: LanguageInterceptor): Retrofit {
        val baseInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(baseInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(languageInterceptor)
            .build()

        return Retrofit.Builder().baseUrl(BuildConfig.ApiBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create API service
    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    /* Room */
    private val MOVIES_DATABASE_NAME = "movies_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao = db.getMovieDao()

    @Singleton
    @Provides
    fun provideGenresDao(db: MoviesDatabase): GenresDao = db.getGenreDao()

    /* Preferences */
    @Provides
    @Singleton
    fun providePreferencesHandler(@ApplicationContext app: Context): PreferencesHandler = PreferencesHandlerImpl(app)

}
