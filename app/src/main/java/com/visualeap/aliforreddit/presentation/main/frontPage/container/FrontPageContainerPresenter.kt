package com.visualeap.aliforreddit.presentation.main.frontPage.container

import com.visualeap.aliforreddit.domain.usecase.IsUserLoggedIn
import com.visualeap.aliforreddit.domain.util.applySchedulers
import com.visualeap.aliforreddit.domain.util.scheduler.SchedulerProvider
import com.visualeap.aliforreddit.presentation.di.FragmentScope
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@FragmentScope
class FrontPageContainerPresenter @Inject constructor(
    private val view: FrontPageContainerView,
    private val isUserLoggedIn: IsUserLoggedIn,
    private val schedulerProvider: SchedulerProvider
) {
    private val disposables = CompositeDisposable()

    fun start() {
        val disposable = isUserLoggedIn.execute(Unit)
            .applySchedulers(schedulerProvider)
            .subscribe(
                { if (it) view.showHomeScreen() else view.showLoginScreen() },
                { /*onError*/ })

        disposables.add(disposable)
    }

    fun stop() {
        disposables.clear()
    }
}