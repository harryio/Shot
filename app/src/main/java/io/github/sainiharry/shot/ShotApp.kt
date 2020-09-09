package io.github.sainiharry.shot

import android.app.Application
import io.github.sainiharry.shot.network.UNSPLASH_API_KEY_QUALIFIER
import io.github.sainiharry.shot.network.networkModule
import io.github.sainiharry.shot.repository.photos.photoRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ShotApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ShotApp)

            modules(module {
                single(named(UNSPLASH_API_KEY_QUALIFIER)) {
                    BuildConfig.UNSPLASH_CLIENT_ID
                }
            }, networkModule, photoRepositoryModule)
        }
    }
}