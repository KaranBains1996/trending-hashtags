package com.karanbains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String HASHTAG_REGEX = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Integer> tagMap = new HashMap<>();

        while (true) {
            System.out.println("Write a tweet:");
            String tweet = reader.readLine();

            Pattern pattern = Pattern.compile(HASHTAG_REGEX);
            Matcher matcher = pattern.matcher(tweet);

            while(matcher.find()) {
                String hashtag = matcher.group().toLowerCase();
                if(tagMap.containsKey(hashtag)) {
                    tagMap.put(hashtag, tagMap.get(hashtag) + 1);
                } else {
                    tagMap.put(hashtag, 1);
                }
            }

            System.out.println("Write another tweet? (y/n):");
            String response = reader.readLine();

            while(!isValidResponse(response)) {
                System.out.println("Invalid response. Please type y to continue and n to stop:");
                response = reader.readLine();

                if (isValidResponse(response)) {
                    break;
                }
            }

            if (response.equalsIgnoreCase("n")) {
                break;
            }
        }

        HashMap<String, Integer> sortedTagMap = sortTagMap(tagMap);

        printTable(sortedTagMap);
    }

    public static boolean isValidResponse(String response) {
        return response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n");
    }

    public static HashMap<String, Integer> sortTagMap(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hm.entrySet());

        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        HashMap<String, Integer> temp = new LinkedHashMap<>();

        int itemCount = 0;
        for (Map.Entry<String, Integer> aa : list) {
            if (itemCount < 10) {
                temp.put(aa.getKey(), aa.getValue());
                itemCount += 1;
            } else {
                break;
            }
        }

        return temp;
    }

    public static void printTable(HashMap<String, Integer> tagMap) {
        System.out.println("Trending Hashtags:");
        // Add 1 to size to include header row
        final Object[][] table = new String[tagMap.size()+1][];
        table[0] = new String[] { "Hash Tag", "Count" };

        int i = 1;
        for (Map.Entry<String, Integer> entry : tagMap.entrySet()) {
            table[i] = new String[] {entry.getKey(), entry.getValue().toString()};
            i += 1;
        }

        for (final Object[] row : table) {
            System.out.format("%15s%15s\n", row);
        }
    }
}
