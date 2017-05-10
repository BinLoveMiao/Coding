package interview.leetcode.dfs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 399: Evaluate Division <br> <br>
 * 
 * Equations are given in the format A / B = k, where A and B are variables represented as strings,
 * and k is a real number (floating point number). Given some queries, return the answers. If the
 * answer does not exist, return -1.0. <br> <br>
 * 
 * Example: Given a / b = 2.0, b / c = 3.0. Queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?,
 * x / x = ? . Return [6.0, 0.5, -1.0, 1.0, -1.0 ]. <br> <br>
 * 
 * Assume that there is no zero results, and no conflict.
 * 
 * @author robin
 *
 */
public class EvaluateDivision {
  public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
    int len = values.length;
    if (len == 0) {
      return new double[] {};
    }
    Map<String, Map<String, Double>> mapOfEquation = new HashMap<String, Map<String, Double>>();
    String[] equation = null;
    for (int i = 0; i < len; ++i) {
      equation = equations[i];
      double value = values[i];
      if (!mapOfEquation.containsKey(equation[0])) {
        mapOfEquation.put(equation[0], new HashMap<String, Double>());
      }
      if (!mapOfEquation.containsKey(equation[1])) {
        mapOfEquation.put(equation[1], new HashMap<String, Double>());
      }
      mapOfEquation.get(equation[0]).put(equation[1], value);
      mapOfEquation.get(equation[1]).put(equation[0], 1 / value);
    }

    double[] results = new double[queries.length];
    String[] query = null;
    Set<String> visited = new HashSet<String>();
    for (int i = 0; i < queries.length; ++i) {
      query = queries[i];
      if (!mapOfEquation.containsKey(query[0])) {
        results[i] = -1.0;
      }
      visited.clear();
      results[i] = dfs(mapOfEquation, query[0], query[1], visited);
    }
    return results;
  }

  private double dfs(Map<String, Map<String, Double>> mapOfEquation, String source, String dest,
      Set<String> visited) {
    if (!mapOfEquation.containsKey(source)) {
      return -1.0;
    }
    if (mapOfEquation.get(source).containsKey(dest)) {
      return mapOfEquation.get(source).get(dest);
    }
    Map<String, Double> entries = mapOfEquation.get(source);
    for (Map.Entry<String, Double> entry : entries.entrySet()) {
      if (visited.contains(entry.getKey())) {
        continue;
      }
      visited.add(entry.getKey());
      double temp = dfs(mapOfEquation, entry.getKey(), dest, visited);
      if (temp != -1) {
        return entry.getValue() * temp;
      }
    }
    return -1.0;
  }
}
