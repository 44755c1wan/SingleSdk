# gradle引入方法
第一步：
在项目根目录build.gradle文件中添加如下：
	allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

第二步：
在app module build.gradle文件中添加如下：
    dependencies {
        implementation 'com.github.44755c1wan:SingleSdk:1.3'
    }
