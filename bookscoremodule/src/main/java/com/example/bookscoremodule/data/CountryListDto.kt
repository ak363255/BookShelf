package com.example.bookscoremodule.data

import androidx.annotation.Keep
import com.example.bookscoremodule.domain.Country
import com.google.gson.annotations.SerializedName
@Keep
data class CountryListDto(
    @SerializedName("status")
    val status:String,
    @SerializedName("status-code")
    val statuscode:Int,
    @SerializedName("data")
    val data:HashMap<String,CountryDto>
){
    data class CountryDto(
        @SerializedName("country")
        val country:String,
        @SerializedName("region")
        val region:String,
    )


}
fun CountryListDto.toCountry():List<Country>{
    val countries:MutableList<Country> = mutableListOf()
    this.data.entries.forEach {entry ->
        countries.add(Country(
            country = entry.value.country,
            region = entry.value.region,
            countryCode = entry.key
        ))
    }
    return countries
}
