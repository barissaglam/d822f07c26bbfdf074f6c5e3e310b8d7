package barissaglam.challenge.data.remote

import barissaglam.challenge.data.remote.model.SpaceStation
import retrofit2.http.POST

interface RemoteApiService {

    @POST("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceStations(): ArrayList<SpaceStation>
}