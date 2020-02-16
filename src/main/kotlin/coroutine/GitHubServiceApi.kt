package coroutine

import kotlinx.coroutines.Deferred
import retrofit2.http.Path

interface GitHubServiceApi {
    fun getUserCoroutine(@Path("login") login: String): Deferred<User>
}
