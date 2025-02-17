package com.ignmonlop.worldoftanks.mainModule.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignmonlop.worldoftanks.Retrofit.Common.Constants
import com.ignmonlop.worldoftanks.Retrofit.Data.Zone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ZoneViewModel: ViewModel() {
    private val _zones = MutableLiveData<List<Zone>>()
    val zones get() = _zones

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchZones() {
        viewModelScope.launch {
            try{
                val response = Constants.service.getZones()
                withContext(Dispatchers.Main){
                    _zones.value = response
                    Log.i("ZoneViewModel", "Response: $response")
                }
            } catch (e: HttpException) {
                _errorMessage.value = "Error en el servidor: ${e.code()} - ${e.message()}"
            } catch (e: IOException) {
                _errorMessage.value = "Error de conexión. Revisa tu internet."
            } catch (e: Exception) {
                _errorMessage.value = "Error desconocido: ${e.localizedMessage}"
            }
        }
    }
}