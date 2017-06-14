package hcpuzzle;

/**
 * Current puzzle state (partially solved)
 *
 * <p>
 * blocks are installed in following order:
 * <pre>
 *     3
 *   2 1 4
 *     5
 *     6
 * </pre>
 * i.e. second block is mounted to left side, 3rd to upper side, etc.
 * <p>
 * vertex naming:
 * <pre>
 *       HG
 *       AB
 *
 *    HA AB BG
 *    FD DC CE
 *
 *       DC
 *       FE
 *
 *       FE
 *       HG
 * </pre>
 * 1st block is ABCD, 2nd - HADF, etc..
 */

public final class Puzzle {
    private int count;
    private Block abcd;
    private Block hadf;
    private Block hgba;
    private Block bgec;
    private Block dcef;
    private Block fegh;

    public Puzzle(Block abcd) {
        this.abcd = abcd;
        this.count = 1;
    }

    public boolean tryAdd(Block b) {
        switch (count) {
            case 1:
                return tryAdd1(b);
            case 2:
                return tryAdd2(b);
            case 3:
                return tryAdd3(b);
            case 4:
                return tryAdd4(b);
            case 5:
                return tryAdd5(b);
        }
        throw new RuntimeException("Should never reach here!");
    }

    public boolean isFull() {
        return count == 6;
    }

    public void removeLast() {
        count--;
    }

    public Block[] getResult() {
        return new Block[]{abcd, hadf, hgba, bgec, dcef, fegh};
    }

    private boolean tryAdd1(Block hadf) {
        if (abcd.v1 && hadf.v2) {
            return false; // a
        }
        if (abcd.v4 && hadf.v3) {
            return false; // d
        }

        if (abcd.e4 != hadf.re2) {
            return false; // da
        }

        this.hadf = hadf;
        count++;
        return true;
    }

    private boolean tryAdd2(Block hgba) {
        if (!one(abcd.v1, hadf.v2, hgba.v4)) {
            return false; // a
        }

        if (abcd.v2 && hgba.v3) {
            return false; // b
        }
        if (hadf.v1 && hgba.v1) {
            return false; // h
        }

        if (abcd.e1 != hgba.re3) {
            return false; // ab
        }
        if (hadf.e1 != hgba.re4) {
            return false; // ha
        }

        this.hgba = hgba;
        count++;
        return true;
    }

    private boolean tryAdd3(Block bgec) {
        if (!one(abcd.v2, hgba.v3, bgec.v1)) {
            return false; // b
        }

        if (hgba.v2 && bgec.v2) {
            return false; // g
        }
        if (abcd.v3 && bgec.v4) {
            return false; // c
        }

        if (abcd.e2 != bgec.re4) {
            return false; // bc
        }
        if (hgba.e2 != bgec.re1) {
            return false; // gb
        }

        this.bgec = bgec;
        count++;
        return true;
    }

    private boolean tryAdd4(Block dcef) {
        if (!one(abcd.v3, bgec.v4, dcef.v2)) {
            return false; // c
        }
        if (!one(abcd.v4, hadf.v3, dcef.v1)) {
            return false; // d
        }

        if (bgec.v3 && dcef.v3) {
            return false; // e
        }
        if (hadf.v4 && dcef.v4) {
            return false; // f
        }

        if (abcd.e3 != dcef.re1) {
            return false; // cd
        }
        if (hadf.e3 != dcef.re4) {
            return false; // df
        }
        if (bgec.e3 != dcef.re2) {
            return false; // ec
        }

        this.dcef = dcef;
        count++;
        return true;
    }

    private boolean tryAdd5(Block fegh) {
        if (!one(hadf.v4, dcef.v4, fegh.v1)) {
            return false; // f
        }
        if (!one(bgec.v3, dcef.v3, fegh.v2)) {
            return false; // e
        }
        if (!one(hgba.v2, bgec.v2, fegh.v3)) {
            return false; // g
        }
        if (!one(hadf.v1, hgba.v1, fegh.v4)) {
            return false; // h
        }

        if (dcef.e3 != fegh.re1) {
            return false; // ef
        }
        if (bgec.e2 != fegh.re2) {
            return false; // ge
        }
        if (hgba.e1 != fegh.re3) {
            return false; // hg
        }
        if (hadf.e4 != fegh.re4) {
            return false; // fh
        }

        this.fegh = fegh;
        count++;
        return true;
    }

    private static boolean one(boolean a, boolean b, boolean c) {
        return a & !b & !c || !a & b & !c || !a & !b & c;
    }

}
