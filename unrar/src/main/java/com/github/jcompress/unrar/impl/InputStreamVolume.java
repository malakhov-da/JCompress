package com.github.jcompress.unrar.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.io.IReadOnlyAccess;
import com.github.jcompress.unrar.io.ReadOnlyAccessByteArray;
import org.apache.commons.compress.utils.IOUtils;

public class InputStreamVolume implements Volume {

    private final Archive archive;
    private final byte[] file;

    /**
     * @param file
     * @throws IOException
     */
    public InputStreamVolume(Archive archive, InputStream inputstream) throws IOException {
        this.archive = archive;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        IOUtils.copy(inputstream, bao, 1024 * 1024);
        byte[] data = bao.toByteArray();
        this.file = data;
    }

    @Override
    public IReadOnlyAccess getReadOnlyAccess() throws IOException {
        return new ReadOnlyAccessByteArray(file);
    }

    @Override
    public long getLength() {
        return file.length;
    }

    @Override
    public Archive getArchive() {
        return archive;
    }

    /**
     * @return the file
     */
    public File getFile() {
        // there is no File object anymore
        return null;
    }
}
