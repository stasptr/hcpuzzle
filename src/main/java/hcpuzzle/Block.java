package hcpuzzle;

/**
 * HCube 5x5 block
 *
 * <pre>
 * bit order: 0b FEDCBA987654320
 *               high        low
 *
 *   x01234
 *  y
 *  0 01234
 *  1 F###5
 *  2 E###6
 *  3 D###7
 *  4 CBA98
 * </pre>
 */
public final class Block {
    public final char label;
    public final int bits;

    public final boolean v1;
    public final boolean v2;
    public final boolean v3;
    public final boolean v4;

    public final int e1;
    public final int e2;
    public final int e3;
    public final int e4;

    public final int re1;
    public final int re2;
    public final int re3;
    public final int re4;

    public Block(char label, int bits) {
        this.label = label;
        this.bits = bits;

        v1 = (bits & 0x1) != 0;
        v2 = (bits & 0x10) != 0;
        v3 = (bits & 0x100) != 0;
        v4 = (bits & 0x1000) != 0;

        e1 = (bits & 0xe) >> 1;
        e2 = (bits & 0xe0) >> 5;
        e3 = (bits & 0xe00) >> 9;
        e4 = (bits & 0xe000) >> 13;

        re1 = re(e1);
        re2 = re(e2);
        re3 = re(e3);
        re4 = re(e4);
    }

    private static int re(int v3) {
        return (((v3 & 1) == 0) ? 4 : 0) | (((v3 & 2) == 0) ? 2 : 0) | (((v3 & 4) == 0) ? 1 : 0);
    }


    private boolean getByXY(int x, int y) {
        if (y == 0) {
            return (bits & (1 << x)) != 0;
        } else if (x == 4) {
            return (bits & (1 << (y + 4))) != 0;
        } else if (y == 4) {
            return (bits & (1 << (12 - x))) != 0;
        } else if (x == 0) {
            return (bits & (1 << (16 - y))) != 0;
        }
        return true;
    }

    public static String blocksToString(Block... block) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 5; y++) {
            for (int i = 0; i < block.length; i++) {
                if (i != 0) {
                    sb.append(' ');
                }
                for (int x = 0; x < 5; x++) {
                    Block b = block[i];
                    sb.append(b == null ? ' ' : b.getByXY(x, y) ? b.label : '.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static String bitsToString(int... data) {
        Block blocks[] = new Block[data.length];
        for (int i = 0; i < data.length; i++) {
            blocks[i] = new Block((char) ('1' + i), data[i]);
        }
        return blocksToString(blocks);
    }

    @Override
    public String toString() {
        return blocksToString(this);
    }

    public static int transform(int block, int transform) {
        if ((transform & 4) == 4) {
            // flip x
            int r = Integer.reverse(block);
            block = ((r >>> 11) & 0xFFFE) | (r >>> 27);
        }
        switch (transform & 3) {
            case 0:
                return block;
            case 1:
                return (block >> 4) | ((block & 0xF) << 12);
            case 2:
                return (block >> 8) | ((block & 0xFF) << 8);
            case 3:
                return (block >> 12) | ((block & 0xFFF) << 4);
        }
        throw new RuntimeException("Should never reach here!");
    }
}
