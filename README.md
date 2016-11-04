# Rosalie Stevenson's Messaging Hub For Sockets
Description of Messaging Hub Base Code
======================================
*	Creates socket at port 5000
*	Creates an asynchronous runnable task when a connection is accepted to this port.
*	Runs said asynchronous task in executable thread pool.
*	Assigns connection to to an arbitrary random number as a user id.
*	Adds id to synchronized list of user ids.
*	Adds output writer in socket handler to hash map, mapped to user id.
*	Checks that the command input by the connection matches one of the following commands:

1.	Who Am I?
2.	Who is here?
3.	Send __message__ to __other_connections__

*	In the case that the command does not match one of the listed commands, print error message.

Tests
===========

Identity Test Positive Scenario
-------------------------------
* Creates connection with port 5000
* Sends single "Who Am I?" request
* Ensures that id is returned from hub in the case of this request.
* Ensures that a connection is able to exit by emitting "."

List Test Positive Scenario
--------------------------
* Creates two connections to port 5000
* Checks id of first and second connections by sending "Who Am I?" requests.
* Uses second connection to emit "Who is here?" request.
* Ensure that first ID is included in responding array and that second ID is excluded.

List Test Single Connection
---------------------------
* Creates single connection to port 5000.
* Emits "Who is here?" request.
* Ensure that empty array responds to request for user ID array.

Relay Message Test Closed Connections
------------------------------------
* Creates three connections to port 5000.
* Emits "Who is here?" request through third connection.
* Closes first and second connections.
* Attempts to send messages to remaining connections by emitting "Send foobar to" request.
* Checks that both the first and second connections receive "foobar".
* Checks that sending these messages does not cause an error.

Relay Messages Non-Existent Connections
--------------------------------------
* Creates three connections to port 5000
* Alters ids associated with these connections by adding a single increment to them.
* Attempt to send messages to these particular incremented, non-existent connections.
* Ensures that the message retrieved from the hub matches an expected error message.

Relay Message Test Positive Scenario
-----------------------------------
* Create three connections to port 5000
* Uses 1st connection to get user ids associated with other connections.
* Sends "foobar" to the other two connections.
* Ensures that other two connections receive the "foobar" message.
