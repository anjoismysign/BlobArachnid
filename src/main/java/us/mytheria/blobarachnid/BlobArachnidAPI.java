package us.mytheria.blobarachnid;

import org.jetbrains.annotations.Nullable;
import us.mytheria.blobarachnid.director.ArachnidManagerDirector;
import us.mytheria.blobarachnid.entities.ArachnidData;

public class BlobArachnidAPI {
    private static BlobArachnidAPI instance;
    private final ArachnidManagerDirector director;

    private BlobArachnidAPI(ArachnidManagerDirector director) {
        this.director = director;
    }

    protected static BlobArachnidAPI initialize(@Nullable ArachnidManagerDirector director) {
        if (instance == null) {
            if (director == null)
                throw new NullPointerException("'director' cannot be null");
            instance = new BlobArachnidAPI(director);
        }
        return instance;
    }

    public static BlobArachnidAPI getInstance() {
        return initialize(null);
    }

    @Nullable
    public ArachnidData getArachnidData(String key) {
        return director.getArachnidDataDirector().getObjectManager().getObject(key);
    }

}
