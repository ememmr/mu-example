
# mu-example

This repository contains three examples using [mu].

##simple-service/simple-client
It uses:
- cats library
- log4cats library
- mu-rpc-netty library
- mu-rpc-server library
- mu-rpc-channel library
- mu-rpc-testing library
    - scalatest library
- mu-rpc-dropwizard library
    - http4s-prometheus-metrics library
    - http4s-blaze-client library
    - http4s-blaze-server library

Run first,
```
sbt server/run
```
after that,
```
sbt client/run
```

##fs2-service/fs2-client
It uses:
- cats library
- log4cats library
- mu-rpc-netty library
- mu-config library
- mu-rpc-server library
- mu-rpc-channel library
- mu-config library
- mu-rpc-fs2 library
    - fs2-core library
- mu-rpc-prometheus library `TODO`
    - http4s-prometheus-metrics library
    - http4s-blaze-client library
    - http4s-blaze-server library
 
Run first,
```
sbt fs2server/run
```
after that,
```
sbt fs2client/run
```

##monix-service/monix-client
It uses:
- cats library
- log4cats library
- mu-rpc-netty library
- mu-config library
- mu-rpc-server library
- mu-rpc-channel library
- mu-config library
- mu-rpc-monix library
    - monix library
    
 
Run first,
```
sbt monixServer/run
```
after that,
```
sbt monixClient/run
```

[mu]: https://higherkindness.github.io/mu/