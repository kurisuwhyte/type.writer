type.writer
===========

type.writer is the simplest documentation tool that could possibly work, but
with a little bit of flexibility. It's based on [@rstacruz's Flatdoc][Flatdoc],
and allows you to just dump Markdown files in a directory and get a
good-looking documentation back. No messing with output formats,
configurations, or anything else.

[Flatdoc]: http://ricostacruz.com/flatdoc

## Philosophy

 -  **Hassle-free**: no need for servers or intricate configuration;
 -  **Simple**: just dump Markdown files in a directory. Done;
 -  **Work off-line**: help the poor souls who code in the airport!
 -  **Readable & good-looking**: [Flatdoc][] is gorgeous and responsive!;
 -  **Easy to install anywhere**: Just need a working JRE 7.


## Installing

Currently the best way to install type.writer is by hand:

 1.  Make sure you have a [Java JRE 7][JRE];
 2.  [Download the latest release][release];
 3.  Place `bin/typewriter` somewhere in your `$PATH`.
 
> **Note**: it only works on Unix systems right now.

[JRE]: http://www.java.com/en/download/index.jsp
[release]: https://github.com/kurisuwhyte/type.writer/releases/download/v0.2.0/typewriter-0.2.0.tar.gz


## Basic Usage

type.writer uses a simple project structure, which you can generate running:

    $ typewriter new

After that, put the relevant meta-data in the `project.edn` file, dump your
`*.md` markdown files in the `source/` folder and run:

    $ typewriter build
    
    
## Documentation

You can either [check out the documentation online][docs], or build the
documentation locally, by running:

    $ cd docs
    $ typewriter build


[docs]: http://kurisuwhyte.github.com/type.writer
    

## Development

If you want to hack into the code, you'll also need [Clojure 1.4+][Clojure] and
[Leiningen 2][lein].

The work-flow follows the usual fork the repository, make a branch, commit your
changes, make sure the tests pass and submit a pull request on Github. Please,
make atomic pull requests, so use different branches & pull-requests for
different features.

[Clojure]: http://clojure.org/
[lein]: http://leiningen.org/


## Acknowledgements

 -  The default theme is based on [Rico Sta. Cruz's Flatdoc][Flatdoc], with
    some small changes to accommodate rendering off-line and Enlive processing.
    

## Licence

MIT.
