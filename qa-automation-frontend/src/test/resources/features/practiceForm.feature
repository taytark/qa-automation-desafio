Feature: Practice Form

  Background:
    Given que estou na página inicial do demoqa
    
  Scenario: Abrir o menu Forms e preencher formulário
    When escolho a opção Forms na página inicial
    And clico no submenu Practice Form
    And preencho todo o formulário com valores aleatórios
    And submeto o formulário
    Then garanto que um popup foi aberto após o submit
    And fecho o popup