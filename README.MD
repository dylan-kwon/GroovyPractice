# Groovy

<a name="index"></a>
## Index
1. [설치](#install)
    1. [Mac](#install_mac)
    2. [Windows](#install_windows)
2. [실행](#execute)
3. [IDE](#ide)
4. [간단한 특징](#summary)
5. [문법](#syntax)
    1. [String](#string) 
    2. [List](#list)
    3. [Array](#array)
    4. [Map](#map)
    5. [Method](#method)
    6. [if-else](#if-else)
    7. [switch-case](#switch-case)
    8. [loop](#loop)
    9. [try-catch-finally](#try-catch-finally)
6. [Reference](#reference)


<a name="install"></a>
## [설치](#index) 


<a name="install_mac"></a>
### [Mac](#index) 
`brew install groovy`


<a name="install_windows"></a>
### [Windows](#index)
`http://groovy-lang.org/download.html`


<a name="execute"></a>
## [실행](#index)
`> groovy {file_name}.groovy {arg1} {arg2} {arg3}`
</br>
`> groovy TestRun.groovy seokchan.kwon 21`


<a name="ide"></a>
## [IDE](#index)
1. IntelliJ: `https://www.jetbrains.com/idea`
2. Eclipse: `그냥 킹갓 IntelliJ 씁시다.`


<a name="summary"></a>
## [간단한 특징](#index) 
- 내가 싫어하는 동적 타이핑 언어다.
- `primitive type`이 없으며, 모두 객체로 간주한다.
- 세미콜론`;` 생략 가능.
- 함수 호출시 괄호`()` 생략 가능.
- 타입 생략 가능. `def` 키워드를 사용하지만, 이것도 생략 가능.
- 클로저`(Closure)`
- 람다 함수의 기본 변수로 `it` 사용

<a name="syntax"></a>
## [문법](#index) 

<a name="string"></a>
### [1. String](#index) 
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

<a name="list"></a>
### [2. List](#index) 
- `동적 배열`이다.
- `[]`를 사용하여 선언한다.
- 타입 제한이 없다.
- `index`로 데이터를 저장하고 접근한다.
- 후술할 `Array`와 다르다.

```groovy
def list = [1, "a", true]               // java.util.ArrayList
def linked = [1, 2, 3] as LinkedList    // java.util.LinkedList 
```


<a name="array"></a>
### [3. Array](#index) 
- `정적 배열`이다.
- `[]`를 사용하여 선언한다.
- 같은 타입의 원소만 허용한다
- `index`로 데이터를 저장하고 접근한다.

```groovy
String[] array = ['one', 'two', 'three']
```

<a name="map"></a>
### [4. Map](#index) 
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


<a name="method"></a>
### [5. Method](#index) 

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

<a name="if-else"></a>
### [6. if-else](#index) 
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


<a name="switch-case"></a>
### [7. switch-case](#index) 
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


<a name="loop"></a>
### [8. loop](#index)
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


<a name="try-catch-finally"></a>
### [9. try-catch-finally](#index) 
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


<a name="reference"></a>
## [Reference](#index) 
- [http://groovy-lang.org/semantics.html#](http://groovy-lang.org/semantics.html#)