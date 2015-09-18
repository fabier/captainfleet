package captainfleet

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class UserController extends grails.plugin.springsecurity.ui.UserController {

    def search() {
        def results = User.createCriteria().list {
            order("username", "asc")
            maxResults(10)
        }
        [results: results, totalCount: User.count(), searched: true]
    }

    def delete(long id) {
        User user = User.get(id)
        if (user != null) {
            UserDevice.removeAll(user)
            UserRole.removeAll(user)
            UserZone.removeAll(user)
            user.delete()
        }
        redirect action: "userSearch", params: params
    }

    def toggleEnabled(long id) {
        User user = User.get(id)
        if (user != null) {
            user.enabled = !user.enabled
            user.save()
        }
        redirect action: "userSearch", params: params
    }

    def userSearch() {

        boolean useOffset = params.containsKey('offset')
        setIfMissing 'max', 10, 100
        setIfMissing 'offset', 0

        def hql = new StringBuilder('FROM ').append(lookupUserClassName()).append(' u WHERE 1=1 ')
        def queryParams = [:]

        def userLookup = SpringSecurityUtils.securityConfig.userLookup
        String usernameFieldName = userLookup.usernamePropertyName

        for (name in [username: usernameFieldName]) {
            if (params[name.key]) {
                hql.append " AND LOWER(u.${name.value}) LIKE :${name.key}"
                queryParams[name.key] = params[name.key].toLowerCase() + '%'
            }
        }

        String enabledPropertyName = userLookup.enabledPropertyName
        String accountExpiredPropertyName = userLookup.accountExpiredPropertyName
        String accountLockedPropertyName = userLookup.accountLockedPropertyName
        String passwordExpiredPropertyName = userLookup.passwordExpiredPropertyName

        for (name in [enabled        : enabledPropertyName,
                      accountExpired : accountExpiredPropertyName,
                      accountLocked  : accountLockedPropertyName,
                      passwordExpired: passwordExpiredPropertyName]) {
            Integer value = params.int(name.key)
            if (value) {
                hql.append " AND u.${name.value}=:${name.key}"
                queryParams[name.key] = value == 1
            }
        }

        int totalCount = lookupUserClass().executeQuery("SELECT COUNT(DISTINCT u) $hql", queryParams)[0]

        Integer max = params.int('max')
        Integer offset = params.int('offset')

        String orderBy = ''
        if (params.sort) {
            orderBy = " ORDER BY u.$params.sort ${params.order ?: 'ASC'}"
        } else {
            orderBy = " ORDER BY u.dateCreated DESC"
        }

        def results = lookupUserClass().executeQuery(
                "SELECT DISTINCT u $hql $orderBy",
                queryParams, [max: max, offset: offset])
        def model = [results: results, totalCount: totalCount, searched: true]

        // add query params to model for paging
        for (name in ['username', 'enabled', 'accountExpired', 'accountLocked',
                      'passwordExpired', 'sort', 'order']) {
            model[name] = params[name]
        }

        render view: 'search', model: model
    }
}
