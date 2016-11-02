# class-funsion-maven-plugin

## 1.build

```
mvn clean install
```

## 2.simple test

```
cd class-fusion-maven-plugin-example
mvn exec:java
```

output

```
User{ id=0, name=xausky, email=xausky@gmail.com }
```

## 3.spring test

```
cd class-fusion-maven-plugin-example-spring
mvn exec:java
```

output

```
Menu{ name=mine,user=User{ id=0, name=xausky, email=xausky@gmail.com } }
```