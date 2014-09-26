package com.amplify.agile

import org.apache.commons.lang.builder.HashCodeBuilder

class UserSecRole implements Serializable {

    private static final long serialVersionUID = 1

    User secUser
    SecRole secRole

    boolean equals(other) {
        if (!(other instanceof UserSecRole)) {
            return false
        }

        other.secUser?.id == secUser?.id &&
                other.secRole?.id == secRole?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (secUser) builder.append(secUser.id)
        if (secRole) builder.append(secRole.id)
        builder.toHashCode()
    }

    static UserSecRole get(long secUserId, long secRoleId) {
        UserSecRole.where {
            secUser == User.load(secUserId) &&
                    secRole == SecRole.load(secRoleId)
        }.get()
    }

    static boolean exists(long secUserId, long secRoleId) {
        UserSecRole.where {
            secUser == User.load(secUserId) &&
                    secRole == SecRole.load(secRoleId)
        }.count() > 0
    }

    static UserSecRole create(User secUser, SecRole secRole, boolean flush = false) {
        def instance = new UserSecRole(secUser: secUser, secRole: secRole)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, SecRole r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = UserSecRole.where {
            secUser == User.load(u.id) &&
                    secRole == SecRole.load(r.id)
        }.deleteAll()

        if (flush) {
            UserSecRole.withSession { it.flush() }
        }

        rowCount > 0
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null) return

        UserSecRole.where {
            secUser == User.load(u.id)
        }.deleteAll()

        if (flush) {
            UserSecRole.withSession { it.flush() }
        }
    }

    static void removeAll(SecRole r, boolean flush = false) {
        if (r == null) return

        UserSecRole.where {
            secRole == SecRole.load(r.id)
        }.deleteAll()

        if (flush) {
            UserSecRole.withSession { it.flush() }
        }
    }

    static constraints = {
        secRole validator: { SecRole r, UserSecRole ur ->
            if (ur.secUser == null) return
            boolean existing = false
            UserSecRole.withNewSession {
                existing = UserSecRole.exists(ur.secUser.id, r.id)
            }
            if (existing) {
                return 'userRole.exists'
            }
        }
    }

    static mapping = {
        id composite: ['secRole', 'secUser']
        version false
    }
}
