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

package com.curbmap.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.curbmap.android.repository.CacheFileRepository;
import com.google.openlocationcode.OpenLocationCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Joshua Herman.
 * Copyright 2018 Curbmap
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

//TODO: Implement images saving to Cache file location
public class CameraViewModel extends ViewModel {

    MutableLiveData<CacheFileRepository> cacheFileRepository;

    //CacheFileRepository cacheFileRepository;
    private static final String TAG = "CameraViewModel";

    OpenLocationCode locationCode;
    //ExifInterface exifInterface;
    byte[] imageJpg;
    FileOutputStream outputStream;
    int count = 0;

    SimpleDateFormat simpleDateFormat;



    public void setImage(Bitmap bitmap){

    }

    public void setImage(byte[] jpeg){

    }

    public void setCacheDirectoryRoot(File root){

    }



    //TODO: Set Date As File Name.
    private void writeToCacheDir(byte[] input, String filename){
        String PATH;
        simpleDateFormat = new SimpleDateFormat("yyyymmdd-hhmmss");

        /*Checks for External Storage before writing.*/
        if(Environment.isExternalStorageEmulated()){

            PATH = Environment.getExternalStorageDirectory().getPath();
        }else {
            PATH = null;//getCacheDir().getPath();
        }


        File cacheImages = new File( PATH, "images");
        cacheImages.mkdirs();
        File image = new File( cacheImages, simpleDateFormat.getCalendar().getTime().toString() + ".jpg");

        Log.d(TAG, "Cache Location: " + image.getParentFile().getAbsolutePath());
        try {
            if(image.exists()){
                image.delete();
                image.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(image);
            outputStream.write(input);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private byte[] compressToJpeg(Bitmap in){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onClear");
    }
}
