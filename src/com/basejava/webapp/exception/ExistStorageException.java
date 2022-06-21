package com.basejava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume with uuid: " + uuid + " already exists", uuid);
    }

    public ExistStorageException(Exception e) {
        super(e);
    }
}
