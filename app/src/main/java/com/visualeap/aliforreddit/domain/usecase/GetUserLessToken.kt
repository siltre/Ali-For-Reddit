package com.visualeap.aliforreddit.domain.usecase

import com.visualeap.aliforreddit.domain.model.token.UserlessToken
import com.visualeap.aliforreddit.domain.repository.TokenRepository
import com.visualeap.aliforreddit.domain.usecase.base.NonReactiveUseCase
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

@Reusable
class GetUserLessToken @Inject constructor(
    private val tokenRepository: TokenRepository,
    @Named("basicAuth") private val basicAuth: String
) :
    NonReactiveUseCase<UserlessToken?, String> {

    /**
     * @param params The device ID. The ID should be unique per-device.
     * You should retain and re-use the same device_id when renewing your access token
     */
    override fun execute(params: String): UserlessToken? {
        var token: UserlessToken? = null

        //Dispose immediately since it's a synchronous call
        tokenRepository.getUserLessToken(
            GRANT_TYPE,
            params,
            basicAuth
        ).subscribe({ token = it }, { /*Do nothing on error*/ }).dispose()

        return token?.copy(deviceId = params)
    }

    companion object {
        private const val GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client"
    }
}