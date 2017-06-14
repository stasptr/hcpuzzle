package hcpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Solver {

    /**
     * Solving given puzzle
     *
     * @param blocks - puzzle fragments
     * @return solved puzzle or null if no solution found
     * @see Block
     * @see Puzzle
     */
    public static Puzzle solve(int[] blocks) {
        // 1st block is fixed (rotated/flipped 1st block produces rotated/flipped solutions)
        Puzzle puzzle = new Puzzle(new Block('1', blocks[0]));

        // unique transformations for 2..6 blocks (rotation & flip)
        List<List<Block>> variants = new ArrayList<>();
        for (int i = 1; i < blocks.length; i++) {
            int block = blocks[i];
            char label = (char) ('1' + i);
            List<Block> vars = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int transform = Block.transform(block, j);
                boolean duplicate = false;
                for (int k = 0; k < j; k++) {
                    if (vars.get(k).bits == transform) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    vars.add(new Block(label, transform));
                }
            }
            variants.add(vars);
        }

        if (findSolution(puzzle, variants, -1)) {
            return puzzle;
        }
        return null;
    }

    /**
     * Recursive solution finding
     *
     * @param puzzle   current puzzle state
     * @param variants all variants
     * @param mask     bitmask for available variants (already used puzzle fragment index bit is set to 0)
     * @return true if solution found
     */
    private static boolean findSolution(Puzzle puzzle, List<List<Block>> variants, int mask) {
        if (mask == 0) {
            return false;
        }
        for (int i = 0, variantsSize = variants.size(); i < variantsSize; i++) {
            int newMask = mask & ~(1<<i);
            if (newMask != mask) {
                List<Block> get = variants.get(i);
                //noinspection ForLoopReplaceableByForEach
                for (int j = 0, getSize = get.size(); j < getSize; j++) {
                    Block block = get.get(j);
                    if (puzzle.tryAdd(block)) {
                        if (puzzle.isFull() || findSolution(puzzle, variants, newMask)) {
                            return true;
                        }
                        puzzle.removeLast();
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        if (args.length != 6) {
            throw new IllegalArgumentException("Required arguments: 6 16-bit integers representing happy cube blocks");
        }
        int data[] = Arrays.stream(args).mapToInt(Integer::decode).toArray();

        System.out.println("Blocks:");
        System.out.println();
        System.out.println(Block.bitsToString(data));

        Puzzle puzzle = solve(data);
        if (puzzle != null) {
            System.out.println("Solution:");
            System.out.println();
            Block[] result = puzzle.getResult();
            System.out.println(Block.blocksToString(null, result[2]));
            System.out.println(Block.blocksToString(result[1], result[0], result[3]));
            System.out.println(Block.blocksToString(null, result[4]));
            System.out.println(Block.blocksToString(null, result[5]));
        } else {
            System.out.println("No solution");
        }
    }

}
