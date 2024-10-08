package com.horionDev.climbingapp.managers.location

typealias OnLocationUpdatedBlock = (location: LocationData) -> Unit
typealias OnLocationUnavailableBlock = () -> Unit
typealias OnPermissionUpdatedBlock = (isGranted: Boolean) -> Unit

interface BaseLocationManagerCompanion {

    fun currentLocation(block: OnLocationUpdatedBlock)
    fun startLocationUpdating()
    fun stopLocationUpdating()
    fun isLocationEnable(): Boolean
    fun isLocationObserved(): Boolean
    fun onLocationUpdated(target: Any, block: OnLocationUpdatedBlock): BaseLocationManagerCompanion
    fun onLocationUnavailable(
        target: Any,
        block: OnLocationUnavailableBlock
    ): BaseLocationManagerCompanion

    fun isPermissionAllowed(): Boolean
    fun requestPermission()
    fun onPermissionUpdated(
        target: Any,
        block: OnPermissionUpdatedBlock
    ): BaseLocationManagerCompanion

    fun removeAllListeners()
    fun removeListeners(target: Any)
    fun removeOnLocationUnavailable(target: Any)
    fun removeOnLocationUpdated(target: Any)
    fun removeOnPermissionUpdated(target: Any)

}