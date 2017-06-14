package hcpuzzle;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

class SolverTest {

    @Test
    void randomPermutations() {
        List<Integer> ints = Arrays.asList(17476, 46517, 19012, 47946, 47780, 43700);

        long seed = System.currentTimeMillis();
        Random rnd = new Random(seed);
        int[] data = new int[6];

        for (int i = 0; i < 1_000_000; i++) {
            ArrayList<Integer> list = new ArrayList<>(ints);
            Collections.shuffle(list, rnd);
            for (int j = 0; j < 6; j++) {
                data[j] = Block.transform(list.get(j), rnd.nextInt(8));
            }

            Puzzle puzzle = Solver.solve(data);
            if (puzzle == null) {
                System.out.println(Block.bitsToString(data));
                for (int j = 0; j < 6; j++) {
                    System.out.println(data[j]);
                }
                fail("no solution found, iteration:" + i + " seed" + seed);
            }
        }
    }

}