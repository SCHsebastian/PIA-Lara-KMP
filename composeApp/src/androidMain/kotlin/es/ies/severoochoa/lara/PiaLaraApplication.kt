package es.ies.severoochoa.lara

import android.app.Application
import es.ies.severoochoa.lara.di.initKoin

class PiaLaraApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

}