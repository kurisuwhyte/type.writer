---
layout: plain.html
---

type.writer Guide
=================

type.writer is a documentation tool that converts Markdown files into beautiful
HTML documentation, which you can either view locally or deploy right up to
your website or your project's Github Pages. You can definitely just dump all
your `*.md` files in the `source/` folder, run `typewriter build` and be done
with it, however there is some room for customisation.

There are three simple concepts involved in typewriter:

 -  **Theme**: the HTML, CSS, JavaScript, etc. that determines how your
    documentation will look in the browser.

 -  **Documents**: the Markdown files with the actual documentation.

 -  **Meta-data**: project and document, like the title of the documentation,
    or which layout to use for a document.


## Themes

A theme in typewriter is just a folder containing the HTML layouts, in a
`layout/` folder, and any static resources in a `static/` folder:

    + /<theme-name>
    |--o project.edn            Meta-data for your project;
    |--o /layout                Place your .html files here;
    `--o /static                Place images, CSS, etc. here;

When you create a project based on a template, these files will be copied over
to the project folder, which will have this structure:

    + /<project-name>
    |--o project.edn
    |--o /source                Markdown documents
    |--o /layout
    `--o /static

While the `static/` folder is just copied as-is when you build the
documentation, the HTML documents in `layout/` are processed with [Enlive][],
and some CSS classes are used to mark elements as placeholders for contents.

[Enlive]: https://github.com/cgrand/enlive

When you run `typewriter build`, these layouts will be processed, filled with
content from the documents, and placed into a `build/`, folder, along with the
`static/` folder. For example:

    + /<project-name>                            o /build
    |--o project.edn    ====== becomes ===>      |--o index.html
    |--+ source/                                 |--o guide.html
    |  |--o index.md                             `--+ /static
    |  `--o guide.md                                `--o style.css
    |--+ /layout
    |  `--o index.html
    `--+ /static
       `--o style.css
       
       
### Layouts

Layouts are plain HTML files, with no special markup whatsoever. They are
processed using [Enlive][], which means that they are parsed and transformed
based on CSS-style selectors. With a few exceptions, all element selections are
based on CSS classes.

As an example, this layout:

    <!DOCTYPE html>
    <html>
        <head>
            <title></title>
        </head>
        <body>
            <h1 class="tw-title"></h1>
            <div class="tw-source"></div>
        </body>
    </html>

Could become this:

    <!DOCTYPE html>
    <html>
        <head>
            <title>My Project</title>
        </head>
        <body>
            <h1 class="tw-title">My Project</h1>
            <div class="tw-source">
                The documentation source goes here.
            </div>
        </body>
    </html>

Tags like `<title>` and `<meta>` are handled specially by the processor and
don't need a `class` declaration. All other elements use classes exclusively
for defining how they are to be processed.


 -  **.tw-title**: Defines a placeholder for the title of the document.

 -  **.tw-source**: Defines a placeholder for the Markdown source.

 -  **.tw-navigation**: Defines a placeholder for project's links. The structure
    of the navigation is defined by a `.tw-item` element, which will be replied
    for each link in the project, whereas the text and URL of the link will be
    placed in a `.tw-link` element.


## Documents

Documents are plain Markdown files (with the `.md` extension) placed in the
`source/` folder. Do note that typewriter doesn't do a recursive search for
Markdown files inside of this folder, so all documents have to be placed
directly in `source/`.

A document may contain an [YAML][] document, describing meta-data for that
document alone (this is how, for example, you use a layout for a specific
document). YAML documents start with a `---` line, and ends with a `---` line:

    ---
    layout: subpage.html
    ---

    ## Markdown goes here

[YAML]: http://yaml.org/


## Meta-data

Meta data can be defined per-project and per-document. This is the data that
gets passed to the processor when dealing with the layouts. Project-level
meta-data is defined in the `project.edn` at the root of a project folder,
whereas document-specific meta-data is defined as an YAML snippet in each
document.

Any meta-data not specified directly in a document is taken from the project
meta-data. So, effectively, document meta-data is a way to override
configuration for just one document.

Available configuration:

 -  `String` **title**: the project's title.

 -  `String` **description**: the project's description.

 -  `String` **layout**: the name of the layout to use.

 -  `[{String :text, String :url}]` **links**: project links, like Github
    repository or issue tracker.
