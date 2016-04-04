# Kotlin Options

A collection of Kotlin extension methods that allow you powerfully transform Kotlin's nullable types.

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
            .orElse { "No user to login!" }
            .ifSome { log(it) }
```

## Options in other languages

* [Java 8](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [Scala](http://www.scala-lang.org/api/2.7.4/scala/Option.html)
* [Swift](https://developer.apple.com/library/ios/documentation/Swift/Conceptual/Swift_Programming_Language/OptionalChaining.html)

## License

    Copyright 2016 Peter Tackage

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

