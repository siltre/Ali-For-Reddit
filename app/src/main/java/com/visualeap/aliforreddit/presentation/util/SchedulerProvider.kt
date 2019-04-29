package com.visualeap.aliforreddit.presentation.util

import io.reactivex.Scheduler

interface SchedulerProvider {
    val io: Scheduler
    val computation: Scheduler
    val ui: Scheduler
}