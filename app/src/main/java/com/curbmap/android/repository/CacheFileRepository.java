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

import com.curbmap.android.model.Image;

import java.nio.file.Path;

public class CacheFileRepository implements Repository<Image>{

    @Override
    public void add(Image item) {

    }

    @Override
    public void add(Iterable<Image> items) {

    }

    @Override
    public void delete(Image name) {

    }

    @Override
    public void deleteAll(Iterable<Image> list) {

    }

    @Override
    public void update(Image item) {

    }

    @Override
    public void find(Image item) {

    }

    public void setDirectory(Image name){


    }

    private void delete(Path name){
        /*try {
        } catch (IOException e) {
            Log.e("CacheFileRepository", e.getMessage());
        }*/
    }

    public void deleteAll(String directory){

    }

    public void save(String name){

    }

    public void save(Image file){

    }

    public void update(String name, Image image){

    }
}
