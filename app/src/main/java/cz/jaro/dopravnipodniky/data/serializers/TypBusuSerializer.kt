package cz.jaro.dopravnipodniky.data.serializers

import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.shared.typyBusu
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class TypBusuSerializer : KSerializer<TypBusu> {
    override fun deserialize(decoder: Decoder): TypBusu {
        val model = decoder.decodeString()
        return typyBusu.first { it.model == model }
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TypBusu", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: TypBusu) {
        encoder.encodeString(value.model)
    }
}