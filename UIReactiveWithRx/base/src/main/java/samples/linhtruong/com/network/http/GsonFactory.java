package samples.linhtruong.com.network.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:31.
 * @organization VED
 */

public class GsonFactory {

    private GsonFactory() {
    }

    public static Gson build() {
        return new GsonBuilder()
                /*.registerTypeAdapter(CheckUpdateResult.Update.class, new UpdateDeserializer())
                .registerTypeAdapter(BannerInfo.class, new BannerInfoDeserializer())
                .registerTypeAdapter(ClanChatHistoryResponse.class, new ClanChatHistoryDeserializer())
                .registerTypeAdapter(GMTemplateData.class, new GMTemplateDataAdapter())*/
                .create();
    }
}
