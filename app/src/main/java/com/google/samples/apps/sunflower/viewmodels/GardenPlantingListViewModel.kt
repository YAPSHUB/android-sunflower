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

package com.google.samples.apps.sunflower.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings

class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {

    private val gardenPlantings = gardenPlantingRepository.getGardenPlantings()
    private val _plantAndGardenPlantings = MediatorLiveData<List<PlantAndGardenPlantings>>()
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> = _plantAndGardenPlantings

    val hasPlantings = ObservableBoolean(false)
    val hasPlantAndGardenPlantings = ObservableBoolean(false)

    init {
        val livePlantAndGardenPlantings =
            Transformations.map(gardenPlantingRepository.getPlantAndGardenPlantings()) {
                it.filter { plantAndGardenPlantings -> plantAndGardenPlantings.gardenPlantings.isNotEmpty() }
            }

        _plantAndGardenPlantings.addSource(livePlantAndGardenPlantings) { list ->
            _plantAndGardenPlantings.value = list
            hasPlantAndGardenPlantings.set(true)
        }
    }

    fun checkGardenPlantings(owner: LifecycleOwner) {
        gardenPlantings.observe(owner, Observer { plantings ->
            hasPlantings.set(plantings != null && plantings.isNotEmpty())
        })
    }
}