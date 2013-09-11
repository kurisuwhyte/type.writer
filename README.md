type.writer
===========

[![Build Status](https://secure.travis-ci.org/kurisuwhyte/type.writer.png)](http://travis-ci.org/kurisuwhyte/type.writer)
[![experimental](http://hughsk.github.io/stability-badges/dist/experimental.svg)](http://github.com/hughsk/stability-badges)

**type.writer** is a documentation tool that's based on
[@rstacruz's Flatdoc](http://ricostacruz.com/flatdoc/), with a few changes to
accomodate better API documentations. It generates all documentation and
cross-references from plain Markdown files, and can be built to run locally,
whoo~!


## Getting started

First you'll need an Essay template, which will define how your documentation
will look. We can generate one for you, so run:

    $ typewriter new

This will create a new Essay documentation project in the current
folder. Templates are just regular HTML files which will be processed by
Enlive.

All documents live in the `source/` folder, and will be passed to the
corresponding templates to be processed.

To generate the documentation, run:

    $ typewriter build



## Licence

MIT.

