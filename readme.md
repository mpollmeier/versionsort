# versionsort

<!-- [![Build Status](https://travis-ci.org/mpollmeier/versionsort.svg?branch=master)](https://travis-ci.org/mpollmeier/versionsort) -->

Compares two version strings, similar to `sort --version-sort`.

Usage:
```
 // build.sbt:
libraryDependencies += "com.michaelpollmeier" % "versionsort" % "1.0.11"

versionsort.VersionHelper.compare("1.0", "0.9")
// result: 1
```

Use this instead of String.compareTo() for a non-lexicographical 
comparison that works for version strings. e.g. "1.10".compareTo("1.6").
Note it does not work if "1.10" is supposed to be equal to "1.10.0".

This project has zero dependencies. 
This is copy paste driven development, it was originally posted by
Alex Gitelman on https://stackoverflow.com/a/6702029/452762

