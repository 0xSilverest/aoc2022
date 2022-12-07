{:ok, file} = File.read("./input/day6.txt")

findTheIndex = fn n -> n + (file |> String.to_charlist() |> Enum.chunk_every(n, 1, []) |> Enum.find_index(fn chars -> Enum.uniq(chars) == chars end)) end

IO.puts("part1 : #{findTheIndex.(4)}")

IO.puts("part2 : #{findTheIndex.(14)}")
