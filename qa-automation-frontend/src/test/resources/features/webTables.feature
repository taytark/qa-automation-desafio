Feature: Web Tables

  Background:
    Given que estou na página inicial do demoqa
    
    Scenario: Criar, editar e deletar um registro
        When escolho a opção Elements
        And clico no submenu Web Tables
        And crio um novo registro
        Then o registro criado deve aparecer na tabela
        When edito o registro criado
        Then as informações do registro devem ser atualizadas
        When deleto o registro criado
        Then o registro não deve mais aparecer na tabela