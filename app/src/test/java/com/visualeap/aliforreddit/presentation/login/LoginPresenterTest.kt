package com.visualeap.aliforreddit.presentation.login

import com.visualeap.aliforreddit.SyncSchedulerProvider
import com.visualeap.aliforreddit.domain.usecase.*
import com.visualeap.aliforreddit.presentation.main.login.LoginPresenter
import com.visualeap.aliforreddit.presentation.main.login.LoginView
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class LoginPresenterTest {

    companion object {
        private const val REDIRECT_URL = "https://example.com/path"
        private const val AUTH_URL = "https://www.reddit.com/api/v1/authorize"
        private const val CLIENT_ID = "CLIENT ID"
        private const val STATE = "STATE"
        private const val FINAL_REDIRECT_URL = "$REDIRECT_URL?state=$STATE"
    }

    private val view: LoginView = mockk(relaxed = true)
    private val generateAuthCode: GenerateAuthCode = mockk(relaxed = true)
    private val buildAuthUrl: BuildAuthUrl = mockk()
    private val authenticateUser: AuthenticateUser = mockk(relaxed = true)

    private val presenter = LoginPresenter(
        view,
        generateAuthCode,
        buildAuthUrl,
        authenticateUser,
        SyncSchedulerProvider(),
        REDIRECT_URL
    )

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Nested
    inner class OnLogInClicked {
        @Test
        fun `pass auth url to view`() {
            //Arrange
            every { generateAuthCode.execute(Unit) } returns STATE
            every { buildAuthUrl.execute(STATE) } returns AUTH_URL

            //Act
            presenter.onLogInClicked()

            //Assert
            verify { view.showLoginPage(AUTH_URL) }
        }

        @Test
        fun `hide login prompt`() {
            //Arrange
            every { generateAuthCode.execute(Unit) } returns STATE
            every { buildAuthUrl.execute(STATE) } returns AUTH_URL

            //Act
            presenter.onLogInClicked()

            //Assert
            verify { view.hideLoginPrompt() }
        }
    }

    @Nested
    inner class OnPageStarted {
        @Test
        fun `hide login ui when url is valid`() {
            //Act
            presenter.onPageStarted(FINAL_REDIRECT_URL)

            //Assert
            verify { view.hideLoginPage() }
        }

        @Test
        fun `keep login ui when url doesn't contain the redirect url`() {
            //Act
            presenter.onPageStarted("https://invalid.com")

            //Assert
            verify { view wasNot Called }
        }

        @Test
        fun `keep login ui when url doesn't contain any query`() {
            //Act
            presenter.onPageStarted(REDIRECT_URL)

            //Assert
            verify { view wasNot Called }
        }

        @Test
        fun `keep login ui when url is malformed`() {
            //Act
            presenter.onPageStarted("this is a malformed URL $REDIRECT_URL")

            //Assert
            verify { view wasNot Called }
        }

        @Test
        fun `reload UI when login is successful`() {
            //Act
            presenter.onPageStarted(FINAL_REDIRECT_URL)

            // Assert
            verify { view.reloadScreen() }
        }
    }
}