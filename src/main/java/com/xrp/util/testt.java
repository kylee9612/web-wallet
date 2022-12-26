package com.xrp.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    int number;
    Map<Integer, Node> nodeMap = new HashMap<>();

    int findPath(int target, int depth, Map<Integer, Boolean> checked) {
        if (nodeMap.containsKey(target)) {
            return depth + 1;
        }
        int min = 0;
        for (int i : nodeMap.keySet()) {
            if (!checked.containsKey(i) || !checked.get(i)) {
                checked.put(i, true);
                int dep = nodeMap.get(i).findPath(target, depth + 1, checked);
                if (dep < min || min == 0) {
                    min = dep;
                }
                checked.put(i,false);
            }
        }
        return min;
    }
}

class Solutio {
    public static int solution(int n, int[][] edge) {
        Map<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < edge.length; i++) {
            for (int j = 0; j < edge[i].length; j++) {
                if (!map.containsKey(edge[i][j])) {
                    map.put(edge[i][j], new Node());
                    map.get(edge[i][j]).number = edge[i][j];
                }
            }
            Node node = map.get(edge[i][0]);
            Node conn = map.get(edge[i][1]);
            node.nodeMap.put(edge[i][1], conn);
            conn.nodeMap.put(edge[i][0], node);
        }

        Node point = map.get(1);
        List<Node> list = new ArrayList<>();
        int max = 0;
        for (int i : map.keySet()) {
            if (i != 1) {
                Map<Integer, Boolean> checked = new HashMap<>();
                checked.put(i, true);
                int depth = point.findPath(i, 0, checked);
                if (depth > max) {
                    max = depth;
                    list = new ArrayList<>();
                }
                if (depth >= max) {
                    list.add(map.get(i));
                }
                checked.put(i, false);
            }
        }
        return list.size();
    }
}

public class testt {

    public static void main(String[] args) {
        int point = 6;
        int[][] path = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};
        System.out.println(Solutio.solution(6, path));
    }
}
