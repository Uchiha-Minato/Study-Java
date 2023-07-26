# Maven

## Maven是什么

一个构建工具，把一个大项目拆成好多个小项目，这些小项目打包成jar存放在统一的仓库中，别的项目需要使用就直接添加依赖

## Lifecycle - Maven生命周期

*IDEA右侧Maven菜单即可一键运行*

clean - 清除项目中的target文件夹

validate - 检查pom.xml是否出错

compile - 编译源文件为字节码文件，生成target文件夹

test - 自动执行单元测试

package - 对项目进行打包

verify - 具有validate compile package的功能，大多数情况下与package运行效果相同

install - 将jar上传到本地仓库中，让其他项目进行依赖引用

site - 生成项目帮助文档

deploy - 将jar上传到远程仓库

## Maven的聚合与继承

一个大型项目为了便于后期维护，会被拆分成很多子模块

在Maven中将这些子模块整合到一起称为**聚合**

父项目集中处理公共配置，子项目可以复用 称为**继承**

    聚合是为了对多个子项目进行统一构建。

<br>

## 项目的聚合

在IntelliJ IDEA中，父子模块的关联配置由IDE自动设置。

如果不用IDEA，则在父项目的pom.xml中使用<modules>标签引用其他子模块：

    <modules>
        <module>subproj</module>
        <module>subproj2</module>
    </modules>

子模块之间互相引用就在pom.xml中添加依赖就行。如：

    <dependency>
      <groupId>com.lhy.abc</groupId>
      <artifactId>Service</artifactId>
      <version>3.0-RELEASE</version>
      <scope>compile</scope>
    </dependency>

## 项目的继承

在OOP中，继承是为了*代码复用*；在Maven中，继承是为了*配置复用*。

在父项目中做好配置，子项目即可继承父项目配置，在项目中无需更多的配置就可使用父类已经定义好的配置。只需在pom.xml中添加parent：

    <parent>
        <groupId>com.lhy.abc</groupId>
        <artifactId>Parent1</artifactId>
        <version>3.0-RELEASE</version>
        <relativePath>../Parent1/pom.xml</relativePath>
    </parent>

以上步骤实现了所有子模块都能使用父类配置。

如果想实现子模块自主选择需要的依赖，就需要在父模块的配置中使用<dependencyManagement>给子模块提供“可以选择的依赖列表”，是否使用依赖由子模块决定。

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.11</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>5.3.4</version>
            </dependency>
            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>javax.servlet-api</artifactId>
                <version>3.1.0</version>
            </dependency>
            <dependency>
                <groupId>org.glassfish.web</groupId>
                <artifactId>jstl-impl</artifactId>
                <version>1.2</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

子模块中选择列表中的依赖，无需提供版本号：

    <dependencies>
        <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        </dependency>

        <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        </dependency>

        <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jstl-impl</artifactId>
        </dependency>

        <dependency>
        <groupId>com.lhy.abc</groupId>
        <artifactId>Service</artifactId>
        <version>3.0-RELEASE</version>
        <scope>compile</scope>
        </dependency>
    </dependencies>

此外，子模块也可以自选其他的依赖：

    <dependencies>
        <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        </dependency>

        <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        </dependency>

        <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
        </dependency>
    </dependencies>

**配置<scope>import</scope>依赖范围的使用**

