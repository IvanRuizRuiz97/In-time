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
Feature: Asistir Reuniones

  @tag1
  Scenario: Aceptar reunion
		Given el usuario "PruebaRegistro1" acepta la reunion "1" 
  	Then el usuario "PruebaRegistro1" tiene la reunion "1" en su agenda
  @tag2
  Scenario: Rechazar reunion
		Given el usuario "PruebaRegistro1" rechaza la reunion "2" 
  	Then el usuario "PruebaRegistro1" no tiene la reunion "2" en su agenda
  	
  
  		
  	
  