This is the code that runs on the raspberry pi, and does the actual vision processing.
It receives the targets it needs to search for through a NetworkTablesDatasource, and
	finds the targets. It then adds data to the targets, and pushes the data back up
	to NetworkTables.
It uses a few settings...
Note: Once the code is running (robotStatus = READY), these cannot be changed. Any changes will be ignored.
	status: A string representing the current status of the vision processing.
	calibPatternSizeX/Y: A string representing the number of internal corners in the calibration pattern.
	calibSquareSize: A string representing the real world size of a single square on the calibration pattern.
		Note: Units do not matter, but this value is used to return the data. So, if this is in inches, the returned data will be in inches.
	robotStatus: Status of the robot code. Needs to be set to "READY" to start vision processing.
	