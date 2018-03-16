package com.github.jcompress.unrar.impl;

import java.io.File;
import java.io.IOException;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.io.IReadOnlyAccess;
import com.github.jcompress.unrar.io.ReadOnlyAccessFile;

public class FileVolume implements Volume {

    private final Archive archive;
    private final File file;

    /**
     * @param file
     */
    public FileVolume(Archive archive, File file) {
        this.archive = archive;
        this.file = file;
    }

    @Override
    public IReadOnlyAccess getReadOnlyAccess() throws IOException {
        return new ReadOnlyAccessFile(file);
    }

    @Override
    public long getLength() {
        return file.length();
    }

    @Override
    public Archive getArchive() {
        return archive;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
}
