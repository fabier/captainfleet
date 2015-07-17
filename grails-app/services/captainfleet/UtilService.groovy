package captainfleet

import grails.transaction.Transactional

@Transactional
class UtilService {

    int compareBaseEntity(BaseEntity a, BaseEntity b) {
        if (a.name == null) {
            if (b.name == null) {
                a.id - b.id
            } else {
                1
            }
        } else {
            if (b.name == null) {
                -1
            } else {
                a.name.compareTo(b.name)
            }
        }
    }

    def sortBaseEntities(List<BaseEntity> entities) {
        entities.sort { a, b ->
            compareBaseEntity(a, b)
        }
    }
}
