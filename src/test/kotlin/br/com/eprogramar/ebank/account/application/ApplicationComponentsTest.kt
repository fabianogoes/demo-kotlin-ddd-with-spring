package br.com.eprogramar.ebank.account.application

import br.com.eprogramar.ebank.account.ArchitectureTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test
import javax.inject.Named


class ApplicationComponentsTest : ArchitectureTest() {

    @Test
    fun `classes that reside in package "application" should be annotated with "Service" stereotype` () {
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideInAPackage(APPLICATION_LAYER_PACKAGES)
                .and().haveSimpleNameEndingWith("UseCaseAdapter")
                .should().beAnnotatedWith(Named::class.java)
        rule.check(classes)
    }

    @Test
    fun `classes that reside in package "application" should only be accessed by classes that reside in package "presentation"` () {
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideInAPackage(APPLICATION_LAYER_PACKAGES)
                .should().onlyBeAccessed().byAnyPackage(PRESENTATION_LAYER_PACKAGES, APPLICATION_LAYER_PACKAGES)
        rule.check(classes)
    }

}