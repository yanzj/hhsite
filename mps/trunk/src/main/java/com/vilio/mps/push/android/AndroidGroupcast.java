package com.vilio.mps.push.android;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class AndroidGroupcast extends AndroidNotification {
    public AndroidGroupcast() {
        try {
            this.setPredefinedKeyValue("type", "groupcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}

