package us.mytheria.blobarachnid;

import us.mytheria.blobarachnid.director.ArachnidManagerDirector;
import us.mytheria.blobarachnid.entities.ArachnidReader;
import us.mytheria.bloblib.managers.BlobPlugin;
import us.mytheria.bloblib.managers.IManagerDirector;

public final class BlobArachnid extends BlobPlugin {
    private IManagerDirector proxy;
    private BlobArachnidAPI api;

    @Override
    public void onEnable() {
        ArachnidReader.initialize(this);
        ArachnidManagerDirector director = new ArachnidManagerDirector(this);
        proxy = director.proxy();
        api = BlobArachnidAPI.initialize(director);
    }

    public IManagerDirector getManagerDirector() {
        return proxy;
    }

    public BlobArachnidAPI getApi() {
        return api;
    }
}
