package com.amplify.agile

class User {

    transient springSecurityService

    String username
    String password
    String first
    String last
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        password blank: false
        first blank: true, nullable: true
        last blank: true, nullable: true
    }

    static mapping = {
        password column: '`password`'
        table 'users'
    }

    Set<SecRole> getAuthorities() {
        UserSecRole.findAllBySecUser(this).collect { it.secRole }
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }
}
