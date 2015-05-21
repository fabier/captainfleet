package trackr

import grails.validation.Validateable

/**
 * Created by fabier on 21/05/15.
 */
@Validateable
class LatitudeLongitude {

    double latitude
    double longitude

    static constraints = {
        latitude nullable: false, min: -90.0d, max: 90.0d
        longitude nullable: false, min: -180.0d, max: 180.0d
    }
}
