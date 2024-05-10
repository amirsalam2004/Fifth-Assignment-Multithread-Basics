#Question 3

An instance of the JoinThread class is created and assigned to the variable thread(Thread_0).
The execution of the run() method is started with theard.start() command.
At this time, both threads are running, then with the join() command,
the execution of main thread is stopped until the end of Thread_0 and This statement is printed:
```
Running in: Thread-0
```
When Thread_0 ends, main thread continues execution from the same place as before.
The print command is executed and this statement is displayed:
```
Back to: main
```