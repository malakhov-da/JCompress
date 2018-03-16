package com.github.jcompress.unrar.vfs2.provider.rar;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.RandomAccessContent;
import org.apache.commons.vfs2.util.RandomAccessMode;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.io.IReadOnlyAccess;
import com.github.jcompress.unrar.io.InputStreamReadOnlyAccessFile;

public class VFSVolume implements Volume {

    private final Archive archive;
    private final FileObject file;

    /**
     * @param archive
     * @param firstVolume
     */
    public VFSVolume(Archive archive, FileObject file) {
        this.archive = archive;
        this.file = file;
    }

    @Override
    public IReadOnlyAccess getReadOnlyAccess() throws IOException {
        IReadOnlyAccess input = null;
        try {
            RandomAccessContent rac = file.getContent().getRandomAccessContent(
                    RandomAccessMode.READ);
            input = new RandomAccessContentAccess(rac);
        } catch (Exception e) {
            input = new InputStreamReadOnlyAccessFile(file.getContent()
                    .getInputStream());
        }
        return input;
    }

    @Override
    public long getLength() {
        try {
            return file.getContent().getSize();
        } catch (FileSystemException e) {
            return -1;
        }
    }

    @Override
    public Archive getArchive() {
        return archive;
    }

    /**
     * @return the file
     */
    public FileObject getFile() {
        return file;
    }
}
