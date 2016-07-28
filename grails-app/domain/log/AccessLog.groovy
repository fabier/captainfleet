package log

import captainfleet.BaseDomain

class AccessLog extends BaseDomain {

    String ip
    String path
    String queryString
    String controller
    String action

    static constraints = {
        queryString nullable: true
        controller nullable: true
        action nullable: true
    }
}
