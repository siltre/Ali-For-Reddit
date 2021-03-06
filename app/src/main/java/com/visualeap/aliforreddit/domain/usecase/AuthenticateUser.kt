package com.visualeap.aliforreddit.domain.usecase

import com.visualeap.aliforreddit.domain.model.Account
import com.visualeap.aliforreddit.domain.repository.AccountRepository
import com.visualeap.aliforreddit.domain.repository.RedditorRepository
import com.visualeap.aliforreddit.domain.repository.TokenRepository
import com.visualeap.aliforreddit.domain.usecase.AuthenticateUser.*
import com.visualeap.aliforreddit.domain.usecase.base.CompletableUseCase
import dagger.Reusable
import io.reactivex.*
import okhttp3.HttpUrl
import java.net.MalformedURLException
import javax.inject.Inject

@Reusable
class AuthenticateUser @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val accountRepository: AccountRepository,
    private val redditorRepository: RedditorRepository
) : CompletableUseCase<Params> {

    override fun execute(params: Params): Completable {

        val parsedFinalUrl = HttpUrl.parse(params.finalUrl)
            ?: return Completable.error(MalformedURLException())

        parsedFinalUrl.queryParameter("error")
            ?.let { return Completable.error(OAuthException("Reddit responded with error: $it")) }

        parsedFinalUrl.queryParameter("state")
            ?.let { stateReceived ->
                if (params.state != stateReceived)
                    return Completable.error(IllegalStateException("State doesn't match"))
            }
            ?: return Completable.error(IllegalArgumentException("Final redirect URL did not contain the 'state' query parameter"))


        val code = parsedFinalUrl.queryParameter("code")
            ?: return Completable.error(IllegalArgumentException("Final redirect URL did not contain the 'code' query parameter"))

        //Fetch user token, set it as the current token, and use it to fetch current redditor.
        // Use redditor username to create a new Account.
        return tokenRepository.getUserToken(code)
            .flatMapCompletable { token ->
                tokenRepository.setCurrentToken(token)
                    /*.andThen(redditorRepository.getCurrentRedditor())
                    .flatMapCompletable { redditor ->
                        accountRepository.addAccount(
                            Account(redditor.username)
                        )
                    }*/
            }
    }

    data class Params(
        val finalUrl: String,
        val state: String
    )
}