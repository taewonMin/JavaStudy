package practice;

import java.util.LinkedList;

public class ProgrammersTest {
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		System.out.println(sol.solution(2, 10, new int[]{7,4,5,6}));
	}
}

class Solution {
	LinkedList<Integer> move = new LinkedList<>();	// 큐
	LinkedList<Integer> pos = new LinkedList<>();	// 큐
	int totalWeight = 0;	// 다리위 트럭들의 무게
	int cnt = 0;
	int weight;
	int[] truck_weights;
	
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        this.weight = weight;
        this.truck_weights = truck_weights;
        
		do {
        	answer++;
//        	System.out.println(answer);
        	for(int i=0; i<pos.size(); i++) {
        		pos.set(i, pos.get(i)+1);
        	}
    		addMove();
        	if(pos.get(0) == bridge_length + 1) {
        		totalWeight -= move.poll();
        		pos.poll();
        		addMove();
        	}
//        	System.out.println(move);
//        	System.out.println(pos);
        } while(!move.isEmpty());
        
        return answer;
    }
    
    public void addMove() {
    	if(cnt < truck_weights.length && totalWeight + truck_weights[cnt] <= weight) {
    		int temp = truck_weights[cnt++];
    		totalWeight += temp;
    		move.offer(temp);
    		pos.offer(1);
    	}
    }
}