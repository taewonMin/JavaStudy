package practice;

import java.util.HashSet;
import java.util.Set;

/**
 	HashSet에 element가 저장되는 방식
 	
 	- HashSet은 내부적으로 HashMap으로 구현되어 있다.
 	- 저장 형식은 key, value, next(다음 노드를 가리킴)쌍인 '노드'로 저장
 	- Set의 경우 value가 없으므로 add메서드를 실행하면 key값이 저장되고 value에는 더미 데이터, next는 null로 저장됨.
 	- key가 저장될 때 key를 hashcode로 변환하여 중복 체크
 	
 	HashSet의 초기 크기를 설정하지 않으면 -> capacity:16 / load factor:0.75로 초기화
 		capacity = 초기 크기
 		load factor = 실제 저장된 데이터의 수 / capacity = 데이터가 저장된 비율
 		설정한 load factor보다 커지면 capacity가 두배로 늘어남.
 	HashMap에 데이터가 저장될 때 index는
 	
 	index = key.hashCode() % capacity
 	
 	로 구해진다. 따라서 만약 set.add(16)라는 코드를 실행한다면
 	key값(16)이 hashCode()로 변환되는데, 16이 int형이므로 hashcode = 16 이 된다.
 	capacity가 16이므로 결과적으로 index는 
 	
 	16 % 16 = 0
 	
 	따라서 0번 index에 16이 저장됨.
 	마찬가지로 set.add(17) -> 1번 인덱스, set.add(34) -> 2번 인덱스에 각각 저장된다.
 	그런데 set.add(1)의 경우, 이미 1번 인덱스에는 17이 저장되어있으므로 바로 저장할 수 없다.
 	이런 경우에는 17노드 앞에 삽입하여, 1의 next를 17로 설정해준다. (LinkedList로 구현되어있음)
 	
 	set을 출력하면 인덱스 순으로, 같은 인덱스에서는 선입선출로 출력된다.
 */
public class HashSetTest {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		set.add(16);
		set.add(17);
		set.add(34);
		set.add(1);
		set.add(36);
		set.add(18);
		set.add(49);
		System.out.println(set);
		// [16, 17, 1, 49, 34, 18, 36]
	}
}

//참고 : https://onsil-thegreenhouse.github.io/programming/java/2018/02/22/java_tutorial_HashMap_bucket/