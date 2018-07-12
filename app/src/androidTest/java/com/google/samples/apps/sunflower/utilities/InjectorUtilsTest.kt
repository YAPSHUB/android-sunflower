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

import android.support.test.rule.ActivityTestRule
import com.google.samples.apps.sunflower.GardenActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class InjectorUtilsTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(GardenActivity::class.java)

    @Test
    fun checkViewModelsSingletonInActivityScope() {
        with(InjectorUtils) {
            val viewModelOne = provideGardenPlantingListViewModel(activityTestRule.activity)
            val viewModelTwo = provideGardenPlantingListViewModel(activityTestRule.activity)
            assertEquals(viewModelOne, viewModelTwo)
        }
        with(InjectorUtils) {
            val viewModelOne = providePlantListViewModel(activityTestRule.activity)
            val viewModelTwo = providePlantListViewModel(activityTestRule.activity)
            assertEquals(viewModelOne, viewModelTwo)
        }
        with(InjectorUtils) {
            val viewModelOne = providePlantDetailViewModel(activityTestRule.activity, testPlant.plantId)
            val viewModelTwo = providePlantDetailViewModel(activityTestRule.activity, testPlant.plantId)
            assertEquals(viewModelOne, viewModelTwo)
        }
    }
}