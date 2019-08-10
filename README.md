# Keres [![Build Status](https://travis-ci.org/elegal-ev/Keres.svg?branch=master)](https://travis-ci.org/elegal-ev/Keres)

A library for automatic generation of cease and desist 🗎 based on docx templates

## Getting Started

Keres is still active in development, therefore we don't have a maven central repository.

### Prerequisites for development build

You basically just need an new jdk, we just support jdk11+

Ubuntu:
```
apt-get update && apt-get install openjdk-11-jdk
```
Windows: (with choco)
```
choco install openjdk --version 11.0
```

### Installing

Clone the repository
```
git clone https://github.com/elegal-ev/Keres.git
```
Build it (here for linux, gradlew.bat for windows)
```
./gradlew jar
```
The jar is located at `build/libs/`

## Running the tests

Just run
```
./gradlew check
```
which is basically a wrapper around `./gradlew test` with a few more checks see [here](https://stackoverflow.com/a/50105980).

## Built With

* [Apache POI](https://poi.apache.org/) - The Java API for Microsoft Documents
* [Gradle](https://gradle.org/) - A popular build automation tool
* [Travis](https://travis-ci.org/elegal-ev/Keres) - For continuous integration 

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) as well as our [Code of Conduct](CODE_OF_CONDUCT.md)

## Authors

Up to this point, this is basically a project of just the two of us

* **Valerius Ma** - [valerius21](https://github.com/valerius21)
* **Lars Quentin** - [lquenti](https://github.com/lquenti)

## License

This project is licensed under the GPL3 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

- Love to everyone who is interested in this project :)
- Everyone who will learn java programming with this project and the [additional slides (just in german for now, sorry!)](https://elegal-ev.github.io/Presentations/)

## Copyright

eLegal Goettingen e.V. 2019
