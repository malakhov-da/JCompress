package com.github.jcompress.unrar.io;

import java.io.IOException;

public interface IReadOnlyAccess {

    /**
     * @return the current position in the file
     */
    public long getPosition() throws IOException;

    /**
     * @param pos the position in the file
     * @return success ? true : false
     */
    public void setPosition(long pos) throws IOException;

    /**
     * Read a single byte of data.
     */
    public int read() throws IOException;

    /**
     * Read up to <tt>count</tt> bytes to the specified buffer.
     */
    public int read(byte[] buffer, int off, int count) throws IOException;

    /**
     * Read exactly <tt>count</tt> bytes to the specified buffer.
     *
     * @param buffer where to store the read data
     * @param count how many bytes to read
     * @return bytes read || -1 if IO problem
     */
    public int readFully(byte[] buffer, int count) throws IOException;

    /**
     * Close this file.
     */
    public void close() throws IOException;
}
