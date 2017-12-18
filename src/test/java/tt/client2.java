package tt;

/**
 * Created by hasee on 2017/12/14.
 */
public class client2 {
    public static void main(String[] args){
        //定义出导演类
        Director director =new Director();
//给我一辆奔驰车SUV
        System.out.println("===制造一辆奔驰SUV===");
        ICar benzSuv = director.createBenzSuv();
        System.out.println(benzSuv);
//给我一辆宝马商务车
        System.out.println("\n===制造一辆宝马商务车===");
        ICar bmwVan = director.createBMWVan();
        System.out.println(bmwVan);
//给我一辆混合车型
        System.out.println("\n===制造一辆混合车===");
        ICar complexCar = director.createComplexCar();
        System.out.println(complexCar);
    }
}

interface ICar {
    //汽车车轮
    public String getWheel();
    //汽车引擎
    public String getEngine();
}

class Car implements ICar {
    //汽车引擎
    private String engine;
    //汽车车轮
    private String wheel;
    //一次性传递汽车需要的信息
    public Car(String _engine,String _wheel){
        this.engine = _engine;
        this.wheel = _wheel;
    }
    public String getEngine() {
        return engine;
    }
    public String getWheel() {
        return wheel;
    }
    public String toString(){
        return "车的轮子是：" + wheel + "\n车的引擎是：" + engine;
    }
}

abstract class CarBuilder {
    //待建造的汽车
    private ICar car;
    //设计蓝图
    private Blueprint bp;
    public Car buildCar(){
//按照顺序生产一辆车
        return new Car(buildEngine(),buildWheel());
    }
    //接收一份设计蓝图
    public void receiveBlueprint(Blueprint _bp){
        this.bp = _bp;
    }
    //查看蓝图，只有真正的建造者才可以查看蓝图
    protected Blueprint getBlueprint(){
        return bp;
    }
    //建造车轮
    protected abstract String buildWheel();
    //建造引擎
    protected abstract String buildEngine();
}

class Blueprint {
    //车轮的要求
    private String wheel;
    //引擎的要求
    private String engine;
    public String getWheel() {
        return wheel;
    }
    public void setWheel(String wheel) {
        this.wheel = wheel;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
}

class BMWBuilder extends CarBuilder {
    public String buildEngine() {
        return super.getBlueprint().getEngine();
    }
    public String buildWheel() {
        return super.getBlueprint().getWheel();
    }
}

class BenzBuilder extends CarBuilder {
    public String buildEngine() {
        return super.getBlueprint().getEngine();
    }
    public String buildWheel() {
        return super.getBlueprint().getWheel();
    }
}


class Director {
    //声明对建造者的引用
    private CarBuilder benzBuilder = new BenzBuilder();
    private CarBuilder bmwBuilder = new BMWBuilder();
    //生产奔驰SUV
    public ICar createBenzSuv(){
//制造出汽车
        return createCar(benzBuilder, "benz的引擎", "benz的轮胎");
    }
    //生产出一辆宝马商务车
    public ICar createBMWVan(){
        return createCar(benzBuilder, "BMW的引擎", "BMW的轮胎");
    }
    //生产出一个混合车型
    public ICar createComplexCar(){
        return createCar(bmwBuilder, "BMW的引擎", "benz的轮胎");
    }
    //生产车辆
    private ICar createCar(CarBuilder _carBuilder,String engine,String wheel){
//导演怀揣蓝图
        Blueprint bp = new Blueprint();
        bp.setEngine(engine);
        bp.setWheel(wheel);
        System.out.println("获得生产蓝图");
        _carBuilder.receiveBlueprint(bp);
        return _carBuilder.buildCar();
    }
}