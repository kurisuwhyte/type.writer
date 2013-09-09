Essay
=====

[![Build Status](https://secure.travis-ci.org/kurisuwhyte/essay.png)](http://travis-ci.org/kurisuwhyte/essay)
[![experimental](http://hughsk.github.io/stability-badges/dist/experimental.svg)](http://github.com/hughsk/stability-badges)

TL;DR. The *essay-est* and pretty-est way of documenting your project.

**Essay** is a documentation tool that's based on
[@rstacruz's Flatdoc](http://ricostacruz.com/flatdoc/), with a few changes to
accomodate better API documentations. It generates all documentation and
cross-references from plain Markdown files, and can be built to run locally,
whoo~!


## Getting started

First you'll need an Essay template, which will define how your documentation
will look. We can generate one for you, so run:

    $ essay new

This will create a new Essay documentation project in the current
folder. Templates are just regular Clojure functions that take in some file and
spit out some HTML.

All documents live in the `source/` folder, and will be passed to the
corresponding templates to be processed.

To generate the documentation, run:

    $ essay generate

Or to start a Jetty server that'll automatically compile things for you, run:

    $ essay server


## Licence

MIT.

