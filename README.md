![Bounded Context](./doc/bounded-context.png)
# Demo Project DDD Account Kotlin

> Exemplo de um projeto de ***estudo*** de uma REST API utilizando DDD com Kotlin e Spring,   
> Fique a vontade para opinar e enviar Pull Request,   
> Assim podemos juntos evoluir o exemplo e deixar disponivel para a comunidade.

**Stack:**   

- Kotlin 
- Spring Web
- Spring Actuator
- Spring Data
- H2 Database
- Java 8
- Gradle

### Build Local
---------------

**Build by Gradle**

```
./gradlew clean build
```

**Running by Jar**

```
java -jar build/libs/demo-ddd-account-kotlin*.jar
```

**Test by Postman**

> To use the Postman Collection: `doc\postman_collection.json`


### Domain: Bank Account
------------------------

**Use Cases:**   

- Create New Account
- Create New Movement of Credit (Depósito)
- Create New Movement of Debit (Saque)
- Retrieve Account by Number (Extrato)
- Retrieve All Accounts

### Project Structure
---------------------

**Folders:**   

![Folders](./doc/structure-folders.png)

**Files:**   

![Files](./doc/structure-files.png)


### Architecture
---------------- 

**DDD Access layers:**    

![DDD Access layers](./doc/packages-layers.png)

**Hexagonal Architecture / Port Adapter:**   

![Hexagonal Architecture](./doc/port-adapter-components.png) 


### Open Source Project 
-----------------------

O objetivo deste projeto é aplicar o estudo do DDD usando a linguagem de programação **Kotlin** e o framework Web **Spring**.   
Através da construção de uma API REST explorando o dominio de uma **Conta Bancária**.   
O projeto está como publico para que consigam ajudar evoluindo a ideia e compartilhando com a comunidade.   

![Open Source / MIT](./doc/opensource-mitlicense.png) 





 