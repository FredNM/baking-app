package com.frednm.baking_app.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.frednm.baking_app.data.local.BakingAppDatabase;
import com.frednm.baking_app.data.local.RecipeDao;
import com.frednm.baking_app.data.remote.BakingDataSource;
import com.frednm.baking_app.data.remote.BakingService;
import com.frednm.baking_app.data.repository.BakingRepository;
import com.frednm.baking_app.features.detail.domain.GetRecipeUseCase;
import com.frednm.baking_app.features.home.domain.GetRecipesUseCase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---
    @Provides
    @Singleton
    BakingAppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                BakingAppDatabase.class, "MovieAppDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    RecipeDao provideMovieDao(BakingAppDatabase database) { return database.recipeDao(); }

    // --- THE SHORT NETWORK REQUEST ---
    private static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/"; // Useless ! Just to give something
    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return okHttpClient;
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    BakingService provideBakingService(Retrofit restAdapter) {
        return restAdapter.create(BakingService.class);
    }

    @Provides
    BakingDataSource provideBakingDatasource(BakingService bakingService) {
        return new BakingDataSource(bakingService);
    }

    // --- REPOSITORY INJECTIONS ---
    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    BakingRepository provideBakingRepository(BakingDataSource bakingDataSource, RecipeDao recipeDao, Context context) {
        return new BakingRepository(bakingDataSource, recipeDao, context);
    }

    // --- DOMAIN INJECTIONS ---
    @Provides
    GetRecipesUseCase provideGetRecipesUseCase(BakingRepository bakingRepository) {
        return new GetRecipesUseCase(bakingRepository);
    }

    @Provides
    GetRecipeUseCase provideGetRecipeUseCase(BakingRepository bakingRepository) {
        return new GetRecipeUseCase(bakingRepository);
    }
}

