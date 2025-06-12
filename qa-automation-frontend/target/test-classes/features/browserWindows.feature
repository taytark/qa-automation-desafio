Feature: Browser Windows

  Background:
    Given que estou na página inicial do demoqa

  Scenario: Abrir nova janela e validar mensagem
    When escolho a opção Alerts, Frame & Windows
    And clico no submenu Browser Windows
    And clico no botão New Window
    Then uma nova janela deve ser aberta
    And valido a mensagem This is a sample page na nova janela
    And fecho a nova janela