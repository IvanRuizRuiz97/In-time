#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Modificar datos

	@tag1
  Scenario: Modificar email usuario
    Given En la vista user "PruebaRegistro1","registro1@registro.com","Password1"
    When El user "PruebaRegistro1" con contrasenia "Password1" ha modificado "danielito@gmail.com"
		Then El user "PruebaRegistro1" ha modificado el correo "registro1@registro.com" correctamente
		
	@tag2
  Scenario: Modificar password usuario
    Given En la vista user "PruebaRegistro1","registro1@registro.com","Daniel1"
    When El user "PruebaRegistro1" con el correo "registro1@registro.com"  ha modificado "Contrasena1"
		Then El user "PruebaRegistro1" ha modificado la contrasenia "Password1" correctamente
	
	
