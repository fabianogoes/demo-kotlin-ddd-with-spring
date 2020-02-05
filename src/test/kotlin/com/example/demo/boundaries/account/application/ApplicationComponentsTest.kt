package com.example.demo.boundaries.account.application

import com.example.demo.boundaries.account.ArchitectureTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service


class ApplicationComponentsTest : ArchitectureTest() {

    @Test
    fun `classes that reside in package "application" should have "Name" ending with "UseCase"` () {
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideInAPackage(APPLICATION_LAYER_PACKAGES)
                .should().haveSimpleNameContaining("UseCase")
        rule.check(classes)
    }

    @Test
    fun `classes that reside in package "application" should be annotated with "Service" stereotype` () {
        val rule: ArchRule = ArchRuleDefinition.classes()
                .that().resideInAPackage(APPLICATION_LAYER_PACKAGES)
                .and().haveSimpleNameEndingWith("UseCaseImpl")
                .should().beAnnotatedWith(Service::class.java)
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