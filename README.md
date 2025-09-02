# thread-exercise-new
## step1-customer
### Class. customer class
### Env. pom.xml
- junit
- lombok
- logback-classic

## step2-cart,cartItem
### Class. Cart, CartItem(Record)
### Test. test code 
- junit(param), parameterized
### Note. Serializable

# step3 - entering queue(입장대기큐 생성)
### Class. EnteringQueue
- Blocking 기능을 하는 큐로 구현
### Test. test code
- Thread.sleep() 대신에 org.awaitility 사용
  - 테스트 코드에서 sleep()을 사용해서 테스트 코드 실행시 대기 상태가 안끝나는 상황 발생

# step4 - customer generator
### Class. CustomerGenerator
- 외부 라이브러리를 사용해서 랜덤 customer 생성
### Test. test code