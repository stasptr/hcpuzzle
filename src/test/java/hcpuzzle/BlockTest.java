package hcpuzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    @Test
    void singleBits() {

        assertEquals("" +
                ".....\n" +
                ".###.\n" +
                ".###.\n" +
                ".###.\n" +
                ".....\n", new Block('#', 0).toString());

        Block[] data = {
                new Block('#', 1),
                new Block('#', 1 << 1),
                new Block('#', 1 << 2),
                new Block('#', 1 << 3),
                new Block('#', 1 << 4),
                new Block('#', 1 << 5),
                new Block('#', 1 << 6),
                new Block('#', 1 << 7),
                new Block('#', 1 << 8),
                new Block('#', 1 << 9),
                new Block('#', 1 << 10),
                new Block('#', 1 << 11),
                new Block('#', 1 << 12),
                new Block('#', 1 << 13),
                new Block('#', 1 << 14),
                new Block('#', 1 << 15),
                new Block('#', 1 << 16),
        };

        assertEquals("" +
                "#.... .#... ..#.. ...#. ....# ..... ..... ..... ..... ..... ..... ..... ..... ..... ..... ..... .....\n" +
                ".###. .###. .###. .###. .###. .#### .###. .###. .###. .###. .###. .###. .###. .###. .###. ####. .###.\n" +
                ".###. .###. .###. .###. .###. .###. .#### .###. .###. .###. .###. .###. .###. .###. ####. .###. .###.\n" +
                ".###. .###. .###. .###. .###. .###. .###. .#### .###. .###. .###. .###. .###. ####. .###. .###. .###.\n" +
                "..... ..... ..... ..... ..... ..... ..... ..... ....# ...#. ..#.. .#... #.... ..... ..... ..... .....\n",
                Block.blocksToString(data));
    }

    @Test
    void verticesAndEdges() {
        checkVerticesAndEdges(0x0000, false, false, false, false, 0, 0, 0, 0);
        checkVerticesAndEdges(0xFFFF, true, true, true, true, 7, 7, 7, 7);

        checkVerticesAndEdges(0x0001, true, false, false, false, 0, 0, 0, 0);
        checkVerticesAndEdges(0x0010, false, true, false, false, 0, 0, 0, 0);
        checkVerticesAndEdges(0x0100, false, false, true, false, 0, 0, 0, 0);
        checkVerticesAndEdges(0x1000, false, false, false, true, 0, 0, 0, 0);

        checkVerticesAndEdges(0x000e, false, false, false, false, 7, 0, 0, 0);
        checkVerticesAndEdges(0x00e0, false, false, false, false, 0, 7, 0, 0);
        checkVerticesAndEdges(0x0e00, false, false, false, false, 0, 0, 7, 0);
        checkVerticesAndEdges(0xe000, false, false, false, false, 0, 0, 0, 7);
    }

    private void checkVerticesAndEdges(int bits, boolean v1, boolean v2, boolean v3, boolean v4, int e1, int e2, int e3, int e4) {
        Block v = new Block('#', bits);
        assertEquals(v1, v.v1);
        assertEquals(v2, v.v2);
        assertEquals(v3, v.v3);
        assertEquals(v4, v.v4);
        assertEquals(e1, v.e1);
        assertEquals(e2, v.e2);
        assertEquals(e3, v.e3);
        assertEquals(e4, v.e4);
    }
}
