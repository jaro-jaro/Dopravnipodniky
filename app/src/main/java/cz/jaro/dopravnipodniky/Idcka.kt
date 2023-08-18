package cz.jaro.dopravnipodniky

import kotlinx.serialization.Serializable
import java.util.UUID

typealias UliceID = @Serializable(with = UUIDSerializer::class) UUID
typealias BusID = @Serializable(with = UUIDSerializer::class) UUID
typealias LinkaID = @Serializable(with = UUIDSerializer::class) UUID