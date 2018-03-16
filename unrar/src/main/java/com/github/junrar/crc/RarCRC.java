package com.github.junrar.crc;

public class RarCRC {

    private final static int crcTab[];

    static {
        crcTab = new int[256];
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int j = 0; j < 8; j++) {
                if ((c & 1) != 0) {
                    c >>>= 1;
                    c ^= 0xEDB88320;
                } else {
                    c >>>= 1;
                }
            }
            crcTab[i] = c;
        }
    }

    private RarCRC() {
    }

    public static int checkCrc(int startCrc, byte[] data, int offset,
            int count) {
        int size = Math.min(data.length - offset, count);

        for (int i = 0; i < size; i++) {

            startCrc = (crcTab[((int) ((int) startCrc
                    ^ (int) data[offset + i])) & 0xff] ^ (startCrc >>> 8));
        }
        return (startCrc);
    }

    public static short checkOldCrc(short startCrc, byte[] data, int count) {
        int n = Math.min(data.length, count);
        for (int i = 0; i < n; i++) {
            startCrc = (short) ((short) (startCrc + (short) (data[i] & 0x00ff)) & -1);
            startCrc = (short) (((startCrc << 1) | (startCrc >>> 15)) & -1);
        }
        return (startCrc);
    }
}
