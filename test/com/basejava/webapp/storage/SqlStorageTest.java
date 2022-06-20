package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                        Config.getConfig().getDbUrl(),
                        Config.getConfig().getDbUser(),
                        Config.getConfig().getDbPass()
                )
        );
    }
}
