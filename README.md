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
* Sends request for "Who Am I?" request
* Ensures that id is returned from hub in the case of this request.
* Ensures that a connection is able to exit by emitting "."


List Test Positive Scenario
--------------------------

