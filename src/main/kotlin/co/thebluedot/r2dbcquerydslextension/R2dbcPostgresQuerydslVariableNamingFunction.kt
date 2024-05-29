package co.thebluedot.r2dbcquerydslextension

import com.google.common.base.CaseFormat
import com.querydsl.codegen.EntityType
import org.springframework.data.relational.core.mapping.Table
import java.util.function.Function

class R2dbcPostgresQuerydslVariableNamingFunction : Function<EntityType, String> {
    override fun apply(entityType: EntityType): String {

        val tableAnnotation = entityType.annotations.filterNotNull().find { it.annotationClass == Table::class } as Table?
        val schemaOverride = tableAnnotation?.schema
        val nameOverride = tableAnnotation?.name
        val fieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.simpleName)

        return buildString {
            if (schemaOverride != null) {
                append(schemaOverride)
                append(".")
            }
            if (nameOverride != null) {
                append(nameOverride)
            } else {
                append(fieldName)
            }
        }
    }
}
