databaseChangeLog = {

	changeSet(author: "efriese (generated)", id: "1410214043629-1") {
		createTable(tableName: "feedback") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "feedbackPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "completed", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "retro_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-2") {
		createTable(tableName: "meter") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "meterPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "retro_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "score", type: "int4") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-3") {
		createTable(tableName: "mood") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "moodPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "mood_type_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "retro_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-4") {
		createTable(tableName: "mood_type") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mood_typePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "display", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "link", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-5") {
		createTable(tableName: "retro") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "retroPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "moderator_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "sprint_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "team_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-6") {
		createTable(tableName: "reward") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rewardPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "recipient_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "retro_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "sender_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-7") {
		createTable(tableName: "reward_type") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "reward_typePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-8") {
		createTable(tableName: "sprint") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sprintPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "end_date", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "start_date", type: "timestamp") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-9") {
		createTable(tableName: "team") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "teamPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-10") {
		createTable(tableName: "team_users") {
			column(name: "team_users_id", type: "int8")

			column(name: "user_id", type: "int8")

			column(name: "users_idx", type: "int4")
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-11") {
		createTable(tableName: "users") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usersPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "first", type: "varchar(255)")

			column(name: "last", type: "varchar(255)")

			column(name: "password", type: "varchar(255)")

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-12") {
		createTable(tableName: "vote") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "votePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "feedback_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "int4") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-30") {
		createIndex(indexName: "username_uniq_1410214043580", tableName: "users", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-31") {
		createSequence(sequenceName: "hibernate_sequence")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-13") {
		addForeignKeyConstraint(baseColumnNames: "retro_id", baseTableName: "feedback", constraintName: "FK_b72orm6aqldtt5s5bewl23l0b", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "retro", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-14") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "feedback", constraintName: "FK_kfpy3yr8gaddnnd11oru7k19s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-15") {
		addForeignKeyConstraint(baseColumnNames: "retro_id", baseTableName: "meter", constraintName: "FK_pq71r1s0cp06sddr51c36fylt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "retro", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-16") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "meter", constraintName: "FK_uja9hhs0ssabcsbc4royqvpe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-17") {
		addForeignKeyConstraint(baseColumnNames: "mood_type_id", baseTableName: "mood", constraintName: "FK_9bs5mdaexefn9p99vxlvjwja1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "mood_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-18") {
		addForeignKeyConstraint(baseColumnNames: "retro_id", baseTableName: "mood", constraintName: "FK_3me1938ayhqhfow6cymjl2v1d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "retro", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-19") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "mood", constraintName: "FK_7jd23o9kffp8ngsw5qkr4di7l", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-20") {
		addForeignKeyConstraint(baseColumnNames: "moderator_id", baseTableName: "retro", constraintName: "FK_4c0ujrqu4ikku5w2gyvan4q7d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-21") {
		addForeignKeyConstraint(baseColumnNames: "sprint_id", baseTableName: "retro", constraintName: "FK_btrrs4f5n97rfin7k7t3rto6u", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sprint", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-22") {
		addForeignKeyConstraint(baseColumnNames: "team_id", baseTableName: "retro", constraintName: "FK_rkta7nwqnpam5xa3dhh31pghr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "team", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-23") {
		addForeignKeyConstraint(baseColumnNames: "recipient_id", baseTableName: "reward", constraintName: "FK_gwuy5ei4s2bl5jfio5ylx4o05", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-24") {
		addForeignKeyConstraint(baseColumnNames: "retro_id", baseTableName: "reward", constraintName: "FK_1uk3f3onmuls9gdm7099hl4wa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "retro", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-25") {
		addForeignKeyConstraint(baseColumnNames: "sender_id", baseTableName: "reward", constraintName: "FK_ihywmuil43vh64a7p3kl7x31p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-26") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "reward", constraintName: "FK_6el5sfhf4k0s9x2mibprrnbm6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "reward_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-27") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "team_users", constraintName: "FK_ksaicybut8t3mv5wg249j4147", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-28") {
		addForeignKeyConstraint(baseColumnNames: "feedback_id", baseTableName: "vote", constraintName: "FK_6prcy38dx9xpe28hedd5xm2os", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "feedback", referencesUniqueColumn: "false")
	}

	changeSet(author: "efriese (generated)", id: "1410214043629-29") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "vote", constraintName: "FK_dx2u8pwxfq611f6nsatwu44p0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "users", referencesUniqueColumn: "false")
	}

    include file: 'changelog2.groovy'
    include file: 'createBaseData.groovy'
}
