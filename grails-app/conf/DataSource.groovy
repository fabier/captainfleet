dataSource {
    pooled = true
    dbCreate = "update" // "validate"
    driverClassName = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/captainfleet"
    dialect = org.hibernatespatial.postgis.PostgisDialect
    username = "dev"
    password = "dev"
    properties {
        initialSize = 1
        maxActive = 16
        minIdle = 1
        maxIdle = 4
        maxWait = 10000
    }
//    logSql = true
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.SingletonEhCacheRegionFactory'
}

// environment specific settings
environments {
    development {
        dataSource {
        }
    }
    test {
        dataSource {
        }
    }
    production {
        dataSource {
            username = "grails"
            password = "P639rb98n4H3YD4E7q"
        }
    }
}
