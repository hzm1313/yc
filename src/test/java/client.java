import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by hasee on 2017/12/14.
 */
public class client {
    public static void main(String[] args) throws IOException {
        Invoker invoker = new Invoker();
        while(true){
//UNIX下的默认提示符号
            System.out.print("#");
//捕获输出
            String input = (new BufferedReader(new InputStreamReader (System.in))).readLine();
//输入quit或exit则退出
            if(input.equals("quit") || input.equals("exit")){
                return;
            }
            System.out.println(invoker.exec(input));
        }
    }
}

abstract class Command {
    public abstract String execute(CommandVO vo);
    //建立链表
    protected final List<? extends CommandName> buildChain(Class<? extends CommandName> abstractClass){
//取出所有的命令名下的子类
        List<Class> classes = ClassUtils.getSonClass(abstractClass);
//存放命令的实例，并建立链表关系
        List<CommandName> commandNameList = new ArrayList<CommandName>();
        for(Class c:classes){
            CommandName commandName =null;
            try {
//产生实例
                commandName = (CommandName)Class.forName (c.getName()) .newInstance();
            } catch (Exception e){
// TODO 异常处理
            }
//建立链表
            if(commandNameList.size()>0){
                commandNameList.get(commandNameList.size()-1).setNext (commandName);
            }
            commandNameList.add(commandName);
        }
        return commandNameList;
    }
}

abstract class CommandName {
    private CommandName nextOperator;
    public final String handleMessage(CommandVO vo){
//处理结果
        String result = "";
//判断是否是自己处理的参数
        if(vo.getParam().size() == 0 || vo.getParam().contains (this.getOperateParam())){
            result = this.echo(vo);
        }else{
            if(this.nextOperator !=null){
                result = this.nextOperator.handleMessage(vo);
            }else{
                result = "命令无法执行";
            }
        }
        return result;
    }
    //设置剩余参数由谁来处理
    public void setNext(CommandName _operator){
        this.nextOperator = _operator;
    }
    //每个处理者都要处理一个后缀参数
    protected abstract String getOperateParam();
    //每个处理者都必须实现处理任务
    protected abstract String echo(CommandVO vo);
}

class CommandVO {
    //定义参数名与参数的分隔符号,一般是空格
    public final static String DIVIDE_FLAG =" ";
    //定义参数前的符号，Unix一般是-,如ls -la
    public final static String PREFIX="-";
    //命令名，如ls、du
    private String commandName = "";
    //参数列表
    private ArrayList<String> paramList = new ArrayList<String>();
    //操作数列表
    private ArrayList<String> dataList = new ArrayList<String>();
    //通过构造函数传递进来命令
    public CommandVO(String commandStr){
//常规判断
        if(commandStr != null && commandStr.length() !=0){
//根据分隔符号拆分出执行符号
            String[] complexStr = commandStr.split(CommandVO.DIVIDE_FLAG);
//第一个参数是执行符号
            this.commandName = complexStr[0];
//把参数放到List中
            for(int i=1;i<complexStr.length;i++){
                String str = complexStr[i];
//包含前缀符号，认为是参数
                if(str.indexOf(CommandVO.PREFIX)==0){
                    this.paramList.add(str.replace
                            (CommandVO.PREFIX, "").trim());
                }else{
                    this.dataList.add(str.trim());
                }
            }
        }else{
            //传递的命令错误
            System.out.println("命令解析失败，必须传递一个命令才能执行！");
        }
    }
    //得到命令名
    public String getCommandName(){
        return this.commandName;
    }
    //获得参数
    public ArrayList<String> getParam(){
//为了方便处理空参数
        if(this.paramList.size() ==0){
            this.paramList.add("");
        }
        return new ArrayList(new HashSet(this.paramList));
    }
    //获得操作数
    public ArrayList<String> getData(){
        return this.dataList;
    }
}

abstract class AbstractDF extends CommandName {
    //默认参数
    public final static String DEFAULT_PARAM = "";
    //参数k
    public final static String K_PARAM = "k";
    //参数g
    public final static String G_PARAM = "g";
}

class DF extends AbstractDF{
    //定义一下自己能处理什么参数
    protected String getOperateParam() {
        return super.DEFAULT_PARAM;
    }
    //命令处理
    protected String echo(CommandVO vo) {
        return DiskManager.df();
    }
}
class DF_K extends AbstractDF{
    //定义一下自己能处理什么参数
    protected String getOperateParam() {
        return super.K_PARAM;
    }
    //命令处理
    protected String echo(CommandVO vo) {
        return DiskManager.df_k();
    }
}
class DF_G extends AbstractDF{
    //定义一下自己能处理什么参数
    protected String getOperateParam() {
        return super.G_PARAM;
    }
    //命令处理
    protected String echo(CommandVO vo) {
        return DiskManager.df_g();
    }
}

class DiskManager {
    //默认的计算大小
    public static String df(){
        return "/\t10485760\n/usr\t104857600\n/home\t1048576000\n";
    }
    //按照kb来计算
    public static String df_k(){
        return "/\t10240\n/usr\t102400\n/home\tt10240000\n";
    }
    //按照gb计算
    public static String df_g(){
        return "/\t10\n/usr\t100\n/home\tt10000\n";
    }
}

class DFCommand extends Command {
    public String execute(CommandVO vo) {
        return super.buildChain(AbstractDF.class).get(0).handleMessage(vo);
    }
}

enum CommandEnum {
    ls("com.cbf4life.common.command.LSCommand"),
    df("com.cbf4life.common.command.DFCommand");
    private String value = "";
    //定义构造函数，目的是Data(value)类型的相匹配
    private CommandEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    //返回所有的enum对象
    public static List<String> getNames(){
        CommandEnum[] commandEnum = CommandEnum.values();
        List<String> names = new ArrayList<String>();
        for(CommandEnum c:commandEnum){
            names.add(c.name());
        }
        return names;
    }
}

class Invoker {
    //执行命令
    public String exec(String _commandStr){
//定义返回值
        String result = "";
//首先解析命令
        CommandVO vo = new CommandVO(_commandStr);
//检查是否支持该命令
        if(CommandEnum.getNames().contains(vo.getCommandName())){
//产生命令对象
            String className = CommandEnum.valueOf (vo.getCommandName()) .getValue();
            Command command;
            try {
                command = (Command)Class.forName(className).newInstance();
                result = command.execute(vo);
            }catch(Exception e){
// TODO 异常处理
            }
        }else{
            result = "无法执行命令，请检查命令格式";
        }
        return result;
    }
}

class ClassUtils {
    //根据父类查找到所有的子类，默认情况是子类和父类都在同一个包名下
    public static List<Class> getSonClass(Class fatherClass){
//定义一个返回值
        List<Class> returnClassList = new ArrayList<Class>();
//获得包名称
        String packageName = fatherClass.getPackage().getName();
//获得包中的所有类
        List<Class> packClasses = getClasses(packageName);
//判断是否是子类
        for(Class c:packClasses){
            if(fatherClass.isAssignableFrom(c) && !fatherClass.equals(c)){
                returnClassList.add(c);
            }
        }
        return returnClassList;
    }
    //从一个包中查找出所有的类，在jar包中不能查找
    private static List<Class> getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }
    private static List<Class> findClasses(File directory, String packageName) {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + '.' + file.getName() .substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}