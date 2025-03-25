package de.dogwaterdev.worldframework;

import de.dogwaterdev.worldframework.VoidWorld;

public enum CustomWorldTypes {
    VOID(VoidWorld.class);

    private final Class<? extends CustomWorld> worldClass;

    CustomWorldTypes(Class<? extends CustomWorld> worldClass) {
        this.worldClass = worldClass;
    }

    public Class<? extends CustomWorld> getWorldClass() {
        return worldClass;
    }

    /**
     * Retrieves the WorldTypeMeta annotation data.
     * @return The WorldTypeMeta annotation if present, otherwise null.
     */
    public WorldTypeMeta getMeta() {
        return worldClass.isAnnotationPresent(WorldTypeMeta.class) ? worldClass.getAnnotation(WorldTypeMeta.class) : null;
    }
}