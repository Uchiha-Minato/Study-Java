package com.lhy.dsp;

public class Moon {
    private static Moon uniqueMoon;

    private final double radius;
    private final double distanceToEarth;

    private Moon() {
        uniqueMoon = this;
        radius = 1738;
        distanceToEarth = 363300;
    }

    public static synchronized Moon getMoon() {
        if(uniqueMoon == null) {
            uniqueMoon = new Moon();
        }
        return uniqueMoon;
    }

    public String show() {
        return "月亮的半径是：" + radius + "km,\n" +
                "距离地球" + distanceToEarth + "km";
    }
}
