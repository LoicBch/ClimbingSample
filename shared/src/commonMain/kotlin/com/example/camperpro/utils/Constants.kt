package com.example.camperpro.utils

object Constants {

    object API {
        const val DEALERS = "dealers.php"
        const val STARTER = "app_general.php"
        const val PARTNERS = "partners.php"
        const val ADS = "ads.php"
    }

    object PreferencesKey {
        const val LAST_SETUP_TIMESTAMP = "LAST_SETUP_TIMESTAMP"
    }

    //enum
    object Persistence {
        const val SEARCH_CATEGORY_FILTER_SERVICE = "SEARCH_CATEGORY_FILTER_SERVICE"
        const val SEARCH_CATEGORY_FILTER_BRANDS = "SEARCH_CATEGORY_FILTER_BRANDS"
        const val SEARCH_CATEGORY_FILTER_ACCESSORIES = "SEARCH_CATEGORY_FILTER_ACCESSORIES"
    }

    const val LOCATION_UPDATE_RATE: Long = 3000
    const val RADIUS_AROUND_LIMIT = 5
    const val WEBSERVICE_VERSION = "v4"
    const val BASE_URL = "https://camper-pro.com/services/$WEBSERVICE_VERSION/"
}