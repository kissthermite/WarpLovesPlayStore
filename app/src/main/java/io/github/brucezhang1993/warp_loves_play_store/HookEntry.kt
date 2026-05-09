package io.github.brucezhang1993.warp_loves_play_store

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {
    private val allowed = setOf("com.android.vending","com.google.android.gms","com.google.android.youtube","com.google.android.apps.photos")
    override fun onInit() = configs { isDebug = BuildConfig.DEBUG }
    override fun onHook() = YukiHookAPI.encase {
        listOf("com.cloudflare.onedotonedotonedotone","com.cloudflare.cloudflareoneagent").forEach { pkg ->
            loadApp(pkg) {
                "android.net.VpnService\$Builder".toClass().method { name = "addDisallowedApplication"; param(StringClass); returnType = "android.net.VpnService\$Builder".toClass() }.hook { before { val p = args().first().string(); if (p in allowed) { result = instanceOrNull; return@before }; result = callOriginal() } }
            }
        }
    }
}
