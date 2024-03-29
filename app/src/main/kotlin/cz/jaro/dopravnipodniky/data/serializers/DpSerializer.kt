package cz.jaro.dopravnipodniky.data.serializers

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class DpSerializer : KSerializer<Dp> {
    override fun deserialize(decoder: Decoder): Dp {
        return Dp(decoder.decodeFloat())
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Dp", PrimitiveKind.FLOAT)

    override fun serialize(encoder: Encoder, value: Dp) {
        encoder.encodeFloat(value.value)
    }
}