# Picon
Fixture Setup through Object Notation for Implicit Test  Fixtures

# Dependencie Install


<dependency>
    <groupId>com.github.douglashiura</groupId>
    <artifactId>picon</artifactId>
    <version>5.0.2</version>
</dependency>


# Usage Example 

## file.picon
```
  com.package.User{
      mary[name='Mary Joe']
  }
```
## Class.java

```
@ExtendWith(PiconRunner.class)
class Class {
  User mary;
  @Test
  void userNotNull(){
    assertNotNull(mary);
  }
}
```


