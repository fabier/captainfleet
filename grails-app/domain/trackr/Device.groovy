package trackr

class Device extends BaseEntity {

    // Identifiant sigfox
    String sigfoxId

    // Etat du device
    DeviceState deviceState

    // Famille de device
    DeviceFamily deviceFamily

    // Code unique permettant d'associer un device à son compte
    String code

    static hasMany = [frames: Frame]

    static constraints = {
        name nullable: true
        sigfoxId nullable: false, unique: true
        dateCreated nullable: true
        lastUpdated nullable: true
        deviceState nullable: true
        code nullable: true
        deviceFamily nullable: true
    }
}
