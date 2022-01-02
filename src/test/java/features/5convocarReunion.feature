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
Feature: Convocar Reuniones

  @tag1
  Scenario: Convocar reunion
    Given los datos "PruebaConvocar1", "LUNES",  "12",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"
    Then Crea una reunion "PruebaConvocar1", "LUNES",  "12",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"

  @tag2
  Scenario: Convocar reunion dia mal
    Given los datos "PruebaConvocar3", "JUERNES",  "12",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"
    When el dia no existe
    Then no se registra la reunion "PruebaConvocar3", "JUERNES",  "12",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"

  @tag3
  Scenario: Convocar reunion hora inicial no existe
    Given los datos "PruebaConvocar4", "LUNES",  "30",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"
    When la hora inicial no existe
    Then no se registra la reunion "PruebaConvocar4", "LUNES",  "30",  "00",  "13",  "00", "2020-W52" y "PruebaRegistro1"

  @tag4
  Scenario: Convocar reunion hora Final no existe
    Given los datos "PruebaConvocar5", "LUNES",  "12",  "00",  "30",  "00", "2020-W52" y "PruebaRegistro1"
    When la hora final no existe
    Then no se registra la reunion "PruebaConvocar5", "LUNES",  "12",  "00",  "30",  "00", "2020-W52" y "PruebaRegistro1"
