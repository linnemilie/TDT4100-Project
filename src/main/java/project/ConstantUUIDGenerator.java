package project;

import java.util.UUID;

public class ConstantUUIDGenerator implements IUUIDGenerator {

    private UUID constantUUID = UUID.fromString("dbb4dcba-1032-4619-ad38-8822780a9899");

    @Override
    public UUID getRandomeUUID() {
        return constantUUID;
    }

}
