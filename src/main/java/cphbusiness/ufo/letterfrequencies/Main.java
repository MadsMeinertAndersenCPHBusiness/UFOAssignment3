package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String fileName = "C:\\Users\\madsm\\OneDrive\\Dokumenter\\CPHBusinessGit\\UFOAssignment3\\letterfrequencies-master\\src\\main\\resources\\FoundationSeries.txt";


        try (Stopwatch sw = new Stopwatch()) {
            Map<Integer, Long> freq = new HashMap<>();
            var pre = sw.step();
            Reader reader = new FileReader(fileName);
            var post = sw.step() - pre;
            tallyChars(reader, freq);
            var postTally = sw.step() - post;
            System.out.println("TIME FOR FileReader: " + postTally);
        }
        try (Stopwatch sw = new Stopwatch()) {
            Map<Integer, Long> freq = new HashMap<>();
            var pre = sw.step();
            Reader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            var post = sw.step() - pre;
            tallyChars3(br, freq);
            var postTally = sw.step() - post;
            System.out.println("TIME FOR BufferedReader: " + postTally);
        }


    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
    }
    private static void tallyChars2(BufferedReader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
    }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));
            ;
        }
    }

    private static void tallyChars3(BufferedReader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            freq.putIfAbsent(b, 0L);
            freq.put(b, freq.get(b) + 1);
        }
    }
}
