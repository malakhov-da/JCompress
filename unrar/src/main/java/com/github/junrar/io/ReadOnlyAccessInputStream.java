package com.github.junrar.io;

import java.io.IOException;
import java.io.InputStream;

public class ReadOnlyAccessInputStream extends InputStream {

    private IReadOnlyAccess file;

    private long curPos;
    private final long startPos;
    private final long endPos;

    public ReadOnlyAccessInputStream(IReadOnlyAccess file, long startPos,
            long endPos) throws IOException {
        super();
        this.file = file;
        this.startPos = startPos;
        curPos = startPos;
        this.endPos = endPos;
        file.setPosition(curPos);
    }

    @Override
    public int read() throws IOException {
        if (curPos == endPos) {
            return -1;
        } else {
            int b = file.read();
            curPos++;
            return b;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return 0;
        }
        if (curPos == endPos) {
            return -1;
        }
        int bytesRead = file.read(b, off,
                (int) Math.min(len, endPos - curPos));
        curPos += bytesRead;
        return bytesRead;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }
//
//    public void close() throws IOException {
//        file.close();
//    }
}
