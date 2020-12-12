package com.example.shoppingcalculator.VKAPI

import com.vk.api.sdk.requests.VKRequest
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiException
import org.json.JSONObject
import java.io.IOException
import java.net.URL


class VKUsersRequest: VKRequest<List<VKUser>> {
    constructor(uids: IntArray = intArrayOf()): super("users.get") {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
        addParam("fields", "photo_200")
        addParam("lang", "ru")
    }

    @Throws(InterruptedException::class, IOException::class, VKApiException::class)
    override fun onExecute(manager: VKApiManager): List<VKUser> {
        val config = manager.config
        if (!params.containsKey("lang")) {
            params.put("lang", config.lang);
        } else {
            params["lang"] = "ru" //Тут заменяется мой параметр на lang из ApiConfig
        }
        //params["device_id"] = config.deviceId
        params["v"] = config.version

        return manager.execute(
            VKMethodCall.Builder()
            .args(params)
            .method(method)
            .version(config.version)
            .build(), this)
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONArray("response")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}