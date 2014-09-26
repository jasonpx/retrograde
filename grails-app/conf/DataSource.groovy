dataSource {
    driverClassName = "org.postgresql.Driver"
    dialect = org.hibernate.dialect.PostgreSQLDialect
    loggingSql = false
    pooled = true
    url = System.env['JDBC_CONNECTION_STRING'] ?: System.properties['JDBC_CONNECTION_STRING'] ?: "jdbc:postgresql://localhost:5432/retrograde"
    username = System.env['JDBC_USERNAME'] ?: System.properties['JDBC_USERNAME'] ?: "postgres"
    password = System.env['JDBC_PASSWORD'] ?: System.properties['JDBC_PASSWORD'] ?: "postgres"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
            url = "jdbc:postgresql://localhost:5432/retrograde"
            username = "postgres"
            password = "postgres"
        }
    }
    old_development {
        dataSource {
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            //url = "jdbc:postgresql://localhost:5432/hacktrospective"
            //username = "postgres"
            //password = "postgres"
			//dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production {
      dataSource {
          dbCreate = "update"
          driverClassName = "org.postgresql.Driver"
          dialect = org.hibernate.dialect.PostgreSQLDialect

          uri = new URI(System.env.DATABASE_URL?:"postgres://test:test@localhost/test")

          url = "jdbc:postgresql://"+uri.host+uri.path
          username = uri.userInfo.split(":")[0]
          password = uri.userInfo.split(":")[1]
      }
    }
    production2 {
        dataSource {
            //dbCreate = "create-drop"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            //url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = "jdbc:postgresql://localhost:5432/retrograde"
            username = "postgres"
            password = "postgres"
            properties {
               // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
               jmxEnabled = true
               initialSize = 5
               maxActive = 50
               minIdle = 5
               maxIdle = 25
               maxWait = 10000
               maxAge = 10 * 60000
               timeBetweenEvictionRunsMillis = 5000
               minEvictableIdleTimeMillis = 60000
               validationQuery = "SELECT 1"
               validationQueryTimeout = 3
               validationInterval = 15000
               testOnBorrow = true
               testWhileIdle = true
               testOnReturn = false
               jdbcInterceptors = "ConnectionState"
               defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
    }
}
