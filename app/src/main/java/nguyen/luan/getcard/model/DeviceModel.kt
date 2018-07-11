package nguyen.luan.getcard.model

import java.util.ArrayList

/**
 * Created by horror on 12/14/16.
 */

class DeviceModel {

    var name: String? = null
    var icon: String? = null
    var point: String? = null
    var status: String? = null



    constructor() {

    }
    constructor(name: String, icon: String, point: String, status: String) {
        this.name = name
        this.icon = icon
        this.point = point
        this.status = status


    }


    override fun toString(): String {
        return "$name $icon $point $status"
    }
    //fsdfsd
}
