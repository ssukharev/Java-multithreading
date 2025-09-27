<summary><h2>🎯 The task:</h2></summary>

Create a message queue to which N threads write (the number of threads is set via args), and N threads read, using only the [java.util.concurrent](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html) package. 

Thread names must be meaningful, and it is forbidden to use constructions with wait/notify.



## Project structure
For illustration, here is the project structure:

```
.
├── message_queue.log
├── out
│   └── production
│       └── Task2_OOP
├── README.md
├── src
│   └── main
│       ├── Consumer.java
│       ├── LoggerConfig.java
│       ├── Main.java
│       ├── MessageQueue.java
│       └── Producer.java
└── Task2_OOP.iml
```
