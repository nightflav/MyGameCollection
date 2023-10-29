# MyGameCollection

### Under active development

Idea: I just wanted to check all games that I ever played, completed or that I want at the moment, but there was no any good application to achieve all my needs.
So i decided to make my own because I able to do it and want it. So here it is!

- UI is fully implemented in Jetpack Compose
- DI is made with pure Dagger, so no Hilt and anything like that.
- Background tasks and asynchronous programming via kotlinx.Coroutines and Flows

## Stack

|What|Technology|
|-|-|
|🔘 User Interface (Android)|[Jetpack Compose](https://developer.android.com/jetpack/compose)|
|🏛 Architecture|[Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)|
|💉 DI (Android)|[Dagger](https://dagger.dev/dev-guide/)|
|⌚️ Async| [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)                |
|📶 Network| [Retrofit](https://square.github.io/retrofit/)|
|📃 Parsing| [KotlinX](https://kotlinlang.org/docs/serialization.html)|

## Architecture

- Core - modules that can be used by any other module
- Feature module divided into api, domain, data and presentation sub-modules
- Domain module is a standalone module that is not depend on Android framework
- Feature API modules are needed to "register" feature in NavGraph and provide some dependencies needed outside of module via DataProvider interface

## Todos

- Implement all others features
- Make all feature entries injectable in destinations map
- Implement some more custom composables so that it will looks exactly like I want it to look
- ...
