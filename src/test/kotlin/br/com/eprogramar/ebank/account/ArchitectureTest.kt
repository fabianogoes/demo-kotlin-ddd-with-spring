package br.com.eprogramar.ebank.account

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import org.junit.BeforeClass


open class ArchitectureTest {

    companion object {
        const val DOMAIN_LAYER_PACKAGES = "com.example.demo.boundaries.account.domain"
        const val APPLICATION_LAYER_PACKAGES = "com.example.demo.boundaries.account.application"
        const val INFRASTRUCTURE_LAYER_PACKAGES = "com.example.demo.boundaries.account.infrastructure"
        const val PRESENTATION_LAYER_PACKAGES = "com.example.demo.boundaries.account.presentation"
    }

    var classes: JavaClasses? = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
            .importPackages("com.example.demo.boundaries.account")

}