/*
 * Copyright (c) 2018. Curbmap - All rights reserved
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

package com.curbmap.android.repository;

import com.curbmap.android.AppThreadingExecutors;
import com.curbmap.android.api.CurbmapServiceFactory;

public class UserRepositoryFactory {

    private static final String TAG = "UserRepositoryFactory";

    public static UserRepository create(){
        return new UserRepository(CurbmapServiceFactory.create(), AppThreadingExecutors.init());
    }
}
