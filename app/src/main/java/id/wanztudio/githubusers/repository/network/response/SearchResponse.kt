package id.wanztudio.githubusers.repository.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
data class SearchResponse(
    @SerializedName("items")
    val users: List<UserSearchResponse>?,
    @SerializedName("total_count")
    val totalCount: Int?
)