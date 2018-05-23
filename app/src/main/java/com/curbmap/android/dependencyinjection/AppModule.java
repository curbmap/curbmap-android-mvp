/*
 * Copyright ${YEAR} ${PROJECT_NAME}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.curbmap.android.dependencyinjection;

import com.curbmap.android.api.CurbmapService;
import com.curbmap.android.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class})
 abstract class AppModule {
    @Singleton @Provides
    static CurbmapService provideCurbmapService(){
        return new Retrofit.Builder()
                .baseUrl("https://curbmap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(CurbmapService.class);
    }

    /*
    @Singleton
    @Provides
    CurbmapDb provideDb(app: Application) {
        return Room
                .databaseBuilder(app, CurbmapDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    UserDao provideUserDao(db: CurbmapDb) {
        return db.userDao()
    }

    @Singleton
    @Provides
    DataDao provideRepoDao(db: CurbmapDb) {
        return db.repoDao()
    }*/
}
