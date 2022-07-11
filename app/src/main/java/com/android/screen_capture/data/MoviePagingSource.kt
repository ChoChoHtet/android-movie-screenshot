package com.android.screen_capture.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.screen_capture.model.Movie
import com.android.screen_capture.network.MovieService
import com.android.screen_capture.utils.API_KEY
import com.android.screen_capture.utils.searchTitle
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_KEY = 1
 const val NETWORK_PAGE_SIZE = 10

class MoviePagingSource @Inject constructor(
    private val movieService: MovieService
) : PagingSource<Int, Movie>() {
    /**
     * The refresh key is used for subsequent refresh calls to PagingSource.load
     * after the initial load
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * function will be called by the Paging library to asynchronously
     * fetch more data to be displayed as the user scrolls around
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_KEY
        return try {
            val movieResponse = movieService.getMovieList(searchTitle, page, API_KEY)
            val movieList = movieResponse.movies
            val nextKey = if (movieList.isNullOrEmpty()){
                null
            }else page +1

            LoadResult.Page(
                data = movieList.orEmpty(),
                prevKey = if (page == STARTING_KEY) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}