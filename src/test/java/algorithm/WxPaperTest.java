package algorithm;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by DT167 on 2018/4/17.
 */
public class WxPaperTest {
    RedPack RedPack;
    BigDecimal amount;
    int num;
    List<BigDecimal> numPeople;

    public static void main(String[] args){
        WxPaperTest wxPaperTest = new WxPaperTest();
        int num = 10;
        int total = 100;
        RedPack redPack = new RedPack(new BigDecimal(total),num);
        double testMoney = 0;
        for(int i=0;i<num;i++){
            double getMoney = wxPaperTest.getRandMoney(redPack);
            testMoney+=getMoney;
            System.out.println(getMoney);
            System.out.println(redPack.toString());
        }
        System.out.println("总钱:"+testMoney);
    }

    public double getRandMoney(RedPack redPack) {
        double remainPrice = 0;
        if(redPack.getReaminNum() == 1) {
            remainPrice = redPack.getRemainAmount().doubleValue();
        } else{
            Random random = new Random();
            double min = 0.01;
            double max = (redPack.getRemainAmount().divide(new BigDecimal(redPack.getReaminNum()),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("2")).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            remainPrice = random.nextDouble()*max + min;
            remainPrice = new BigDecimal(remainPrice).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        redPack.setRemainAmount(redPack.getRemainAmount().subtract(new BigDecimal(remainPrice)).setScale(2,BigDecimal.ROUND_DOWN));
        redPack.setReaminNum(redPack.getReaminNum()-1);
        return remainPrice;
    }
}

class RedPack{
    private BigDecimal allAmount;
    private int reaminNum;
    private BigDecimal remainAmount;

    RedPack(BigDecimal allAmount,int reaminNum) {
        this.allAmount = allAmount;
        this.reaminNum = reaminNum;
        this.remainAmount = allAmount;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    public int getReaminNum() {
        return reaminNum;
    }

    public void setReaminNum(int reaminNum) {
        this.reaminNum = reaminNum;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    @Override
    public String toString(){
        return "剩余金额:"+remainAmount+"剩余次数:"+reaminNum+"总金额："+allAmount;
    }
}
