/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.utilities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantRepository
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModelFactory
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(AppDatabase.getInstance(context).plantDao())
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
                AppDatabase.getInstance(context).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModel(activity: FragmentActivity) =
        GardenPlantingListViewModelFactory(getGardenPlantingRepository(activity.applicationContext)).run {
            ViewModelProviders.of(activity, this).get(GardenPlantingListViewModel::class.java)
        }

    fun providePlantListViewModel(activity: FragmentActivity) =
        PlantListViewModelFactory(getPlantRepository(activity.applicationContext)).run {
            ViewModelProviders.of(activity, this).get(PlantListViewModel::class.java)
        }

    fun providePlantDetailViewModel(activity: FragmentActivity, plantId: String) =
        PlantDetailViewModelFactory(
            getPlantRepository(activity.applicationContext),
            getGardenPlantingRepository(activity.applicationContext),
            plantId
        ).run {
            ViewModelProviders.of(activity, this).get(PlantDetailViewModel::class.java)
        }
}
