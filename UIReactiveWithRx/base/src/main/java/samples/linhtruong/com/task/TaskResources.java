package samples.linhtruong.com.task;

import samples.linhtruong.com.db.DbManager;
import samples.linhtruong.com.network.NetworkManager;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:13.
 * @organization VED
 */

public class TaskResources {

    @Inject
    public DbManager dbManager;

    @Inject
    public NetworkManager networkManager;
}
