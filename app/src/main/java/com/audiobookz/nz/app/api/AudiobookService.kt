package com.audiobookz.nz.app.api
import com.audiobookz.nz.app.audiobookList.data.AudiobookList
import com.audiobookz.nz.app.browse.categories.data.Category
import com.audiobookz.nz.app.login.data.UserData
import retrofit2.Response
import retrofit2.http.*

/**
 * Lego REST API access points
 */
interface AudiobookService {

    companion object {
        const val ENDPOINT = "https://audiobooksnz.co.nz/backend/v1/"
    }

    @GET("categories")
    suspend fun getCategory(
        @Query("expand") expand: String? = null,
        @Query("page") page: Int? = null,
        @Query("per-page") pageSize: Int? = null,
        @Query("filter[parent_id]") filter: Int? = 0
    ): Response<List<Category>>

    @GET("audiobooks")
    suspend fun getAudiobooksList(
        @Query("filter[category_id]") filter: Int? =null,
        @Query("page") page: Int? = null,
        @Query("per-page") pageSize: Int? = null,
        @Query("filter[language][]") pageLanguage:String
        ): Response<List<AudiobookList>>

    @FormUrlEncoded
    @POST("users/login")
    suspend fun postEmailLogin(
        @Field("username") Username: String? ="test@gmail.com",
        @Field("password") Password: String? ="Welcome1"
    ): Response<UserData>

//    @GET("lego/sets/")
//    suspend fun getSets(@Query("page") page: Int? = null,
//                        @Query("page_size") pageSize: Int? = null,
//                        @Query("theme_id") themeId: Int? = null,
//                        @Query("ordering") order: String? = null): Response<ResultsResponse<LegoSet>>
//
//    @GET("lego/sets/{id}/")
//    suspend fun getSet(@Path("id") id: String): Response<LegoSet>

}
