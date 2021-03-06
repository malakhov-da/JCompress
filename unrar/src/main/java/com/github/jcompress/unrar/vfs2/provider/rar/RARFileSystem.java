package com.github.jcompress.unrar.vfs2.provider.rar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileSystem;
import org.apache.commons.vfs2.provider.UriParser;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.exception.RarException;
import com.github.jcompress.unrar.rarfile.FileHeader;

public class RARFileSystem extends AbstractFileSystem implements FileSystem {

    private final FileObject parentLayer;

    private Archive archive;
    private Map<String, FileHeader> files = new HashMap<String, FileHeader>();

    public RARFileSystem(final AbstractFileName rootName,
            final FileObject parentLayer,
            final FileSystemOptions fileSystemOptions)
            throws FileSystemException {
        super(rootName, parentLayer, fileSystemOptions);
        this.parentLayer = parentLayer;
    }

    @Override
    public void init() throws FileSystemException {
        super.init();

        try {
            try {
                archive = new Archive(new VFSVolumeManager(parentLayer));
                // Build the index
                List<RARFileObject> strongRef = new ArrayList<RARFileObject>(
                        100);
                for (final FileHeader header : archive.getFileHeaders()) {
                    AbstractFileName name = (AbstractFileName) getFileSystemManager()
                            .resolveName(
                                    getRootName(),
                                    UriParser.encode(header.getFileNameString()));

                    // Create the file
                    RARFileObject fileObj;
                    if (header.isDirectory() && getFileFromCache(name) != null) {
                        fileObj = (RARFileObject) getFileFromCache(name);
                        fileObj.setHeader(header);
                        continue;
                    }

                    fileObj = createRARFileObject(name, header);
                    putFileToCache(fileObj);
                    strongRef.add(fileObj);
                    fileObj.holdObject(strongRef);

                    // Make sure all ancestors exist
                    RARFileObject parent;
                    for (AbstractFileName parentName = (AbstractFileName) name
                            .getParent(); parentName != null; fileObj = parent, parentName = (AbstractFileName) parentName
                                    .getParent()) {
                        // Locate the parent
                        parent = (RARFileObject) getFileFromCache(parentName);
                        if (parent == null) {
                            parent = createRARFileObject(parentName, null);
                            putFileToCache(parent);
                            strongRef.add(parent);
                            parent.holdObject(strongRef);
                        }

                        // Attach child to parent
                        parent.attachChild(fileObj.getName());
                    }
                }

            } catch (RarException e) {
                throw new FileSystemException(e);
            } catch (IOException e) {
                throw new FileSystemException(e);
            }
        } finally {
            // closeCommunicationLink();
        }
    }

    protected RARFileObject createRARFileObject(final AbstractFileName name,
            final FileHeader header) throws FileSystemException {
        return new RARFileObject(name, archive, header, this);
    }

    @Override
    protected void doCloseCommunicationLink() {
        try {
            archive.close();
        } catch (FileSystemException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the capabilities of this file system.
     */
    @Override
    protected void addCapabilities(final Collection<Capability> caps) {
        caps.addAll(RARFileProvider.capabilities);
    }

    /**
     * Creates a file object.
     */
    @Override
    protected FileObject createFile(final AbstractFileName name)
            throws FileSystemException {
        String path = name.getPath().substring(1);
        if (path.length() == 0) {
            return new RARFileObject(name, archive, null, this);
        } else if (files.containsKey(name.getPath())) {
            return new RARFileObject(name, archive, files.get(name.getPath()),
                    this);
        }
        return null;
    }

    /**
     * will be called after all file-objects closed their streams.
     */
    protected void notifyAllStreamsClosed() {
        closeCommunicationLink();
    }
}
