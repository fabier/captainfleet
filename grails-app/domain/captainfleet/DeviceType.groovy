package captainfleet

/**
 * Created by fabier on 30/04/15.
 */
class DeviceType extends BaseEntity {

    // Identifiant sigfox de type de device
    String sigfoxId

    static constraints = {
        sigfoxId nullable: true
    }
}
