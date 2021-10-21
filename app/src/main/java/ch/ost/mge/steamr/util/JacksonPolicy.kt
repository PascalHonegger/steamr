package ch.ost.mge.steamr.util

import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.QName
import nl.adaptivity.xmlutil.serialization.*
import nl.adaptivity.xmlutil.serialization.structure.SafeParentInfo

@ExperimentalXmlUtilApi
private val ignoreMissingChildHandler = UnknownChildHandler { _, _, _, _, _ ->
    emptyList()
}

/**
 * Example policy that (very crudely) mimicks the way that Jackson serializes xml. It starts by eliding defaults.
 * Note that this version doesn't handle the jackson annotations.
 */
@ExperimentalXmlUtilApi
object JacksonPolicy :
    DefaultXmlSerializationPolicy(false, encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.NEVER, unknownChildHandler = ignoreMissingChildHandler) {
    /*
     * Rather than replacing the method wholesale, just make attributes into elements unless the [XmlElement] annotation
     * is present with a `false` value on the value attribute.
     */
    override fun effectiveOutputKind(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        canBeAttribute: Boolean
    ): OutputKind {
        val r = super.effectiveOutputKind(serializerParent, tagParent, canBeAttribute)
        return when {
            // Do take into account the XmlElement annotation
            r == OutputKind.Attribute &&
                    serializerParent.elementUseAnnotations.mapNotNull { it as? XmlElement }
                        .firstOrNull()?.value != false ->
                OutputKind.Element

            else -> r
        }
    }

    /**
     * Jackson naming policy is based upon use name only. However, for this policy we do take the type annotation
     * if it is available. If there is no annotation for the name, we get the name out of the useName in all cases
     * (the default policy is dependent on member kind and the output used (attribute vs element)).
     */
    override fun effectiveName(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        outputKind: OutputKind,
        useName: XmlSerializationPolicy.DeclaredNameInfo
    ): QName {
        return useName.annotatedName
            ?: serializerParent.elementTypeDescriptor.typeQname
            ?: serialUseNameToQName(useName, tagParent.namespace)
    }

}