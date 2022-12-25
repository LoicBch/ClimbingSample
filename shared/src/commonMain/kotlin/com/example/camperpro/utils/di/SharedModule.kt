package com.example.camperpro.utils.di

import com.example.camperpro.data.datasources.local.SearchDaoDelight
import com.example.camperpro.data.datasources.local.dao.SearchDao
import com.example.camperpro.data.datasources.remote.Api
import com.example.camperpro.data.datasources.remote.CamperProApi
import com.example.camperpro.data.repositories.*
import com.example.camperpro.database.CamperproDatabase
import com.example.camperpro.domain.repositories.*
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import com.example.camperpro.domain.usecases.*
import com.example.camperpro.utils.Constants
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.util.date.*
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

fun sharedModule() = listOf(apiDependency, useCasesDependencies, repositoriesDependencies)


val apiDependency = module {
    singleOf<Api> {
        CamperProApi(HttpClient {

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
            }

            install(ContentNegotiation) {
                json(DefaultJson, ContentType.Any)
            }

            defaultRequest {
                url(Constants.BASE_URL)
            }
        })
    }
}

val repositoriesDependencies = module {
    singleOf(::Ads) { bind<AdRepository>() }
    singleOf(::AllNews) { bind<NewsRepository>() }
    singleOf(::Spots) { bind<SpotRepository>() }
    singleOf(::CheckLists) { bind<CheckListRepository>() }
    singleOf(::Searches) { bind<SearchesRepository>() }
}

val useCasesDependencies = module {
    factoryOf(::FetchSpotAtLocationUseCase)
    factoryOf(::FetchAds)
    factoryOf(::FetchNews)
    factoryOf(::FetchCheckLists)
    factoryOf(::SetupApp)
    factoryOf(::GetAllSearchForACategory)
    factoryOf(::AddSearch)
    factoryOf(::DeleteSearch)
}

//val utilityDependencies = module {
//    factoryOf(::KMMCalendar)
//    factoryOf(::KMMPreference)
//}
