package io.github.brucezhang1993.warp_loves_play_store

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.StringType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {

    private val allowedPackages = setOf(
        "com.android.vending",
        "com.google.android.apps.photos",
        "com.google.android.youtube"
    )

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
    }

    override fun onHook() = YukiHookAPI.encase {
        loadApp("com.cloudflare.onedotonedotonedotone") {
            "android.net.VpnService\$Builder".toClass().method {
                name = "addDisallowedApplication"
                param(StringClass)
                returnType = "android.net.VpnService\$Builder".toClass()
            }.hook {
                before {
                    val param1 = args().first().string();
                    if (param1 in allowedPackages) {
                        result = instanceOrNull
                        return@before
                    }
                    result = callOriginal()
                }
            }
        }
    }
}
