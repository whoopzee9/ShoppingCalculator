package com.example.shoppingcalculator.VKAPI

import com.vk.api.sdk.requests.VKRequest
import com.vk.api.sdk.VKApiConfig
import org.json.JSONObject
import java.net.URL


class VKUsersRequest: VKRequest<List<VKUser>> {
    constructor(uids: IntArray = intArrayOf()): super("users.get") {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
        //addParam("fields", "photo_200")
        addParam("lang", 0)
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