package captainfleet

/**
 * Created by fabier on 25/04/15.
 */
abstract class BaseEntity extends BaseDomain {

    // Nom de l'entité
    String name

    // Description de l'entité
    String description

    // Créateur de cette entrée en base
    User creator

    static constraints = {
        name nullable: true
        description nullable: true
        creator nullable: true
    }

    static mapping = {
        name type: 'text'
        description type: 'text'
    }
}
