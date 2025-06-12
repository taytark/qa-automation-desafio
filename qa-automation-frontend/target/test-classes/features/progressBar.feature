Feature: Progress Bar

  Background:
    Given que estou na página inicial do demoqa

  Scenario: Validar carregamento e reset da Progress Bar
    When escolho a opção Widgets
    And clico no submenu Progress Bar
    And clico no botão Start
    And paro a execução antes de atingir 25%
    Then o valor da progress Bar deve ser menor ou igual a 25%
    When aperto Start novamente
    And aguardo a barra chegar a 100%
    Then clico no botão Reset para limpar a progress bar