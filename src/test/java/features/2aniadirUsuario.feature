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
Feature: Aniadir usuario

 @tag1
  Scenario: Aniadir usuario
  Given En la vista admin "PruebaDani1","admin@admin.com","Password2", "Password2" y "ASISTENTE"
  Then el usuario se ha creado el usuario correctamente "PruebaDani1"

 @tag2
  Scenario: Aniadir usuario vacio
  Given En la vista admin "","","", "" y ""
  Then no se ha registrado el usuario vacio ""
  
  @tag3
  Scenario: Aniadir usuario existente
  Given En la vista admin "PruebaDani1","admin@admin.com","Password2", "Password2" y "ASISTENTE"
  Then no se registrado el usuario "PruebaDani1"