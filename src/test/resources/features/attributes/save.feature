@save
Feature: Save Game
  I want to check the functionality of Save button and Restart button

  Background:
    Given I open the game page thru "firefox"

  Scenario: Having pressed the Save button after playing the game for some time the game is restarted by pressing Restart button

    When player waits 3 seconds
    And player presses 3 times Left button and Drop button and player waits 3 seconds
    And player presses 2 times Right button and Drop button and player waits 3 seconds
    And player presses Drop button and player waits 3 seconds
    And player presses Save button
    And player again opens the game page and player waits 3 seconds
    And player again presses 2 times Right button and Drop button and player waits 3 seconds
    And player again presses Drop button and player waits 3 seconds
    And player presses Restart button
    Then mosaic of fallen tetraminos changes into that as it was when Save button was pressed during previous game
