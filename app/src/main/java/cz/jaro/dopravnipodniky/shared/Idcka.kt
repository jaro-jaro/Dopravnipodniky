package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.data.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

typealias UliceID = @Serializable(with = UUIDSerializer::class) UUID
typealias KrizovatkaID = @Serializable(with = UUIDSerializer::class) UUID
typealias BusID = @Serializable(with = UUIDSerializer::class) UUID
typealias LinkaID = @Serializable(with = UUIDSerializer::class) UUID
typealias DPID = @Serializable(with = UUIDSerializer::class) UUID