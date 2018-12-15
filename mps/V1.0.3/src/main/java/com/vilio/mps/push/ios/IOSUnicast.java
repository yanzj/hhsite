package com.vilio.mps.push.ios;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class IOSUnicast extends IOSNotification {
    public IOSUnicast() {
        try {
            this.setPredefinedKeyValue("type", "unicast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
