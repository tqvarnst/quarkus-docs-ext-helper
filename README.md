# quarkus-docs-ext-helper

This project provides a helper tool to help list the support level of an extension in Red Hat build of Quarkus

## Usage

_This sections assums you have installed and and have quarkus-docs-ext-helper on your path_

To check if for example `quarkus-resteasy-reactive` is supported in Red Hat build of Quarkus 3.2 
(support status of the latest version in that stream will be used) you can for example use

    quarkus-docs-ext-helper -S 3.2 -e quarkus-resteasy-reactive

You can also use pipes with xargs like this:

    echo "quarkus-resteasy-reactive" | xargs quarkus-docs-ext-helper -S 3.2 -e

## Installation

1. Download the latest release from https://github.com/tqvarnst/quarkus-docs-ext-helper/releases matching your OS and CPU architecture
2. Copy the file to a directory that is part of your PATH, for example `/usr/local/bin`
3. Make the file executable `sudo chmod +x /usr/local/bin/quarksu-docs-ext-helper`
4. For Mac OS users, you might have to remove the quarantine that Apple enforces on unidentified applications using
`sudo xattr -r -d /usr/local/bin/com.apple.quarantine quarkus-docs-ext-helper`


