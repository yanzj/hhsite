package com.vilio.mps.push.android;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class AndroidUnicast extends AndroidNotification {
    public AndroidUnicast() {
        try {
            this.setPredefinedKeyValue("type", "unicast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}

