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

    /**
     * Position de l'ancre pour ce marqueur, en pourcentage de la largeur de l'image
     * (0.0 = gauche de l'image, 1.0 = droite de l'image)
     */
    Float anchorX = 0.5

    /**
     * Position de l'ancre pour ce marqueur, en pourcentage de la hauteur de l'image
     * (0.0 = haut de l'image, 1.0 = bas de l'image)
     */
    Float anchorY = 1.0

    /**
     * Indique si cette icone est l'icone utilisée par défaut dans le cas où le device n'a pas encore d'icone
     */
    Boolean isDefault = false

    static constraints = {
        name nullable: true
        filename nullable: true
        data nullable: true
        anchorX nullable: true
        anchorY nullable: true
        isDefault nullable: true
    }
}
