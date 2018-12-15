package com.vilio.mps.push.android;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
public class AndroidBroadcast extends AndroidNotification {
    public AndroidBroadcast() {
        try {
            this.setPredefinedKeyValue("type", "broadcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
