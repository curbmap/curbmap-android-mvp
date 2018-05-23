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

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.curbmap.android.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.curbmap.android.domain.Status.ERROR;
import static com.curbmap.android.domain.Status.LOADING;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public class NetworkState<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public NetworkState(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> NetworkState<T> success(@Nullable T data) {
        return new NetworkState<>(Status.SUCCESS, data, null);
    }

    /**
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> NetworkState<T> error(String msg, @Nullable T data) {
        return new NetworkState<>(ERROR, data, msg);
    }


    public static <T> NetworkState<T> loading(@Nullable T data) {
        return new NetworkState<>(LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NetworkState<?> networkState = (NetworkState<?>) o;

        if (status != networkState.status) {
            return false;
        }
        if (message != null ? !message.equals(networkState.message) : networkState.message != null) {
            return false;
        }
        return data != null ? data.equals(networkState.data) : networkState.data == null;
    }

    /**
     * Hash: Status, Message, Data
     * @return int
     */
    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NetworkState{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
