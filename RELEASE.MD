# Release instructions

## Release process

1. Check-in all changes you plan to release
2. Run the maven prepare step

`./mvnw release:prepare`

3. If everything looks good run the perform step, skipping the deploy step

`./mvnw release:perform -Darguments="-Dmaven.deploy.skip=true"`

4. Go to https://github.com/tqvarnst/rhbq-supported-extension-generator/releases and click **Draft a new release**
5. Choose the recently created tag for the version you released.
6. Give it a good title (e.g. release number X.Y.Z) and a good description of what is new in the release
7. Click publish release
8. Go to https://github.com/tqvarnst/rhbq-supported-extension-generator/actions and wait for the work to complete
9. You will have to manually upload a MacOS aarch64 binary by building locally and upload it to the release.

