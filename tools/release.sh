#!/bin/bash
# Copies stuff to dist/ and packs everything up as a tarball. Whoo!
if [ ! -f "./project.clj" ]; then
    echo "This has to be run from the project root."
    exit 1
fi

echo "Recompiling the project..."
lein uberjar

PROJECT_VERSION="$(java -jar target/typewriter*standalone.jar --version | cut -d " " -f 2)"
TARGET_DIR="dist/typewriter-$PROJECT_VERSION"

echo "Copying stuff to dist/..."
mkdir -p "$TARGET_DIR"
cp -r bin "$TARGET_DIR"
cp -r resources "$TARGET_DIR"
cp target/typewriter*standalone.jar "$TARGET_DIR/typewriter.jar"
cp LICENCE "$TARGET_DIR"
cp README.md "$TARGET_DIR"

echo "Generating the documentation and copying it over..."
cd docs && "../$TARGET_DIR/bin/typewriter" build && cd ..
cp -r docs/build "$TARGET_DIR/docs"

echo "Generating the gzipped tarball..."
cd "$TARGET_DIR" && cd ..
tar -czf "../target/typewriter-$PROJECT_VERSION.tar.gz" typewriter*

echo "Done!"
