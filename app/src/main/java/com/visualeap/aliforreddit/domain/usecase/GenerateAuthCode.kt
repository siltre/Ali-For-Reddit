package com.visualeap.aliforreddit.domain.usecase

import com.visualeap.aliforreddit.domain.usecase.base.NonReactiveUseCase
import java.math.BigInteger
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenerateAuthCode @Inject constructor() : NonReactiveUseCase<String, Unit> {

    private val code = BigInteger(128, SecureRandom()).toString(32)

    override fun execute(params: Unit): String {
        return code
    }
}
