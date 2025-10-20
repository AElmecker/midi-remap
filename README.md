# MIDI Remap

Ever had a MIDI drum file that sounded good in one program and after importing into another one it started sounding 
weird or the wrong percussion elements were hit?

Same here, but there's a solution!\
With this tool you can build note mappings between programs/formats and convert MIDI files so they sound at least
somewhat nice, wherever you need them to :)

## Getting Started

1. Make sure to have java 21 or newer installed and properly set up
2. Checkout the repository
3. Create the jar by running
   1. `./gradlew jar` (MacOS/Linux) or
   2. `./gradlew.bat jar` (Windows)
4. Execute the application `java -jar ./build/libs/midi-remap-<version>.jar <midi-in> <midi-out> <remapping-definition>`

## Getting Help / Reporting Issues

We are happy to help, but please consult documentation and existing issues first.

Please provide as much information as you can when creating an issue and if feasible a test/sample code to reproduce.

## Contributing

Contributions are very welcome!\
To make collaboration as easy as possible everyone should adhere following steps:

* Every contribution has a dedicated issue
  * Except minor changes like
    * small re-formatting changes
    * minor code smell fixes
    * spelling/grammar corrections
    * etc...
* Commit messages start with issue or `NOISSUE`:
  * `#3: add tests`
  * `NOISSUE: fix typo in README.md`
* Contributions uphold the quality standards
  * tests
  * formatting
  * etc...
* Make sure that when contributing, that you are the owner and/or have the rights to distribute code, compositions, or
  any other kind of data.

## License

MIDI Remap is released under the [MIT License](./LICENSE).

## Notice

Any trademarks and copyrights are property of their respective owners and are only mentioned for informative purposes.
Other names may be trademarks of their respective owners.
