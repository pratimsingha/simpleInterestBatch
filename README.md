This is a Batch Job that reads Principal, time and rate from a source file
and the saves the output in the MySQL database.

- Read the Principal, time and rate from a source file whose filename and filepath
are addressed in the application properties file.
- Trigger a rest service which takes the above 3 field values and gives the simple interest as an output.
- Save the Principal, Rate, time and the result in the database whose table name is InterestModel.