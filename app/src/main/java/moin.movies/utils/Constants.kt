package moin.movies.utils

class Constants {

    private object Params {
        const val BASE_URL = "http://www.omdbapi.com/"
        const val ID = "i"
        const val SEARCH = "s"
        const val TYPE = "type"
        const val PLOT = "plot"
        const val PAGE = "page"
        const val API_KEY = "apikey"
    }

    private object Image {
        const val OLD_SIZE = "SX300"
        const val NEW_SIZE = "SX600"
    }

    companion object {

        const val BASE_URL = Params.BASE_URL
        const val imdbId = "imdbID"
        const val paramId = Params.ID
        const val paramSearch = Params.SEARCH
        const val paramType = Params.TYPE
        const val paramPlot = Params.PLOT
        const val paramPage = Params.PAGE
        const val paramApiKey = Params.API_KEY
        //const val apiKey = "45d162c8"
        const val apiKey = "ae94d30c"
        const val query = "query"
        const val MOVIE_FRAGMENT = "movie_fragment"
        const val MOVIE_LIST_FRAGMENT = "movie_list_fragment"
        const val WELCOME_FRAGMENT = "welcome_fragment"
        const val imageOldSize = Image.OLD_SIZE
        const val imageNewSize = Image.NEW_SIZE
        const val firstRun = "fistrun"
        const val NETWORK_CONNECTED = 1
        const val NETWORK_DESCONNECTED = 0
    }
}
