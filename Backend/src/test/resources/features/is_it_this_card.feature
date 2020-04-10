#Author: s.lira.2016@alumnos.urjc.es
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
@Card
Feature: Is/Are this/those cards I'm looking for?
  Everyone wants to search cards by diferent filters

	  @Name
	  Scenario: Search by name
	    Given Card name Azorius
	    When I search that name
	    Then I see all cards with Azorius in its name

    @Text
    Scenario: Search by card text
    	Given Text Vigilance
    	When I search texts
    	Then i see all cards with Vigilance
    
    
    
    
    
    
    
    
    