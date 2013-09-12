type.writer
===========

[![Build Status](https://secure.travis-ci.org/kurisuwhyte/type.writer.png)](http://travis-ci.org/kurisuwhyte/type.writer)
[![experimental](http://hughsk.github.io/stability-badges/dist/experimental.svg)](http://github.com/hughsk/stability-badges)

The simplest documentation tool that could possibly work — with a little bit of
flexibility ;)

**type.writer** is a documentation tool that's based on
[@rstacruz's Flatdoc](http://ricostacruz.com/flatdoc/). In a sense, it works
like [Sphinx](http://sphinx-doc.org/), except that you don't have multiple
output formats, multiple configurations, multiple... Instead, you just dump
whatever markdown files you have in a folder and we'll generate the proper HTML
docs.


## Installing

Currently the best way to install type.writer is by hand:

 1.  Make sure you have a [Java JRE 7][JRE].
 2.  [Download the latest release][release]
 3.  Place `bin/typewriter` somewhere in your `$PATH`.

> **Note**: it only works on Linux right now (and **maybe** in OS/X).

[JRE]: http://www.java.com/en/download/index.jsp
[release]: https://github.com/kurisuwhyte/type.writer/releases/download/v0.2.0/typewriter-0.2.0.tar.gz


## Basic Usage

type.writer uses a simple project structure, which you can generate running:

    $ typewriter new
    
After that, put the relevant meta-data in the `project.edn` file, dump your
`*.md` markdown files in the `source/` folder and run:

    $ typewriter build

Check out the [documentation](http://kurisuwhyte.github.io/type.writer/) for
more information.


## Licence

MIT.

