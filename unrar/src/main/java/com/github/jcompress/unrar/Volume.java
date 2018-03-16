package com.github.jcompress.unrar;

import java.io.IOException;

import com.github.jcompress.unrar.io.IReadOnlyAccess;

public interface Volume {

    /**
     * @return the access
     * @throws IOException
     */
    IReadOnlyAccess getReadOnlyAccess() throws IOException;

    /**
     * @return the data length
     */
    long getLength();

    /**
     * @return the archive this volume belongs to
     */
    Archive getArchive();
}
