package cz.jaro.dopravnipodniky

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import cz.jaro.dopravnipodniky.migrations.BarakyNemajiBarvuMigration
import cz.jaro.dopravnipodniky.migrations.NoveKrizovatkyMigration
import cz.jaro.dopravnipodniky.migrations.NovySystemGeneratoruMigration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(module {
                single {
                    PreferenceDataStoreFactory.create(
                        migrations = listOf(
                            NovySystemGeneratoruMigration,
                            BarakyNemajiBarvuMigration,
                            NoveKrizovatkyMigration,
                        )
                    ) {
                        get<Context>().preferencesDataStoreFile("DopravniPodniky_DataStore")
                    }
                }
            })
            defaultModule()
        }
    }
}
