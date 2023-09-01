package project;

import java.util.UUID;

public class RandomUUIDGenerator implements IUUIDGenerator {

    @Override
    public UUID getRandomeUUID() {
        return UUID.randomUUID();
    }

}
