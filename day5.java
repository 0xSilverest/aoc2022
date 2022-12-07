package day5;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class day5 {
  static class Tuple2<T1, T2> {
    public T1 _1;
    public T2 _2;

    public Tuple2(T1 _1, T2 _2) {
      this._1 = _1;
      this._2 = _2;
    }
  }

  static class Move {
    int numberOfCrates;
    int from;
    int to;

    public Move(int numberOfCrates, int from, int to) {
      this.numberOfCrates = numberOfCrates;
      this.from = from - 1;
      this.to = to - 1;
    }
  }

  static enum CrateMoverType {
    CrateMover9000,
    CrateMover9001
  }

  public static Tuple2<List<String>, ArrayList<String>> splitInput(List<String> input) {
    ArrayList<String> head =
        input.stream()
            .takeWhile(s -> !s.isEmpty())
            .collect(Collectors.toCollection(ArrayList::new));
    ArrayList<String> tail =
        input.stream()
            .dropWhile(s -> !s.isEmpty())
            .skip(1)
            .collect(Collectors.toCollection(ArrayList::new));
    Collections.reverse(head);
    return new Tuple2<>(head.subList(1, head.size()), tail);
  }

  public static ArrayList<ArrayList<Character>> parseStacks(List<String> input) {
    ArrayList<String> newInput = new ArrayList<>();
    for (int i = 0; i <= input.get(0).length() - 3; i += 4) {
      int ind = i;
      newInput.add(
          input.stream().map(s -> s.substring(ind, ind + 3)).collect(Collectors.joining(" ")));
    }

    return newInput.stream()
        .map(
            s ->
                Arrays.stream(s.split("\\s+"))
                    .filter(s1 -> !s1.isEmpty())
                    .map(cs -> cs.charAt(1))
                    .collect(Collectors.toCollection(ArrayList::new)))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static ArrayList<Move> parseMoves(List<String> input) {
    Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    return input.stream()
        .map(
            s -> {
              Matcher matcher = pattern.matcher(s);
              if (matcher.find()) {
                return new Move(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)));
              } else {
                throw new RuntimeException("Invalid input");
              }
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static ArrayList<ArrayList<Character>> crateMover(
          ArrayList<ArrayList<Character>> stacks, Move move, Object type) {

    ArrayList<Character> fromStack = stacks.get(move.from);
    ArrayList<Character> toStack = stacks.get(move.to);

    ArrayList<Character> cratesToMove =
        new ArrayList<>(
            fromStack.subList(fromStack.size() - move.numberOfCrates, fromStack.size()));

    if (type == CrateMoverType.CrateMover9000) {
      Collections.reverse(cratesToMove);
    }

    stacks.set(
        move.from, new ArrayList<>(fromStack.subList(0, fromStack.size() - move.numberOfCrates)));
    toStack.addAll(cratesToMove);
    return stacks;
  }

  public static void main(String[] args) {

    Function<CrateMoverType, String> solution =
        crateMoverType -> {
          List<String> lines;
          try {
            lines =
                Files.readAllLines(new File("./input/day5.txt").toPath(), StandardCharsets.UTF_8);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          Tuple2<List<String>, ArrayList<String>> inputPair = splitInput(lines);
          ArrayList<ArrayList<Character>> stacks = parseStacks(inputPair._1);
          ArrayList<Move> moves = parseMoves(inputPair._2);
          return moves.stream()
              .reduce(
                  stacks,
                  (stacks1, move) -> crateMover(stacks, move, crateMoverType),
                  (stacks1, stacks2) -> stacks2)
              .stream()
              .map(stack -> stack.get(stack.size() - 1))
              .collect(
                  Collector.of(
                      StringBuilder::new,
                      StringBuilder::append,
                      StringBuilder::append,
                      StringBuilder::toString));
        };

    // Part 1
    System.out.println("part 1 : " + solution.apply(CrateMoverType.CrateMover9000));

    // Part 2
    System.out.println("part 2 : " + solution.apply(CrateMoverType.CrateMover9001));
  }
}
