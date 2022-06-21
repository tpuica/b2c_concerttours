# Local setup

1. Clone this repository.
2. Download Hybris 2105 ZIP
3. Unpack the ZIP in the root directory of the repository.
4. Go to the `installer` subdirectory.
5. Run `install.bat -r b2c_concerttours initialize -A local_property:initialpassword.admin=<your_password>`.
	5.a. Alternatively simply run `install.bat -r b2c_concerttours initialize` (default admin password 'admin' will be set instead). 
7. Go to the `hybris/bin/platform` directory.
8. Run `setantenv.bat`.
9. Run `hybrisserver.bat` to start Hybris.

