package captainfleet

/**
 * http://stackoverflow.com/questions/2110055/removefrom-not-working-and-with-no-errors
 */
abstract class BaseDomain {

    // Date de création en base
    Date dateCreated

    // Date de dernière modification en base
    Date lastUpdated

    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null) return false
        // hibernate creates dynamic subclasses, so
        // checking o.class == class would fail most of the time
        if (!o.getClass().isAssignableFrom(getClass()) &&
                !getClass().isAssignableFrom(o.getClass())) return false

        if (ident() != null) {
            ident() == o.ident()
        } else {
            false
        }
    }

    @Override
    int hashCode() {
        ident()?.hashCode() ?: 0
    }
}