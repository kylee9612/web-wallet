package com.xrp.util;

import java.util.*;

class Member {
    Member referral = null;
    int profit = 0;
    Map<String, Member> members = new HashMap<>();

    @Override
    public String toString() {
        return "profit = " + profit + ", members = " + members;
    }
}

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];

        Map<String, Member> map = new HashMap<>();

        for (int i = 0; i < referral.length; i++) {
            if (!map.containsKey(enroll[i])) {
                map.put(enroll[i], new Member());
            }
            Member newMember = map.get(enroll[i]);
            if (!referral[i].equals("-")) {
                Member refer = map.get(referral[i]);
                newMember.referral = refer;
                refer.members.put(enroll[i], newMember);
            }
        }

        for (int i = 0; i < seller.length; i++) {
            int profit = (int) (amount[i] * 0.9);
            System.out.println(seller[i]);
            map.get(seller[i]).profit += profit;
            Member next = map.get(seller[i]).referral;
            while (next != null) {
                next.profit += profit;
                next = next.referral;
                profit = (int) (profit * 0.9);
            }
        }

        for (int i = 0; i < enroll.length; i++) {
            answer[i] = map.get(enroll[i]).profit;
        }
        return answer;
    }
}

public class test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = new int[]{360, 958, 108, 0, 450, 18, 180, 1080};
        int[] answer = solution.solution(enroll, referral, seller, amount);
        System.out.println(Arrays.toString(answer));
    }
}