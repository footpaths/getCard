package vn.dhstudio.topinstall.model

/**
 * Created by horror on 12/14/16.
 */

class DeviceModel {

    var name: String? = null
    var icon: String? = null
    var point: String? = null
    var email: String? = null
    var idDevice: String? = null
    var idParams: String? = null
    var packageParams: String? = null



    constructor() {

    }
    constructor(name: String, icon: String, point: String, email: String, idDevice:String,idParams: String,packageParams: String) {
        this.name = name
        this.icon = icon
        this.point = point
        this.email = email
        this.idDevice = idDevice
        this.idParams = idParams
        this.packageParams = packageParams


    }


    override fun toString(): String {
        return "$name $icon $point $email$idDevice$idParams$packageParams"
    }
    //fsdfsd
}
