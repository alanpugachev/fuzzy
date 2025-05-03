package com.alanpugachev

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import com.typesafe.config.ConfigFactory
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private lateinit var dataSource: HikariDataSource

    fun init() {
        val config = ConfigFactory.load().getConfig("database")

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.getString("url")
            username = config.getString("user")
            password = config.getString("password")
            driverClassName = config.getString("driver")
            maximumPoolSize = config.getInt("pool.maxSize")
            connectionTimeout = config.getLong("pool.connectionTimeout")
            validate()
        }

        dataSource = HikariDataSource(hikariConfig)

        Database.connect(dataSource)

        testConnection()
    }

    private fun testConnection() {
        transaction {
            try {
                exec("SELECT 1") {}
                println("✅ Database connection established")
            } catch (e: Exception) {
                println("❌ Database connection failed: ${e.message}")
                throw e
            }
        }
    }

    fun close() {
        dataSource.close()
    }
}