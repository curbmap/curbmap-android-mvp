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

package com.curbmap.android.model;

import android.location.Location;
import android.support.media.ExifInterface;
import android.util.Log;

import com.google.openlocationcode.OpenLocationCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ${EMAIL} on 4/17/18.
 */

//TODO: W/ExifInterface: Skip the tag entry since tag number is not defined ???
public class Image {

    private static final String TAG = "Image";

    android.media.Image imageNative;

    private File imageFile;
    private byte[] imageByte;
    ExifInterface exifInterface;
    OpenLocationCode openLocationCode;


    public Image(){}

    public Image(File image){
        this.imageFile = image;
    }

   /* public byte[] getImage(){

    }

    @TargetApi(26)
    @NonNull
    public byte[] getImage() {
        if(imageByte != null){
            return imageByte;
        }
        byte[] holder;
        try {
            holder = Files.readAllBytes(imageFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return holder;
    }*/

    public void setImage(File imageCacheDirectory, final byte[] image, Location location, float azimuth) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    FileOutputStream imageTempFile = new FileOutputStream();
                    imageTempFile.write(image);
                    imageTempFile.close();
                    new File(imageCacheDirectory, imageTempFile.getFD().)
                    if(!imageTempFile.setWritable(true)){
                        throw new IOException();
                    }
                    exifInterface = new ExifInterface(imageFile.getName());
                    double[] latlng = exifInterface.getLatLong();
                    setOpenLocationCode(
                            new OpenLocationCode(latlng[0],latlng[1], OpenLocationCode.CODE_PRECISION_EXTRA)
                    );

                    exifInterface.setAttribute("olc", getOpenLocationCode().getCode());
                    exifInterface.saveAttributes();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });*/

    }

    public void setImage(final File image){


        try {
            imageFile = File.createTempFile(setNewFileName(), ".jpg", image);
            //imageFile.setWritable(true);
            if(!imageFile.setWritable(true)){
                throw new IOException();
            }
            exifInterface = new ExifInterface(imageFile.getName());
            double[] latlng = exifInterface.getLatLong();
            setOpenLocationCode(
                new OpenLocationCode(latlng[0],latlng[1], OpenLocationCode.CODE_PRECISION_EXTRA)
            );

            exifInterface.setAttribute("olc", getOpenLocationCode().getCode());
            exifInterface.saveAttributes();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }


    }

    private String getDate(){
        String timeStamp;
        return timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
    }

    public String setNewFileName() {
        return ("IMG_" + getDate());
    }

    private void setOpenLocationCode(OpenLocationCode openLocationCode){
        this.openLocationCode = openLocationCode;
    }

    public OpenLocationCode getOpenLocationCode(){
        return this.openLocationCode;
    }
}
