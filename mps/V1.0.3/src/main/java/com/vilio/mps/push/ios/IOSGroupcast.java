package com.vilio.mps.push.ios;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class IOSGroupcast extends IOSNotification {
    public IOSGroupcast() {
        try {
            this.setPredefinedKeyValue("type", "groupcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}

