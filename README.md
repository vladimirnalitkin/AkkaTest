# Akka test

I took simple stack(last input first output)

IStack - interface, with 2 simple methods: push and pop 

SimpleStack - simple implementation of IStack. This implementation is not thread safe.

UnblockStack - thread-safe implementation based on CAS algorithm.

BlockingStack - synchronized implementation 

## Run Tests

```
mvn test
```
