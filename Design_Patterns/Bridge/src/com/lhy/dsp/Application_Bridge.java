package com.lhy.dsp;

import com.lhy.dsp.refinedabstraction.TUPBookEdit;

public class Application_Bridge {
    public static void main(String[] args) {
        TUPBookEdit zhang = new TUPBookEdit();
        String[] seriesBook = {"人间失格","圆圈正义","呐喊"};
        String[] authorName = {"太宰治","罗翔","鲁迅"};
        zhang.planBook(seriesBook, authorName);
        zhang.releaseBook();
    }
}
