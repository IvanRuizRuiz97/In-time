#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and "placeholder"
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#"" (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Login

  @tag1
  Scenario: Acceder a la aplicacion satisfactoriamente Asistente
    Given acceso con "PruebaRegistro1" y "Password1" correctos asistente
    When los datos son correctos y de un asistente
    Then Accedo a la pantalla principal de asistente
  
  @tag2
  Scenario: Acceder a la aplicacion satisfactoriamente Admin
    Given acceso con "chema" y "Chema1" correctos admin
    When los datos son correctos y de un administrador
    Then Accedo a la pantalla principal de admin
  
  @tag3
  Scenario: Acceder a la aplicacion con la contrasena mal puesta
    Given acceso con "PruebaRegistro1" correcto y "Password2" incorrecto
    When intento acceder con el "PruebaRegistro1" correcto y "Password2" mal
    Then se lanza la excepcion CredencialesInvalidas
    
  @tag4
  Scenario: Acceder a la aplicacion con el nombre mal puesto
    Given acceso con "PruebaRegistroincorrecto" incorrecto y "Password1" correcto
    When intento acceder con el "nombre" mal y "password" bien
    Then se lanza la excepcion de CredencialesInvalidas
    
    


    
