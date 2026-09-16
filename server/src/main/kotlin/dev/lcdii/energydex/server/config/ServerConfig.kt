package dev.lcdii.energydex.server.config

import io.ktor.server.application.ApplicationEnvironment

data class ServerConfig(
    val databaseUrl: String,
    val databaseUser: String,
    val databasePassword: String,
    val redisUri: String,
    val jwtSecret: String,
    val smtpHost: String,
    val smtpPort: Int,
    val migrateOnStart: Boolean
)

object ServerConfigLoader{
    fun load(enviroment: ApplicationEnvironment) : ServerConfig = ServerConfig(
        databaseUrl = enviroment.requiredValue(
            propertyName = "energydex.database.url",
            environmentName = "ENERGYDEX_DATABASE_URL"
        ),
        databaseUser = enviroment.requiredValue(
            propertyName = "energydex.database.user",
            environmentName = "ENERGYDEX_DATABASE_USER"
        ),
        databasePassword = enviroment.requiredValue(
            propertyName = "energydex.database.password",
            environmentName = "ENERGYDEX_DATABASE_PASSWORD"
        ),
        redisUri = enviroment.requiredValue(
            propertyName = "energydex.redis.uri",
            environmentName = "ENERGYDEX_REDIS_URI"
        ),
        jwtSecret = enviroment.requiredValue(
            propertyName = "energydex.jwt.secret",
            environmentName = "ENERGYDEX_JWT_SECRET"
        ),
        smtpHost = enviroment.requiredValue(
            propertyName = "energydex.smtp.host",
            environmentName = "ENERGYDEX_SMTP_HOST"
        ),
        smtpPort = enviroment.requiredValue(
            propertyName = "energydex.smtp.port",
            environmentName = "ENERGYDEX_SMTP_PORT"
        ).toIntOrNull() ?: error("Server configuration value ENERGYDEX_SMTP_PORT must be a number"),
        migrateOnStart = enviroment.optionalValue(
            propertyName = "energydex.database.migrate-on-start",
            environmentName = "ENERGYDEX_DATABASE_MIGRATE_ON_START"
        )?.toBooleanStrictOrNull() ?: true
    )

    private fun ApplicationEnvironment.requiredValue(propertyName: String, environmentName: String):
            String =
        config.propertyOrNull(propertyName)?.getString()
            ?: System.getenv(environmentName)
            ?: error("Missing required server configuration $environmentName (or $propertyName)")
    private fun ApplicationEnvironment.optionalValue(propertyName: String, environmentName: String):
            String? =
        config.propertyOrNull(propertyName)?.getString() ?: System.getenv(environmentName)
}
