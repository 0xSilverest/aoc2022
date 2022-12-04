import System.IO
import Data.List
import Data.Char

splitBy3 :: [String] -> [[String]]
splitBy3 [] = []
splitBy3 s = (take 3 s) : (splitBy3 $ drop 3 s)

charToInt :: Char -> Int
charToInt c
    | c >= 'a' && c <= 'z' = (ord c) - 96
    | c >= 'A' && c <= 'Z' = (ord c) - 38

solve :: String -> Int
solve xs = sum $ map charToInt xs

comparator :: [String] -> [Char]
comparator [] = []
comparator (x:xs) = (contained (fst t) (snd t)) : comparator xs
    where
        contained t1 t2 = head $ filter (\c -> elem c t1) t2
        t = splitAt (length x `div` 2) x

comparator3 :: [[String]] -> [Char]
comparator3 [] = []
comparator3 (x:xs) = (contained3 x) : comparator3 xs
    where
        contained3 :: [String] -> Char
        contained3 (t1:t2:t3:_) = head $ filter (\c -> elem c t1 && elem c t2) t3

main = do
    f <- readFile "./input/day3.txt"
    print $ "le elf carries : " ++ show (solve $ comparator (lines f))
    print $ "le three elves carries : " ++ show (solve $ comparator3 (splitBy3 $ lines f))


