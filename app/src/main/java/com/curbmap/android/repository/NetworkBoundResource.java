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
z */

package com.curbmap.android.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.curbmap.android.AppThreadingExecutors;
import com.curbmap.android.api.ApiResponse;
import com.curbmap.android.domain.NetworkState;

import java.util.Objects;


/**
 *
 * @param <ResultType> output Object
 * @param <RequestType> input Object
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppThreadingExecutors appThreadingExecutors;

    private final MediatorLiveData<NetworkState<ResultType>> resultLiveDataMediator = new MediatorLiveData<>();
    private final MutableLiveData<NetworkState<ResultType>> resultMutableLiveData = new MutableLiveData<>();
    // SEE: https://github.com/googlesamples/android-architecture-components/blob/5ec49a4bcdada748b4968138c4182deef3741123/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.java#L45
    @MainThread
    NetworkBoundResource(AppThreadingExecutors appThreadingExecutors) {
        //Setup MultiThreading
        this.appThreadingExecutors = appThreadingExecutors;

        resultLiveDataMediator.setValue(NetworkState.loading(null));

        LiveData<ResultType> dbSource = loadFromDb();

        resultLiveDataMediator.addSource(dbSource, data -> {
            resultLiveDataMediator.removeSource(dbSource);
            if (shouldFetchFromNetwork(data)) {
                fetchFromNetwork(dbSource);
            } else {
                resultLiveDataMediator.addSource(dbSource, newData -> setValue(NetworkState.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(NetworkState<ResultType> newValue) {
        if (!Objects.equals(resultLiveDataMediator.getValue(), newValue)) {
            resultLiveDataMediator.setValue(newValue);
        }
    }

    /**
     * Retrieve Data from Network Source
     *
     * @param dbSource
     */
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {

        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        resultLiveDataMediator.addSource(dbSource, newData -> setValue(NetworkState.loading(newData)));
        resultLiveDataMediator.addSource(apiResponse, (ApiResponse<RequestType> response) -> {
            resultLiveDataMediator.removeSource(apiResponse);
            resultLiveDataMediator.removeSource(dbSource);

            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appThreadingExecutors.diskIO().execute(() -> {

                    saveCallResult(processResponse(response));
                    appThreadingExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            resultLiveDataMediator.addSource(loadFromDb(),
                                    newData -> setValue(NetworkState.success(newData)))
                    );
                });
            } else {
                onFetchFailed();
                resultLiveDataMediator.addSource(dbSource,
                        newData -> setValue(NetworkState.error(response.errorMessage, newData)));
            }
        });
    }

    protected void onFetchFailed() {

    }

    public LiveData<NetworkState<ResultType>> asLiveData() {
        return resultLiveDataMediator;
    }


    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.of;
    }

    /**
     * Save Network call to database
     *
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    /**
     * <div>Receives Data and checks data for null </div>
     * Or Can just be set to boolean value.
     *
     * @param data
     * @return boolean
     */
    @MainThread
    protected abstract boolean shouldFetchFromNetwork(@Nullable ResultType data);

    @MainThread
    protected abstract boolean shouldLoadFromDb();

    /**
     * Loads Data From Room / Database upon implementation
     *
     * @return
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    /**
     * Use to retrieve data by adding functionality
     *
     * @return LiveData<ApiResponse<RequestType>>
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
