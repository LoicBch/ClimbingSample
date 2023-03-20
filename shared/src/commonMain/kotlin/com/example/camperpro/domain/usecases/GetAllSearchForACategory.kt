package com.example.camperpro.domain.usecases

import com.example.camperpro.data.repositories.Searches
import com.example.camperpro.domain.model.Search
import com.example.camperpro.data.ResultWrapper
import com.example.camperpro.domain.repositories.SearchesRepository

class GetAllSearchForACategory(private val searches: SearchesRepository) : IBaseUsecase {
    suspend operator fun invoke(searchCategoryKey: String): ResultWrapper<List<Search>> {
        return ResultWrapper.Success(
            searches.allOfCategory(searchCategoryKey)!!
                .sortedByDescending { it.timeStamp })
    }
}