package com.github.junrar;

import java.io.IOException;

import com.github.junrar.io.IReadOnlyAccess;

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
