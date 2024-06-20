# T3 Compiladores

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

### Integrantes

- Jorge Pires (790942)
- Giullio Gerolamo (790965)

## Analisador Semântico para Linguagem Algorítmica (LA)
Este é o trabalho 3 (T3) da disciplina, que consiste na implementação de um analisador semântico para a linguagem LA (Linguagem Algorítmica) desenvolvida pelo professor Jander, no âmbito do DC/UFSCar. O analisador semântico lê um programa-fonte e verifica se estão de acordo com as regras definidas na gramática da linguagem, detectando 4 tipos de erro:

- Identificador (variável, constante, procedimento, função, tipo) já declarado anteriormente no escopo
  - O mesmo identificador não pode ser usado novamente no mesmo escopo mesmo que para categorias diferentes
- Tipo não declarado
- Identificador (variável, constante, procedimento, função) não declarado
- Atribuição não compatível com o tipo declarado
  - Atribuições possíveis
    ```
        ponteiro ← endereço
        (real | inteiro) ← (real | inteiro)
        literal ← literal
        logico ← logico
        registro ← registro (com mesmo nome de tipo)
    ```

  - As mesmas restrições são válidas para expressões, por exemplo, ao tentar combinar um literal com um logico (como em literal + logico) deve dar tipo_indefinido e inviabilizar a atribuição


## Pré Requisitos
Antes de compilar e executar o analisador semântico, verifique se o seguinte software está instalado em seu sistema:

- Java 11 ou superior
- Apache Maven 3.6.3 ou superior


## Como executar o Analisador Semântico

### Dependências utilizadas
- Antlr = 4.12.0
- jUnit = 4.11
- maven-clean-plugin = 3.1.0
- maven-resources-plugin = 3.0.2
- maven-compiler-plugin = 3.8.0
- maven-surefire-plugin = 2.22.1
- maven-jar-plugin = 3.0.2
- maven-install-plugin = 2.5.2
- maven-deploy-plugin = 2.8.2
- maven-site-plugin = 3.7.1
- maven-project-info-reports-plugin = 3.0.0
  
### Compilação com Maven
Siga estas etapas para compilar o analisador semântico usando Maven:

1. Abra um terminal ou prompt de comando.
2. Navegue até o diretório raiz do projeto onde está localizado o arquivo pom.xml.
3. Execute o seguinte comando para limpar o projeto e compilar os arquivos fonte:
    ```
    mvn clean compile 
    ```
   Este comando irá limpar os arquivos compilados antigos e compilar os novos.

### Empacotamento do Projeto
Após a compilação com sucesso, você pode empacotar o projeto usando o seguinte comando Maven:
```
mvn package
```
Este comando irá gerar um arquivo JAR executável no diretório target, com todas as dependências incluídas.

### Execução do Analisador Semântico
Após empacotar o projeto, você pode executar o analisador semântico para um par de entrada e saída arbitrário. Use o seguinte comando:
    
```
java -jar target/alguma-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar caminho_entrada caminho_saida
```
    
- ``caminho_entrada``: Caminho completo do arquivo contendo o programa-fonte em linguagem LA.

- ``caminho_saída``: Caminho completo do arquivo onde a saída será salva.


Substitua ``target/alguma-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar`` pelo caminho correto do arquivo JAR gerado pelo Maven.
