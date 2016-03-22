# Kotlin Options

A collection of Kotlin extension methods that allow you treat Kotlin's nullable type as
a monad.

## Usage:

Take a nullable type and chain together operators to transform as required.

For example, given the following functions:
```Kotlin
    private fun getCurrentUserId(): String? {
        // return the current userId, should it exist.
    }

    private fun getCurrentUserFromDatabase(id: String): User? {
        // return the User object associated with the given id, should it exist.
    }
```
You could perform an action to log the user's name or an error message:
``` Kotlin
    getCurrentUserId()
            .filter { it.isNotEmpty() && it != "Invalid Id" }
            .flatMap { getCurrentUserFromDatabase(it) }
            .map { it.username }
            .map { "Logged in user: %s".format(it) }
            .orValue { "No user to login!" }
            .ifSome { log(it) }
```

## License

Copyright (c) Peter Tackage 2016

