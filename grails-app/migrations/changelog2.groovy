databaseChangeLog = {

    changeSet(author: "jpriest (generated)", id: "1411010958306-1") {
        createTable(tableName: "feedback_vote") {
            column(name: "feedback_votes_id", type: "int8")

            column(name: "vote_id", type: "int8")

            column(name: "votes_idx", type: "int4")
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-2") {
        createTable(tableName: "retro_users") {
            column(name: "retro_users_id", type: "int8")

            column(name: "user_id", type: "int8")

            column(name: "users_idx", type: "int4")
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-3") {
        createTable(tableName: "sec_role") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sec_rolePK")
            }

            column(name: "version", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-4") {
        createTable(tableName: "user_sec_role") {
            column(name: "sec_role_id", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "sec_user_id", type: "int8") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-5") {
        addColumn(tableName: "feedback") {
            column(name: "discussed", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-6") {
        addColumn(tableName: "retro") {
            column(name: "previous_retro_id", type: "int8")
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-7") {
        addColumn(tableName: "reward_type") {
            column(name: "display", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-8") {
        addColumn(tableName: "reward_type") {
            column(name: "link", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-9") {
        addColumn(tableName: "sprint") {
            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-10") {
        addColumn(tableName: "users") {
            column(name: "account_expired", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-11") {
        addColumn(tableName: "users") {
            column(name: "account_locked", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-12") {
        addColumn(tableName: "users") {
            column(name: "enabled", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-13") {
        addColumn(tableName: "users") {
            column(name: "password_expired", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-14") {
        modifyDataType(columnName: "completed", newDataType: "boolean", tableName: "feedback")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-15") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "password", tableName: "users")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-16") {
        addPrimaryKey(columnNames: "sec_role_id, sec_user_id", constraintName: "user_sec_rolePK", tableName: "user_sec_role")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-17") {
        dropForeignKeyConstraint(baseTableName: "vote", baseTableSchemaName: "public", constraintName: "FK_6prcy38dx9xpe28hedd5xm2os")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-23") {
        createIndex(indexName: "authority_uniq_1411010957677", tableName: "sec_role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-24") {
        dropColumn(columnName: "name", tableName: "reward_type")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-25") {
        dropColumn(columnName: "feedback_id", tableName: "vote")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-18") {
        addForeignKeyConstraint(baseColumnNames: "vote_id", baseTableName: "feedback_vote", constraintName: "FK_7xyge5s81kciw5f4493sxyrt0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vote", referencesUniqueColumn: "false")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-19") {
        addForeignKeyConstraint(baseColumnNames: "previous_retro_id", baseTableName: "retro", constraintName: "FK_70oxj0ehmckglit3n41yudd3u", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "retro", referencesUniqueColumn: "false")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-20") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "retro_users", constraintName: "FK_4r9sk94i5cuncfqf42ahuwc2v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-21") {
        addForeignKeyConstraint(baseColumnNames: "sec_role_id", baseTableName: "user_sec_role", constraintName: "FK_qkrkbctawm06f3muvgtaosuf1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sec_role", referencesUniqueColumn: "false")
    }

    changeSet(author: "jpriest (generated)", id: "1411010958306-22") {
        addForeignKeyConstraint(baseColumnNames: "sec_user_id", baseTableName: "user_sec_role", constraintName: "FK_nekcynohutuxon57ucgs4tyy8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
    }
}
