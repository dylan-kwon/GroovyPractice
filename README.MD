# Groovy

## 실행
`groovy {file_name}.groovy {arg1} {arg2} {arg3}`
</br>
`ex) groovy TestRun.groovy seokchan.kwon`

## 설치

### Mac
`brew install groovy`


### Windows
`http://groovy-lang.org/download.html`


## IDE
1. IntelliJ: `https://www.jetbrains.com/idea`
2. Eclipse: `그냥 킹갓 IntelliJ 씁시다.`


## 간략한 특징
- 내가 싫어하는 동적 타이핑 언어다.
- `primitive type`이 없으며, 모두 객체로 간주한다.
- 세미콜론`;` 생략 가능.
- 함수 호출시 괄호`()` 생략 가능.
- 타입 생략 가능. `def` 키워드를 사용하지만, 이것도 생략 가능.
- 클로저`(Closure)`
- 람다 함수의 기본 변수로 `it` 사용


## 문법 

### 1. String
- `'string'`
- `"string"`
- `/string/`
- `$/string$/`
- `'''string'''`
- `"""string"""`
- `$variable` or `${variable.method()}`

```groovy
def hello1 = 'string'
def hello2 = "string"
def hello3 = /string/

String hello4 = "hello, $hello1"
String hello5 = "hello, ${hello2.length()}"

def hello6 = """
이곳에서
여러줄의
문자열을
입력할 수 있다.
"""

String hello7 = $/ 
이곳에도
여러줄의
문자열을
입력할 수 있다.                
/$ 
```

### 2. List
- `동적 배열`이다.
- `[]`를 사용하여 선언한다.
- 타입 제한이 없다.
- `index`로 데이터를 저장하고 접근한다.
- 후술할 `Array`와 다르다.

```groovy
def list = [1, "a", true]               // java.util.ArrayList
def linked = [1, 2, 3] as LinkedList    // java.util.LinkedList 
```


### 3. Array
- `정적 배열`이다.
- `[]`를 사용하여 선언한다.
- 같은 타입의 원소만 허용한다
- `index`로 데이터를 저장하고 접근한다.

```groovy
String[] array = ['one', 'two', 'three']
```


### 4. Map
- `[:]`를 사용하여 선언한다.
- `key`:`value`로 구성된 자료구조다.

```groovy
def map1 = [:]

def map2 = [
    'key1':'value1',
    'key2':'value2',
    'key3':'value3'
]

// add
map1.key1 = 'value1'
map1['key2'] = 'value2'
map1.put('key3', 'value3')
map1 << ['key4':'value4']

// remove
map2.remove('key1')
map2 -= ['key2': 'value2']
```


### 5. Method

- 인수가 있는 메소드 호출 시 `()`를 생략할 수 있다.
- 인수가 없는 메소드 호출 시 `()`를 생략할 수 없다.

```groovy
println('hello, world!!') // success
println 'hello, world!!'  // success

println() // success
println   // groovy.lang.MissingPropertyException

public void println() {
    println('none message')
}

public void println(def message) {
    ...
}
```

### 6. if-else
- `Java`와 사용법이 같다.

```groovy
if ( ... ) {
    ...
    
} else if( ... ) {
    ...
    
} else {
    ...
}
```


### 7. switch-case
- 기본적으로는 `Java`와 사용법이 같다.
- 단, case문에서 아래와 같은 차이가 있다.

```groovy
switch (value) {
    case "case_a":
        ...
        break
    
    case 1..10:
        ...
        break
        
    case { value > 10}:  // or { it > 10}
        ...
        break
        
    case [1, 2 , "case_b"]:
        ...
        break
        
    case Integer:
        ...
        break
        
    default:
        ...
        break
}
```


### 8. Loop
- Groovy에는 여러 종류의 반복문이 있다.
- Java와 같은 `for`, `while`문.
- 모든 종류의 컬렉션에서 작동하는 `for-in`문

``` groovy
for (int i = 0; i < 10; i++) {
    ...
}

for (i in [0, 1, 2, 3, 4]) {
    ...
}

def list = [0, 1, 2, 3, 4]
for (i in list) {
    ...
}

def map = ['key':'value']
for (e in map) {
    e.key
    e.value
    ...
}

for (k in map.keySet()) {
    ...
}

for (v in map.values()) {
    ...
}

for (s in "string") {
    ...
}

while ( ... ) {
    ...
}
```


### 9. try-catch-finally
- `Java`와 비슷하다.

```
try {
    ...
    
} catch (e) {
    e.printStackTrace()
    
} finally {
    ...
}
```


## Reference
- `http://groovy-lang.org/semantics.html#`