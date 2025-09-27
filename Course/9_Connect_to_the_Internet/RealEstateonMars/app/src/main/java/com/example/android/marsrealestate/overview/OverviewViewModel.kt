/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch


enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: LiveData<MarsProperty?>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            val getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)

            try {
                val listResult =  getPropertiesDeferred.toList()

                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}

/** Refactor: 7 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android.marsrealestate.network.MarsApi
//import com.example.android.marsrealestate.network.MarsProperty
//import kotlinx.coroutines.launch
//
//
//enum class MarsApiStatus { LOADING, ERROR, DONE }
//
//class OverviewViewModel : ViewModel() {
//
//    private val _status = MutableLiveData<MarsApiStatus>()
//
//    val status: LiveData<MarsApiStatus>
//        get() = _status
//
//    private val _properties = MutableLiveData<List<MarsProperty>>()
//    val properties: LiveData<List<MarsProperty>>
//        get() = _properties
//
//    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
//    val navigateToSelectedProperty: LiveData<MarsProperty?>
//        get() = _navigateToSelectedProperty
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        viewModelScope.launch {
//            _status.value = MarsApiStatus.LOADING
//            val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
//
//            try {
//                val listResult =  getPropertiesDeferred.toList()
//
//                _status.value = MarsApiStatus.DONE
//                _properties.value = listResult
//            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
//                _properties.value = ArrayList()
//            }
//        }
//    }
//
//    fun displayPropertyDetails(marsProperty: MarsProperty) {
//        _navigateToSelectedProperty.value = marsProperty
//    }
//
//    fun displayPropertyDetailsComplete() {
//        _navigateToSelectedProperty.value = null
//    }
//}

/** Refactor: 6 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android.marsrealestate.network.MarsApi
//import com.example.android.marsrealestate.network.MarsProperty
//import kotlinx.coroutines.launch
//
//enum class MarsApiStatus { LOADING, ERROR, DONE }
//
//class OverviewViewModel : ViewModel() {
//
//    private val _status = MutableLiveData<MarsApiStatus>()
//
//    val status: LiveData<MarsApiStatus>
//        get() = _status
//
//    private val _properties = MutableLiveData<List<MarsProperty>>()
//    val properties: LiveData<List<MarsProperty>>
//        get() = _properties
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        viewModelScope.launch {
//            _status.value = MarsApiStatus.LOADING
//            val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
//
//            try {
//                val listResult =  getPropertiesDeferred.toList()
//
//                _status.value = MarsApiStatus.DONE
//                _properties.value = listResult
//            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
//                _properties.value = ArrayList()
//            }
//        }
//    }
//}

/** Refactor: 5 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android.marsrealestate.network.MarsApi
//import com.example.android.marsrealestate.network.MarsProperty
//import kotlinx.coroutines.launch
//
//class OverviewViewModel : ViewModel() {
//
//    private val _status = MutableLiveData<String>()
//
//    val status: LiveData<String>
//        get() = _status
//
//    private val _properties = MutableLiveData<List<MarsProperty>>()
//    val properties: LiveData<List<MarsProperty>>
//        get() = _properties
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        viewModelScope.launch {
//            val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
//
//            try {
//                val listResult = getPropertiesDeferred.toList()
//                _properties.value = listResult
//            } catch (e: Exception) {
//                _status.value = "Failure: ${e.message}"
//            }
//        }
//    }
//}

/** Refactor: 4 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android.marsrealestate.network.MarsApi
//import com.example.android.marsrealestate.network.MarsProperty
//import kotlinx.coroutines.launch
//
//class OverviewViewModel : ViewModel() {
//
//    private val _status = MutableLiveData<String>()
//
//    val status: LiveData<String>
//        get() = _status
//
//    private val _property = MutableLiveData<MarsProperty>()
//    val property: LiveData<MarsProperty>
//        get() = _property
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        viewModelScope.launch {
//            val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
//
//            try {
//                val listResult = getPropertiesDeferred.toList()
//
//                if (listResult.isNotEmpty()) {
//                    _property.value = listResult[0]
//                }
//            } catch (e: Exception) {
//                _status.value = "Failure: ${e.message}"
//            }
//        }
//    }
//}

/** Refactor: 3 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android.marsrealestate.network.MarsApi
//import kotlinx.coroutines.launch
//
//class OverviewViewModel : ViewModel() {
//
//    private val _response = MutableLiveData<String>()
//
//    val response: LiveData<String>
//        get() = _response
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        viewModelScope.launch {
//            val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
//
//            try {
//                val listResult = getPropertiesDeferred.toList()
//                _response.value = "Success: ${listResult.size} Mars properties retrieved"
//            } catch (e: Exception) {
//                _response.value = "Failure: ${e.message}"
//            }
//        }
//    }
//}

/** Refactor: 2 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.android.marsrealestate.network.MarsApi
//import com.example.android.marsrealestate.network.MarsProperty
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class OverviewViewModel : ViewModel() {
//
//    private val _response = MutableLiveData<String>()
//
//    val response: LiveData<String>
//        get() = _response
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        MarsApi.retrofitService.getProperties().enqueue( object: Callback<List<MarsProperty>> {
//            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//
//            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
//                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
//            }
//        })
//    }
//}

/** Refactor: 1 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.android.marsrealestate.network.MarsApi
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class OverviewViewModel : ViewModel() {
//
//    private val _response = MutableLiveData<String>()
//
//    val response: LiveData<String>
//        get() = _response
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        MarsApi.retrofitService.getProperties().enqueue( object: Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                _response.value = response.body()
//            }
//        })
//    }
//}

/** Refactor: 0 **/

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//class OverviewViewModel : ViewModel() {
//
//    private val _response = MutableLiveData<String>()
//
//    val response: LiveData<String>
//        get() = _response
//
//    init {
//        getMarsRealEstateProperties()
//    }
//
//    private fun getMarsRealEstateProperties() {
//        _response.value = "Set the Mars API Response here!"
//    }
//}
