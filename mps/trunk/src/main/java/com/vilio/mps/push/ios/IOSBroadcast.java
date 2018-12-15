package com.vilio.mps.push.ios;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class IOSBroadcast extends IOSNotification {
    public IOSBroadcast() {
        try {
            this.setPredefinedKeyValue("type", "broadcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}

