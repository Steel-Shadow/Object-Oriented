package Src;

interface Shape {
    public double getArea(); // 你不能为抽象的`形状`概念定义求面积方法
}

class Square implements Shape {
    private double length;
    public Square(double length) {
        this.length = length;
    }
    @Override
    public double getArea() { //你可以为一个正方形编写求面积方法
        return length * length;
    }
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) {
        this.radius = radius;
    }
    @Override
    public double getArea() { //你可以为一个圆形编写求面积方法
        return radius * radius * Math.PI;
    }
}
public class Main {
    public static void main(String[] args) {
        Shape myShape; // 声明一个Shape的变量， 这是还没有任何实例产生
        myShape = new Square(888); // 创建一个Square的实例，用myShape变量引用它。
        System.out.println(myShape.getArea());
        myShape = new Circle(888); // 创建一个Circle的实例，用myShape变量引用它。
        System.out.println(myShape.getArea());
        // myShape = new Shape(); // Shape的概念过于抽象以至于实例化没有意义，这一行编译报错。

    }
}