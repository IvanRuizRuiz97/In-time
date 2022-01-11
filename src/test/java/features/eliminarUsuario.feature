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
Feature: Eliminar usuario

  @tag1
  Scenario: Eliminar usuario
    Given Como administrador borro el usuario "PruebaRegistro2" 
    Then Se ha eliminado el usuario "PruebaRegistro2"    
 @tag2
  Scenario: Eliminar usuario no existe
    Given Como admin borro el usuario "PruebaRegistro2" 
    Then El usuario "PruebaRegistro2" no existe   
    @tag3
  Scenario: Eliminar usuario admin
    Given Como admin borro el admin "ramon" 
    Then El usuario "ramon" no se ha borrado