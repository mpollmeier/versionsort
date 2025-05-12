# versionsort

Compares two version strings, similar to `sort --version-sort`

* handles missing patch/minor (defaults to 0)
* orders SNAPSHOT and pre-releases correctly
* supports alphanumeric comparison for non-numeric suffixes
* Pre-release versions, e.g. `1.0.0-alpha`, `1.0.0-SNAPSHOT`
* Post-release suffixes, e.g. `1.0.0a`
* Mixed alphanumeric segments
* follows semver precedence rules with extra flexibility
* "1.10" does not equal "1.10.0"
* The result is a negative integer if version1 lt version 2
* The result is a positive integer if version1 gt version 2
* The result is zero if the strings are numerically equal.

Usage:
```scala
 // build.sbt:
libraryDependencies += "com.michaelpollmeier" % "versionsort" % "1.0.14"

versionsort.VersionHelper.compare("1.0", "0.9") // 1
versionsort.VersionHelper.compare("1.0", "1.0") // 0
versionsort.VersionHelper.compare("0.9", "1.0") // -1

versionsort.VersionHelper.compare("0.0.0.2", "0.0.0.1") // 1
versionsort.VersionHelper.compare("1.0", "0.9") // 1
versionsort.VersionHelper.compare("2.0.1", "2.0.0") // 1
versionsort.VersionHelper.compare("2.0.1", "2.0") // 1
versionsort.VersionHelper.compare("2.0.1", "2") // 1
versionsort.VersionHelper.compare("0.9.1", "0.9.0") // 1
versionsort.VersionHelper.compare("0.9.2", "0.9.1") // 1
versionsort.VersionHelper.compare("0.9.11", "0.9.2") // 1
versionsort.VersionHelper.compare("0.9.12", "0.9.11") // 1
versionsort.VersionHelper.compare("0.10", "0.9") // 1
versionsort.VersionHelper.compare("0.10", "0.10") // 0
versionsort.VersionHelper.compare("2.10", "2.10.1") // -1
versionsort.VersionHelper.compare("0.0.0.2", "0.1") // -1
versionsort.VersionHelper.compare("1.0", "0.9.2") // 1
versionsort.VersionHelper.compare("1.10", "1.6") // 1
versionsort.VersionHelper.compare("1.10.0.0.0.1", "1.10") // 1

versionsort.VersionHelper.compare("1.5.0", "1.5.0-SNAPSHOT") // 1
versionsort.VersionHelper.compare("1.5.0", "1.5.0-alpha") // 1
versionsort.VersionHelper.compare("1.5.0-alpha", "1.5.0-SNAPSHOT") // > 1
versionsort.VersionHelper.compare("1.5", "1.6-SNAPSHOT") // -1
versionsort.VersionHelper.compare("1.7", "1.7a") // -1
versionsort.VersionHelper.compare("1.7b", "1.7a") // 1
```
