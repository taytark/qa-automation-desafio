Feature: Teste de API para Bookstore

  Scenario: Fluxo completo de usuário com reserva de livros
    Given um usuário é criado
    And um token é gerado
    And o usuário está autorizado
    When os livros disponíveis são listados
    And dois livros são alugados
    Then liste os detalhes do usuário com os livros escolhidos