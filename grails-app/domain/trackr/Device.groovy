package trackr

class Device {

    // Nom du device
    String name

    // Identifiant sigfox
    String sigfoxId

    // Etat du device
    DeviceState deviceState

    // Famille de device
    DeviceFamily deviceFamily

    // Code unique permettant d'associer un device à son compte
    String code

    // Date de création en base
    Date dateCreated

    // Date de dernière modification en base
    Date lastUpdated

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
