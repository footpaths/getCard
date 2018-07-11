package nguyen.luan.getcard.model

import java.util.ArrayList

/**
 * Created by horror on 12/14/16.
 */

class Emails {

     var device: ArrayList<DeviceModel>? = null

    constructor() {

    }

    constructor( device: ArrayList<DeviceModel> ) {
        this.device = device



    }

    override fun toString(): String {
        return "$device"
    }
    //fsdfsd
}
