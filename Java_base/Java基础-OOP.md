# Java基础

作为面向对象语言，Java支持以下特征：

封装，继承，多态，抽象，类，对象，实例，方法，重载

## 1.面向对象三大基本特征 - 封装，继承，多态

这三种特性属于层层递进的关系。

**类与对象的关系**

    对象是类的实例，类是对象的抽象

*类（Class）- 一个模板，用于描述一类对象的状态和行为* 

**把这些状态和行为打包并保护的过程叫做封装**

    状态：成员属性
    行为：成员方法

创建类的实例时需要调用类的*构造器/构造方法（Constructor）* - **不必须为public，无返回值，方法名与类名完全一致**，如：

    public class Person {
        private String name;
        public Person(String name) {
            //构造方法
            this.name = name;
        }
    }

    public class A {
        private A() {
            //私有构造方法 多用于单例模式
        }

        private A uniqueA;

        public static A getA() {
            uniqueA = new A();
            return uniqueA;
        }
    }

若一个类中没有手动定义任何构造方法，JVM会默认创建一个无参构造方法

**类/方法的权限**有四种

    默认（什么都不写） 公共public
    私有private 被保护protected
    
    如：
    public class Test {}
    private String name;
    protected void onCreate(Bundle) {}
    void method() {}

其中
<br>public是整个Project文件都可以直接访问；
<br>protected是同包可以访问；
<br>private只有本类可以访问。

**继承（extends关键字）- 儿子（子类）拥有和父亲一样的行为（方法和属性）**

    class Animal {
        private int age;
        public void eat() {}
    }

    public class Dog extends Animal {
        @Override
        public void eat() {}
    }

继承关系下，不同权限所对应的能继承到的东西有所不同。

| |public|protected|private|
|----|----|----|----|
|属性|可以继承|可以继承|可以继承|
|行为|可以继承|可以继承|-|
|构造器|-|-|-|

**注意事项**

1.private的属性可以继承，但无法直接使用。需要通过get和set方法访问；

2.父类构造器虽然不能被继承，但是可以被调用。（super关键字）

**多态 - 不同子类做相同的事，表现效果不同。**

如：

    class Animal {
        public void eat() {}
    }

    class Dog extends Animal {
        @Override
        public void eat() {
            sysout("吃骨头");
        }
    }
    class Cat extends Animal {
        @Override
        public void eat() {
            sysout("吃鱼");
        }
    }

多态使用的三个必要条件：

    继承 重写 
    父类引用指向子类对象
    Parent a = new Child()

## 2.一些关键字

**static - 静态**：主要用于类、变量、方法或代码块的修饰，表示静态的、不可变的、可以被类的所有实例对象共享的。属于类，独一份

如：

    private static String TAG = "MyTAG"; //静态变量
    public static void main(String[] args) {} //静态方法
    static {
        //静态代码块
    }

static的属性和方法 不用创建对象就可以直接访问，如

    java.util.Arrays ⬇
        public static toString(int[] a) {...}
    使用时：
    import java.util.Arrays;
    Arrays.toString(int数组)

static代码块会在类初始化时调用一次。

static在导包时使用 可在引用类时省去类名。如：

    import static java.util.Arrays;
    sort(数组)

**super和this**:super代表父类，this代表当前类

super和this一样，都可以调用属性、行为和构造器。*只不过super调的是父类的东西*

在构造器中直接使用this(参数)代表调用本类的构造方法，在方法中使用this用于区分同名全局变量与参数

    public Person(String name) {
        this.name = name;
    }
    public Person() {
        this("张三");
    }

super关键字调用父类方法，复用父类此方法的所有逻辑。

*有时必须手动调用super构造方法，因为* **子类构造之前必须先完成所有直接或间接父类的构造。**
- **典型情况：父类只有含参数的构造器**

此外，被static修饰的地方也不能使用this与super。

**final** - 最终的，不可被修改的

final类不可被继承。如java.lang.String;

final类的实例不可以被修改。如String创建后即为常量;

final的变量即为常量(const)。如final int a = 10;

## 3.抽象 - 从众多物中抽出共同特征的过程


