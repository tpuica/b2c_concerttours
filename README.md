# Local setup

1. Clone this repository.
2. Download Hybris 2105 ZIP
3. Unpack the ZIP in the root directory of the repository.
4. Go to the `installer` subdirectory.
5. Run `install.bat -r b2c_concerttours initialize` (default admin password 'admin' will be set, cannot be overriden with property but can be changed in installer/customconfig/custom.properties file prior to install command execution). 
6. Go to the `hybris/bin/platform` directory.
7. Run `setantenv.bat`.
8. Run `hybrisserver.bat` to start Hybris.

