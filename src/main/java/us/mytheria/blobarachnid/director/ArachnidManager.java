package us.mytheria.blobarachnid.director;

import us.mytheria.blobarachnid.BlobArachnid;
import us.mytheria.bloblib.entities.GenericManager;

public class ArachnidManager extends GenericManager<BlobArachnid, ArachnidManagerDirector> {

    public ArachnidManager(ArachnidManagerDirector managerDirector) {
        super(managerDirector);
    }
}