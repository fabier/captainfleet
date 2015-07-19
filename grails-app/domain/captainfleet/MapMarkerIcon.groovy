package captainfleet

class MapMarkerIcon extends BaseDomain {

    /**
     * Nom de l'icone
     */
    String name

    /**
     * Nom du fichier uploadé initialement
     */
    String filename

    /**
     * Données de l'image au format PNG (obligatoire pour un marqueur de carte)
     */
    byte[] data

    static constraints = {
        name nullable: true
        filename nullable: true
        data nullable: true
    }
}
