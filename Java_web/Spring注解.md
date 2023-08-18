# Spring注解

### **1. @SpringBootApplication**

Boot项目的启动类注解。

这个注解底层包括的其他注解：

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(excludeFilters={@Filter(type=CUSTOM,classes=TypeExcludeFilter.class),})

### **2. @Autowired**

自动导入对象到类中，被注入进的类同样要被Spring容器管理。

使用对象时省去了new的步骤。

### **3. @Component @Repository @Service @Controller**

都是把类标识成可以用*@Autowired*自动装配的Bean类。

- @Component 

    通用注解，可标注任意类为Spring组件

- @Repository 

    持久层DAO，主要用于DB操作

- @Service 

    服务层，主要涉及复杂的逻辑

- @Controller 
    
    MVC控制层，主要接收用户请求并调用Service层

### **4. @ResponseBody**

使用此注解，可以在控制层返回JSON或XML数据到前端。

### **5. @RestController**

@RestController = @Controller + @ResponseBody

将方法返回值直接填入HTTP响应体中。

### **6. @Scope(作用域)**

声明Spring Bean的作用域。

四种常见的Spring Bean作用域：

- singleton

    单例bean

- prototype 

    每次请求都会创建一个新的bean实例

- request 
    
    每次HTTP请求都会产生一个新的bean
    且bean仅在当前请求中有效

- session

    每次HTTP请求都会产生一个新的bean
    且bean仅在当前session中有效

### **7. @Configuration**

一般用于声明配置类。

### **8. @RequestMapping**

定义url的路径。

参数：
- value：路径名
- produce：接收的数据类型