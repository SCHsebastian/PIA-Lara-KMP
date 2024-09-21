package es.ies.severoochoa.lara.domain.core

import es.ies.severoochoa.lara.common.Context
import es.ies.severoochoa.lara.common.getData
import es.ies.severoochoa.lara.common.putData

const val APP_DATASTORE = "es.ies.severoochoa.lara.APP_DATASTORE"

class AppDataStoreManager(val context: Context) : AppDataStore {

    override suspend fun setValue(
        key: String,
        value: String
    ) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }
}