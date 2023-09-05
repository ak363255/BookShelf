package com.example.bookshelf.data

import com.example.bookscoremodule.data.CountryListDto
import com.example.bookscoremodule.data.toCountry
import com.example.bookscoremodule.domain.Country
import com.example.bookscoremodule.utils.UrlConstants
import com.example.bookshelf.domain.repository.GetCountryRepo
import com.example.bookshelf.domain.utils.LocalResourceLoaderUtil
import com.example.foodrecipes.domain.utils.BookResourceUtils
import com.example.network.NetworkHelper
import com.example.network.NetworkResponse
import com.example.network.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ImplGetCountryRepo:GetCountryRepo {
    override suspend fun getCountryList(): Flow<ResultEvent<List<Country>>>  = flow{
      /*  val url = UrlConstants.GET_COUNTRY_LIST
        val data = NetworkHelper.makeGetRequest<CountryListDto>(url)
        when(data){
            is NetworkResponse.ApiError -> {
                emit(ResultEvent.OnFailure("Something went wrong!"))
            }
            is NetworkResponse.NetworkError -> {
                emit(ResultEvent.OnFailure("Something went wrong!"))
            }
            is NetworkResponse.Success -> {
                val countries = data.body.toCountry()
                emit(ResultEvent.OnSuccess(countries))
            }
            is NetworkResponse.UnknownError -> {
                emit(ResultEvent.OnFailure("Something went wrong!"))
            }

        }
        */
        val countryList = BookResourceUtils.loadCountryList()
        emit(ResultEvent.OnSuccess(countryList))

    }
}