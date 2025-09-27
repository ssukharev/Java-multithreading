<summary><h2>🎯 The task:</h2></summary>

Create a supervisor (control program) that monitors the execution of an abstract program.

An abstract program runs in a separate thread and is a class with an enumerated type field that reflects its state.

● UNKNOWN – before the first launch

● STOPPING – stopped

● RUNNING – running

● FATAL ERROR is a critical error

and has a random state daemon thread that changes its state to random at a given interval.

The supervisor must have methods for stopping and starting an abstract program that change its state. 
The supervisor it is a thread that cyclically polls an abstract program, and if its state is STOPPING, it restarts it. 

If the condition is FATAL ERROR, then the supervisor terminates the work of the abstract program. 

All status changes must be accompanied by appropriate messages in the console.
The supervisor should not miss any status of the abstract program. Use constructions with wait/notify.



## Project structure
For illustration, here is the project structure:

```
.
├── out
│   └── production
│       └── Task1_OOP
├── program.log
├── README.md
├── src
│   └── main
│       ├── AbstractProgram.java
│       ├── LoggerConfig.java
│       ├── Main.java
│       ├── ProgramState.java
│       └── Supervisor.java
└── Task1_OOP.iml

```
