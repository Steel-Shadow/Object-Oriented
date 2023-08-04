// DYNAMIC MEMORY ALLOCATION MALLOC/FREE STACK/HEAP
// function(pfn 函数指针 bsearch qsort) macro
// 宏开关
// 函数过载 overloading
// defualt parameter

```c++
#include <stdio.h>
#include <stdlib.h>

int main() {
    printf("%d", sizeof(char*));
    getchar();
    return 0;
}
```

# 2022.9.20

pointer vs. reference

Java 拷贝 clone 浅拷贝 vs. 深拷贝   
C指针问题   
1野指针 2内存泄漏   
3重复释放 4返回局部变量地址 
5类似浅拷贝 

Java static

Java 继承    
class extends   
interface  

Java const

# 2022.9.27

Operator overload 非必要 Java不支持

Java String

## new delete
new vs. mallo  
new = malloc + construct  
delete = destruct + free

## inheritance and composition 
## 继承 联合/组合/组成
继承：共性与特性
延伸类/子类 基类/父类
protected 访问权限

Java 父类仅有一  
c++ 可以有多个父类

# 2022.10.4

继承中的overload牵一发而动全身

## 多态

upcasting 向上类型转换（默认支持）

binding 绑定，将一次函数调用，与函数入口相对性的过程

early binding  
多态就是 later binding/ runtime binding/ dynamic binding
类有 v-table(virtual table) 虚函数表 v-ptr对象有虚指针 寻址->调用

# 2022.10.11

destructor
```c++
virtrual ~Class();
```

Java: final finally finalize

abstract 抽象类不可实例化

pure virtual 包含纯虚函数(自动继承给子类)
则是abstract(c++)
```c++
virtual void speak()=0;//纯虚函数 通常 没有函数体
```

高级抽象
+ 抽象类
+ 行为抽象共性 串联 (java中的interface)

c++ template

模板类
STL: standard template library

# 2022.10.18

设计模式
std 迭代器模式 iterator

图形化界面 GUI graphical user interface

 