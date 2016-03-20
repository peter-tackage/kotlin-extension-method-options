# Kotlin Options

A collection of Kotlin extension methods that treat Kotlin's nullable type as
a monad.


## Usage:

``` Kotlin
    val message : String? = "This a nullable value"

    message.map{it -> it + " that has been mapped"}
           .filter{it -> it.length < 10}
           .ifSome(logShortMessageReceived())
           .ifNone(logNullMessageReceived())
```

## License

Copyright (c) Peter Tackage 2016

