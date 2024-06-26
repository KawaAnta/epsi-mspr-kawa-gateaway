# API GateAway

## Description

The GateAway broker plays a crucial role within the application's architecture for managing microservices API connection. Its primary objective is to handle operations related to order management between the 3 microservices of Kawa Application, providing functionalities to create, retrieve, update, and delete order records.

In the context provided earlier in this conversation, the GateAway microservice aligns with the broader goal of modernizing the PayeTonKawa company's IT infrastructure by migrating towards a microservices-based architecture. Specifically, it aims to cater to the needs of managing order information for both B2C and B2B segments efficiently.

### Contributions
#### Checkstyle :

Please, before make a contribution and a pull-request ensure that checkstyle test are ok !
To do so, execute commande below :

```sh
mvn checkstyle:check 
```

#### Branch Naming Strategy :
* fix/kawa-{ticket-number}-{brief-description-of-fix} (for bug fixes)
* feature/kawa-{ticket-number}-{brief-description-of-feature} (for user story developments)
* chore/kawa-{ticket-number}-{brief-description-of-refactoring} (for code cleanups / refactoring) (ticket number is optional if no user story)
* tests/kawa-{ticket-number}-{brief-description-of-test-dev} (for user story test developments)

#### Commit Naming Strategy :
* Fix[ticket-number]: Description of the fix
* Feat[ticket-number]: Description of the feature
* Chore[ticket-number]: Description of the refactoring
* Tests[ticket-number]: Description of the test